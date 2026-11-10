package com.example.mathrpgturnbased;

public class Enemy {
    private int HP;
    private int fullHealth;
    private String type;
    private int damageRate;

    public Enemy(int HP, int fullHealth, String type, int damageRate){
        this.HP = HP;
        this.fullHealth = fullHealth;
        this.type = type;
        this.damageRate = damageRate;
    }

    public int getHP() {
        return HP;
    }
    public String getType() {
        return type;
    }
    public int getDamageRate() {
        return damageRate;
    }

    public int getFullHealth() {
        return fullHealth;
    }
}
