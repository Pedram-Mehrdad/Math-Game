package com.example.mathrpgturnbased;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Questions{

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


    public  void show(
            Activity activity,
            AnswerListener listener) {

        Dialog dialog = new Dialog(activity);

        // Load our XML layout
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_question, null);

        // Connect XML elements
        TextView questionText =
                view.findViewById(R.id.questionText);

        Button[] answerButtons = {
                view.findViewById(R.id.answer1),
                view.findViewById(R.id.answer2),
                view.findViewById(R.id.answer3),
                view.findViewById(R.id.answer4)
        };

        // Display question
        questionText.setText(this.prompt);

        // Get the four possible answers

        // Configure the buttons
        for (int i = 0; i < 4; i++) {

            Button button = answerButtons[i];

            button.setText(options[i]);

            button.setOnClickListener(v -> {

                String selectedAnswer =
                        button.getText().toString();

                boolean isCorrect = selectedAnswer.equals(
                        this.correctAnswer
                );

                // Destroy the popup
                dialog.dismiss();

                // Return true or false to Java
                listener.onAnswer(isCorrect);
            });
        }

        // Show popup
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        // Set dialog dimensions
        Window window = dialog.getWindow();

        if (window != null) {

            window.setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );

            int screenWidth = activity.getResources()
                    .getDisplayMetrics().widthPixels;

            window.setLayout(
                    (int) (screenWidth * 0.90f),
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }
    }
}