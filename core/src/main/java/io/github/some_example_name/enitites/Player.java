package io.github.some_example_name.enitites;

import io.github.some_example_name.cards.SpellCards.FireCard;
import io.github.some_example_name.cards.SpellCards.MonsterGainDamageCard;
import io.github.some_example_name.cards.SpellCards.SlowBurn;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.data.Cards;
import io.github.some_example_name.screens.SimpleMonsterCard;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

import java.util.ArrayList;
import java.util.List;

public class Player extends Targatable {

    private final Cards cards;
    private final List<Monster> monsters;

    private int mana;
    private final int maxMana = 3;

    public Player() {
        super(10, 10);
        this.monsters = new ArrayList<>();
        this.health = maxHealth;
        this.mana = maxMana;
        this.cards = new Cards(setupPlayerDeck());
    }

    public void discardCard(Card card) {
        cards.discard(card);
    }

    public void playCard(Card card) {
        spendMana(card.getManaCost());
        discardCard(card);

    }


    private List<Card> setupPlayerDeck() {
        List<Card> playerDeck = new ArrayList<>();
        playerDeck.add(new FireCard());
        playerDeck.add(new SlowBurn());
        playerDeck.add(new MonsterGainDamageCard());
        playerDeck.add(new SimpleMonsterCard());
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


    public boolean isDead() {
        return false;
    }

    public void resetMana() {
        this.mana = maxMana;
    }
}
