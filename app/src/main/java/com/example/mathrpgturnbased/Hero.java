package com.example.mathrpgturnbased;

 public class Hero implements HeroOrEnemy {
    private int HP;
    private final int fullHealth;
    private final String type;
    private final int damageRate;
    private final int imageResID;
    private final int cardResID;

     public Hero(int HP, int fullHealth, String type, int damageRate, int imageResID, int cardResID){
        this.HP = HP;
        this.fullHealth = fullHealth;
        this.type = type;
        this.damageRate = damageRate;
        this.imageResID = imageResID;
        this.cardResID = cardResID;
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

    @Override
    public void setHP(int HP) {
        this.HP = Math.max(0, Math.min(HP, fullHealth));
    }

     public int getImageResID() {
         return imageResID;
     }

     public int getCardResID() {
         return cardResID;
     }
 }
