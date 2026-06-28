package io.github.some_example_name.entiteRelated;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.FireCard;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.data.Cards;
import io.github.some_example_name.screens.SimpleMonsterCard;

import java.util.ArrayList;
import java.util.List;

public class Player implements Targatable {

    private final Cards cards;
    private final List<Monster> monsters;

    private float health;
    private int mana;
    public float maxHealth = 10;
    private int maxMana = 3;

    public Player() {
        this.monsters = new ArrayList<>();
        this.health = maxHealth;
        this.mana = maxMana;
        this.cards = new Cards(setupPlayerDeck());
    }

    public void discardCard(Card card) {
        cards.discard(card);
    }


    private List<Card> setupPlayerDeck() {
        List<Card> playerDeck = new  ArrayList<>();
        playerDeck.add(new FireCard("Card 1"));
        playerDeck.add(new FireCard("Card 2"));
        playerDeck.add(new FireCard("Card 3"));
        playerDeck.add(new FireCard("Card 4"));
        playerDeck.add(new SimpleMonsterCard());
        return playerDeck;
    }

    public Cards getCards() {
        return cards;
    }

    public List<Card> getCardDeck() {
        return cards.getCardDeck();
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return mana;
    }

    public Cards getDeck() {
        return cards;
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


    public boolean isDead() {
        return false;
    }

    public void resetMana() {
        this.mana = maxMana;
    }
}
