package com.example.mathrpgturnbased;

import android.app.Activity;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private Logic logic;
    private ImageView heroImage;
    private ImageView enemyImage;

    private ObjectAnimator heroIdleAnimator;
    private ObjectAnimator enemyIdleAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        logic = new Logic();

        showNextQuestion();
        heroImage = findViewById(R.id.Hero);
        enemyImage = findViewById(R.id.Enemy);
    }


    // Start idle animations when the game opens
    @Override
    protected void onStart() {
        super.onStart();
        startIdleAnimations();
    }


    // Stop animations when leaving the game
    @Override
    protected void onStop() {

        if (heroIdleAnimator != null) {
            heroIdleAnimator.cancel();
        }

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.cancel();
        }

        super.onStop();
    }


    // Idle animations
    private void startIdleAnimations() {

        // Hero breathing
        heroIdleAnimator = ObjectAnimator.ofPropertyValuesHolder(
                heroImage,
                PropertyValuesHolder.ofFloat("scaleX", 1f, 1.015f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 1.025f, 1f)
        );

        heroIdleAnimator.setDuration(1800);
        heroIdleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        heroIdleAnimator.setInterpolator(
                new AccelerateDecelerateInterpolator()
        );


        // Enemy floating
        enemyIdleAnimator = ObjectAnimator.ofFloat(
                enemyImage,
                "translationY",
                0f, -dp(5), 0f
        );

        enemyIdleAnimator.setDuration(1600);
        enemyIdleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        enemyIdleAnimator.setInterpolator(
                new AccelerateDecelerateInterpolator()
        );


        heroIdleAnimator.start();
        enemyIdleAnimator.start();
    }


    // Hero attack
    private void playAttackAnimation() {

        ObjectAnimator attack = ObjectAnimator.ofFloat(
                heroImage,
                "translationY",
                0f, -dp(30), 0f
        );

        attack.setDuration(400);
        attack.setInterpolator(new DecelerateInterpolator());
        attack.start();
    }


    // Enemy takes damage
    private void playHitAnimation() {

        ObjectAnimator hit = ObjectAnimator.ofFloat(
                enemyImage,
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

    private void showNextQuestion() {

        Questions question = logic.nextQuestion();

        if (question == null) {
            return;
        }

        question.show(this, isCorrect -> {

            if (isCorrect) {

                // Correct answer
                playAttackAnimation();
                playHitAnimation();

            } else {

                // Incorrect answer
                // No damage
            }

            // Show the next question after a short delay
            new android.os.Handler(
                    android.os.Looper.getMainLooper()
            ).postDelayed(
                    this::showNextQuestion,
                    600
            );

        });
    }
}