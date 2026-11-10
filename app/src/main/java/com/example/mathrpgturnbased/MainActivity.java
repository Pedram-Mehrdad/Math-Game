package com.example.mathrpgturnbased;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    // Game settings
    private final int MAX_ENEMY_HEALTH = 100;
    private final int DAMAGE_PER_CORRECT = 20;
    private final int SCORE_PER_CORRECT = 10;

    // Heroes and their mathematical topics
    private final String[] heroNames = {
            "WARRIOR", "MAGE", "ARCHER"
    };

    private final String[] topics = {
            "GCD", "LCM", "PYTHAGORAS"
    };

    // Game state
    private int currentHeroIndex = 0;
    private int enemyHealth = MAX_ENEMY_HEALTH;
    private int score = 0;
    private int turnNumber = 1;

    private boolean battleFinished = false;

    // Game objects
    private QuestionGenerator questionGenerator;
    private Questions currentQuestion;

    // UI elements
    private TextView tvTurn;
    private TextView tvTopic;
    private TextView tvQuestion;
    private TextView tvFeedback;
    private TextView tvScore;
    private TextView tvEnemyHealth;

    private ProgressBar enemyHealthBar;

    private Button[] answerButtons;
    private Button btnRestart;

    private TextView[] heroCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize the question generator
        questionGenerator = new QuestionGenerator();

        // Connect XML elements to Java
        tvTurn = findViewById(R.id.tvTurn);
        tvTopic = findViewById(R.id.tvTopic);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvFeedback = findViewById(R.id.tvFeedback);
        tvScore = findViewById(R.id.tvScore);
        tvEnemyHealth = findViewById(R.id.tvEnemyHealth);

        enemyHealthBar = findViewById(R.id.enemyHealthBar);

        btnRestart = findViewById(R.id.btnRestart);

        heroCards = new TextView[]{
                findViewById(R.id.heroWarrior),
                findViewById(R.id.heroMage),
                findViewById(R.id.heroArcher)
        };

        answerButtons = new Button[]{
                findViewById(R.id.option1),
                findViewById(R.id.option2),
                findViewById(R.id.option3),
                findViewById(R.id.option4)
        };

        // Set click listeners for the four answers
        for (Button button : answerButtons) {

            button.setOnClickListener(v ->
                    answerQuestion((Button) v)
            );
        }

        // Restart button
        btnRestart.setOnClickListener(v -> resetGame());

        resetGame();
    }


    // Generate and display the current hero's question
    private void showTurn() {

        currentQuestion =
                questionGenerator.generate(topics[currentHeroIndex]);

        tvTurn.setText(
                "TURN " + turnNumber + " - " + heroNames[currentHeroIndex]
        );

        tvTopic.setText(
                heroNames[currentHeroIndex] + " CHALLENGE: " +
                        topics[currentHeroIndex]
        );

        tvQuestion.setText(currentQuestion.getPrompt() + "?");

        String[] options = currentQuestion.getOptions();

        for (int i = 0; i < answerButtons.length; i++) {

            answerButtons[i].setText(options[i]);
            answerButtons[i].setEnabled(true);
        }

        updateHeroHighlight();
    }


    // Check the selected answer
    private void answerQuestion(Button selectedButton) {

        if (battleFinished) {
            return;
        }

        String selectedAnswer =
                selectedButton.getText().toString();

        String correctAnswer =
                currentQuestion.getCorrectAnswer();

        String activeHero = heroNames[currentHeroIndex];

        if (selectedAnswer.equals(correctAnswer)) {

            // Correct answer
            enemyHealth = Math.max(
                    0,
                    enemyHealth - DAMAGE_PER_CORRECT
            );

            score += SCORE_PER_CORRECT;

            tvFeedback.setText(
                    "Correct! " + activeHero +
                            " dealt " + DAMAGE_PER_CORRECT + " damage!"
            );

            tvFeedback.setTextColor(
                    Color.parseColor("#79E08E")
            );

        } else {

            // Incorrect answer
            tvFeedback.setText(
                    "Incorrect! The correct answer was " +
                            correctAnswer
            );

            tvFeedback.setTextColor(
                    Color.parseColor("#F87171")
            );
        }

        updateEnemyHealth();
        updateScore();

        // Check whether the enemy has been defeated
        if (enemyHealth <= 0) {

            finishBattle();
            return;
        }

        // Move to the next hero
        currentHeroIndex =
                (currentHeroIndex + 1) % heroNames.length;

        turnNumber++;

        showTurn();
    }


    // Update enemy health
    private void updateEnemyHealth() {

        enemyHealthBar.setProgress(enemyHealth);

        tvEnemyHealth.setText(
                enemyHealth + " / " +
                        MAX_ENEMY_HEALTH + " HP"
        );
    }


    // Update player score
    private void updateScore() {

        tvScore.setText("SCORE: " + score);
    }


    // Highlight the active hero
    private void updateHeroHighlight() {

        for (int i = 0; i < heroCards.length; i++) {

            if (i == currentHeroIndex) {

                heroCards[i].setBackgroundColor(
                        Color.parseColor("#475569")
                );

            } else {

                heroCards[i].setBackgroundColor(
                        Color.parseColor("#31445A")
                );
            }
        }
    }


    // Finish the battle
    private void finishBattle() {

        battleFinished = true;

        tvTurn.setText("BATTLE COMPLETE");

        tvTopic.setText("VICTORY!");

        tvQuestion.setText(
                "You defeated the Guardian!"
        );

        tvFeedback.setText(
                "Final score: " + score
        );

        for (Button button : answerButtons) {
            button.setEnabled(false);
        }

        btnRestart.setVisibility(View.VISIBLE);
    }


    // Reset the game
    private void resetGame() {

        currentHeroIndex = 0;

        enemyHealth = MAX_ENEMY_HEALTH;

        score = 0;

        turnNumber = 1;

        battleFinished = false;

        for (Button button : answerButtons) {
            button.setEnabled(true);
        }

        btnRestart.setVisibility(View.GONE);

        tvFeedback.setText(
                "Choose the correct answer!"
        );

        tvFeedback.setTextColor(Color.WHITE);

        updateEnemyHealth();

        updateScore();

        showTurn();
    }
}