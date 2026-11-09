package com.example.mathrpgturnbased;

public class Questions{

    private String prompt;
    private String[] options;
    private String correctAnswer;

    public constructor(String prompt, String[] options, String correctAnswer) {
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

}