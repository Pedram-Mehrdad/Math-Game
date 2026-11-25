package com.example.mathrpgturnbased;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.animation.DecelerateInterpolator;

public class Questions {

    private String prompt;
    private String[] options;
    private String correctAnswer;

    public Questions(String prompt, String[] options, String correctAnswer) {
        this.prompt = prompt;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public interface AnswerListener {
        void onAnswer(boolean isCorrect);
    }


    public void show(
            Activity activity,
            FrameLayout container,
            AnswerListener listener) {

        // Inflate the question layout.
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_question, container, false);

        // Connect XML elements.
        TextView questionText =
                view.findViewById(R.id.questionText);

        Button[] answerButtons = {
                view.findViewById(R.id.answer1),
                view.findViewById(R.id.answer2),
                view.findViewById(R.id.answer3),
                view.findViewById(R.id.answer4)
        };

        // Display question.
        questionText.setText(this.prompt);

        // Configure the buttons.
        for (int i = 0; i < 4; i++) {

            Button button = answerButtons[i];

            button.setText(options[i]);

            button.setOnClickListener(v -> {

                String selectedAnswer =
                        button.getText().toString();

                boolean isCorrect = selectedAnswer.equals(
                        this.correctAnswer
                );

                // Prevent multiple answers.
                for (Button b : answerButtons) {
                    b.setEnabled(false);
                }

                // Slide the question panel out.
                view.animate()
                        .translationX(
                                container.getWidth() + view.getWidth()
                        )
                        .setDuration(350)
                        .setInterpolator(new DecelerateInterpolator())
                        .withEndAction(() -> {

                            container.removeView(view);
                            container.setVisibility(View.GONE);

                            listener.onAnswer(isCorrect);
                        })
                        .start();
            });
        }

        // Add the panel at the bottom of the scene.
        int screenWidth = activity.getResources()
                .getDisplayMetrics().widthPixels;

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        (int) (screenWidth * 0.90f),
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.TOP | Gravity.CENTER_HORIZONTAL
                );

        params.topMargin = dp(activity, 16);

        container.setVisibility(View.VISIBLE);
        container.addView(view, params);

        // Start below the screen and slide into position.
        view.post(() -> {

            // Start completely outside the left side.
            view.setTranslationX(
                    -container.getWidth() - view.getWidth()
            );

            view.setAlpha(1f);

            // Slide into position.
            view.animate()
                    .translationX(0f)
                    .setDuration(400)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        });
    }

    private int dp(Activity activity, float value) {
        return (int) (value *
                activity.getResources().getDisplayMetrics().density);
    }

}