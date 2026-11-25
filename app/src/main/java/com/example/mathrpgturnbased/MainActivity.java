package com.example.mathrpgturnbased;

import android.widget.FrameLayout;
import android.app.Activity;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Logic logic;
    private FrameLayout questionOverlay;

    private ImageView heroImage;
    private ImageView enemyImage;
    private View heroFrame;
    private View enemyFrame;
    private ProgressBar heroHPBar;
    private ProgressBar enemyHPBar;

    private ImageView leftCard;
    private ImageView centerCard;
    private ImageView rightCard;
    private Enemy enemy;
    private Hero hero;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        logic = new Logic();

        questionOverlay = findViewById(R.id.questionOverlay);

        heroImage = findViewById(R.id.Hero);
        enemyImage = findViewById(R.id.Enemy);

        leftCard = findViewById(R.id.LeftCard);
        centerCard = findViewById(R.id.SelectedCard);
        rightCard = findViewById(R.id.RightCard);

        heroFrame = findViewById(R.id.heroFrame);
        enemyFrame = findViewById(R.id.enemyFrame);

        heroHPBar = findViewById(R.id.heroHPBar);
        enemyHPBar = findViewById(R.id.enemyHPBar);

        enemy = logic.getEnemy();

        enemyImage.setImageResource(enemy.getImageResID());
        enemyHPBar.setMax(enemy.getFullHealth());
        enemyHPBar.setProgress(enemy.getHP());

        showNextQuestion();
    }

    // Attack animation
    private void playHeroAttackAnimation() {

        ObjectAnimator attack = ObjectAnimator.ofFloat(
                heroFrame,
                "translationY",
                0f, -dp(30), 0f
        );

        attack.setDuration(400);
        attack.setInterpolator(new DecelerateInterpolator());
        attack.start();
    }

    private void playEnemyAttackAnimation() {

        // Use horizontal movement so it doesn't conflict
        // with the enemy's vertical idle animation.
        ObjectAnimator attack = ObjectAnimator.ofFloat(
                enemyFrame,
                "translationX",
                0f, dp(12), -dp(12), 0f
        );

        attack.setDuration(400);
        attack.setInterpolator(new DecelerateInterpolator());
        attack.start();
    }
    // Enemy takes damage
    private void playHitAnimation(View model) {

        ObjectAnimator hit = ObjectAnimator.ofFloat(
                model,
                "rotation",
                0f, -8f, 8f, -5f, 0f
        );

        hit.setDuration(350);
        hit.start();
    }


    // Convert dp to pixels
    private float dp(float value) {
        return value * getResources()
                .getDisplayMetrics().density;
    }

    private void updateHealthBars() {

        if (hero != null) {
            heroHPBar.setMax(hero.getFullHealth());
            heroHPBar.setProgress(hero.getHP());
        }

        enemyHPBar.setMax(enemy.getFullHealth());
        enemyHPBar.setProgress(enemy.getHP());
    }

    private void updateHeroCards() {

        int count = logic.getHeroCount();
        int currentIndex = logic.getCurrentHeroIndex();

        if (count == 0 || currentIndex < 0) {
            return;
        }

        // Previous hero, wrapping around the list.
        Hero previousHero = logic.getHeroAt(
                (currentIndex - 1 + count) % count
        );

        // Currently selected hero.
        Hero currentHero = logic.getHeroAt(currentIndex);

        // Next hero, wrapping around the list.
        Hero nextHero = logic.getHeroAt(
                (currentIndex + 1) % count
        );

        // Update card images.
        leftCard.setImageResource(
                previousHero.getCardResID()
        );

        centerCard.setImageResource(
                currentHero.getCardResID()
        );

        rightCard.setImageResource(
                nextHero.getCardResID()
        );

        // Small fade-in for the selected card.
        centerCard.animate().cancel();
        centerCard.setAlpha(0f);
        centerCard.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }

    public void dealDamage(HeroOrEnemy dealer, HeroOrEnemy receiver) {

        int damage = dealer.getDamageRate();

        int remainingHP = Math.max(0, receiver.getHP() - damage);

        receiver.setHP(remainingHP);
    }

    private void showNextQuestion() {
        if (logic.getIsBattleFinished()) {
            return;
        }

        Questions question = logic.nextQuestion();

        if (question == null) {
            logic.endGame();
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            return;
        }

        hero = logic.getCurrentHero();
        heroImage.setImageResource(hero.getImageResID());
        updateHeroCards();
        updateHealthBars();

        question.show(this, questionOverlay, isCorrect -> {
            if (logic.getIsBattleFinished()) {
                return;
            }

            if (isCorrect) {
                // Correct answer
                playHeroAttackAnimation();
                playHitAnimation(enemyFrame);
                dealDamage(hero, enemy);

            } else {
                playEnemyAttackAnimation();
                playHitAnimation(heroFrame);
                dealDamage(enemy, hero);
            }

            updateHealthBars();

            if (enemy.getHP() <= 0) {
                logic.endGame();
                Toast.makeText(this, "Victory!", Toast.LENGTH_LONG).show();
                return;
            }

            // Check defeat.
            if (logic.areAllHeroesDefeated()) {

                logic.endGame();

                Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();

                return;
            }

                // Display the next question after the animations begin.
            handler.postDelayed(this::showNextQuestion, 600);
        });
    }
}