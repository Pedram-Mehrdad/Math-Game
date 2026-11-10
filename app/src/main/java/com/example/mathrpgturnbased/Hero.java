package com.example.mathrpgturnbased;

public class Hero {
    private int HP;
    private int fullHealth;
    private String type;
    private int damageRate;
    public Hero(int HP, int fullHealth, String type, int damageRate){
        this.HP = HP;
        this.fullHealth = fullHealth;
        this.type = type;
        this.damageRate = damageRate;
    }

    public int getFullHealth() {
        return fullHealth;
    }

    public int getDamageRate() {
        return damageRate;
    }

    public String getType() {
        return type;
    }

    public int getHP() {
        return HP;
    }
}
