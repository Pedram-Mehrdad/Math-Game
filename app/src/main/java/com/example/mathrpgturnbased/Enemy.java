package com.example.mathrpgturnbased;

public class Enemy implements HeroOrEnemy {
    private int HP;
    private final int fullHealth;
    private final int damageRate;
    private final int imageResID;
    public Enemy(int HP, int fullHealth, int damageRate, int imageResID){
        this.HP = HP;
        this.fullHealth = fullHealth;
        this.damageRate = damageRate;
        this.imageResID = imageResID;
    }

    public int getHP() {
        return HP;
    }
    public int getDamageRate() {

        return damageRate;
    }

    public int getFullHealth() {
        return fullHealth;
    }

    public int getImageResID() {
        return imageResID;
    }

    @Override
    public void setHP(int HP) {
        this.HP = Math.max(0, Math.min(HP, fullHealth));
    }
}
