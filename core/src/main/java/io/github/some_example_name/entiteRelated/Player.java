package io.github.some_example_name.entiteRelated;

import io.github.some_example_name.data.Deck;

public class Player implements Targatable {
    public  float maxHealth = 10;
    private int maxMana = 3;

    private final Deck deck;

    private float health;
    private int mana;

    public Player() {
        this.health = maxHealth;
        this.mana = maxMana;
        this.deck = new Deck();
    }

    public  float getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return mana;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCurrentMana() {
        return mana;
    }

    public void spendMana(int mana) {
        this.mana -= mana;
    }


    @Override
    public void takeDamage(int amount) {
        this.health -= amount;
    }


    public float getHealth() {
        return this.health;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public void resetMana() {
        this.mana = maxMana;
    }
}
