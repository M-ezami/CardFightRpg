package io.github.some_example_name.cards.cardParents;


import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.Monster;

public abstract class MonsterCard implements Card {

    private int manaCost;
    private String name;
    protected Monster monster;
    private CardType cardType;
    private String description;


    public MonsterCard() {

    }




    public Monster getMonster() {
        return monster;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String getDescription() {
        return "";
    }

    public String getName() {
        return name;
    }

}
