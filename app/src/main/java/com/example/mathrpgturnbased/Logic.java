package com.example.mathrpgturnbased;

import java.util.ArrayList;
public class Logic {
    private final QuestionGenerator questionGenerator = new QuestionGenerator();
    private Questions currentQuestion;
    boolean isBattleFinished = false;

    private Enemy enemy;
    private Hero LCMhero;
    private Hero GCDhero;
    private Hero Pyhero;
    ArrayList<Hero> heroes = new ArrayList<>();

    private int currentHeroIndex = -1;
    private int nextHeroIndex = 0;

    private int hpHero = 100;
    private int fullHealthHero = 100;
    private int damageRateHero = 10;

    private int hpEnemy = 300;
    private int fullHealthEnemy = 300;
    private int damageRateEnemy = 30;
    public Logic() {
        enemy = new Enemy(hpEnemy, fullHealthEnemy, damageRateEnemy, R.drawable.enemy);
        LCMhero = new Hero(hpHero, fullHealthHero, "LCM", damageRateHero, R.drawable.heroboy, R.drawable.knightcard);
        GCDhero = new Hero(hpHero, fullHealthHero, "GCD", damageRateHero, R.drawable.herogirl, R.drawable.warriorcard);
        Pyhero = new Hero(hpHero, fullHealthHero, "PYTHAGORAS", damageRateHero, R.drawable.heroold, R.drawable.magecard);

        heroes.add(GCDhero);
        heroes.add(LCMhero);
        heroes.add(Pyhero);
    }
    public Questions nextQuestion() {

        if (isBattleFinished) {
            return null;
        }

        // Find the next living hero.
        for (int i = 0; i < heroes.size(); i++) {

            currentHeroIndex = nextHeroIndex;
            nextHeroIndex = (nextHeroIndex + 1) % heroes.size();

            Hero selectedHero = getCurrentHero();

            if (selectedHero.getHP() > 0) {
                currentQuestion = questionGenerator.generate(
                        selectedHero.getType()
                );

                return currentQuestion;
            }
        }
        // No living heroes remain.
        endGame();
        return null;
    }

    public boolean areAllHeroesDefeated() {

        for (Hero hero : heroes) {

            if (hero.getHP() > 0) {
                return false;
            }
        }

        return true;
    }

    public void endGame(){
        isBattleFinished = true;
    }

    public boolean getIsBattleFinished() {
        return isBattleFinished;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Hero getCurrentHero() {
        if (currentHeroIndex < 0) {
            return heroes.get(0);
        }
        return heroes.get(currentHeroIndex);
    }

    public int getCurrentHeroIndex() {
        return currentHeroIndex;
    }

    public int getHeroCount() {
        return heroes.size();
    }

    public Hero getHeroAt(int index) {
        return heroes.get(index);
    }
}
