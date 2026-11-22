package com.example.mathrpgturnbased;

public class Logic {
    private int round = 0;
    private QuestionGenerator questionGenerator = new QuestionGenerator();
    private Questions currentQuestion;
    boolean isBattleFinished = false;
    private final String[] heroList = {"GCDWarrior", "LCMFighter", "PythagorasSolider"};
    private final String[] topic = {"GCD", "LCM", "PYTHAGORAS"};

    private int currentHeroIndex = 0;

    public Questions nextQuestion() {

        if (isBattleFinished) {
            return null;
        }

        currentHeroIndex = round % 3;

        currentQuestion = questionGenerator.generate(
                topic[currentHeroIndex]
        );

        round++;

        return currentQuestion;
    }

    public String getCurrentHero() {
        return heroList[currentHeroIndex];
    }

    private void endGame(){
        isBattleFinished = true;
    }

    public boolean getisBattleFinished() {
        return isBattleFinished;
    }

    public void setBattleFinished(boolean bool){
        this.isBattleFinished = bool;
    }
}
