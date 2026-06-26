package io.github.some_example_name.cards.cardRelated.parents;


import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.cards.cardRelated.CardType;

public abstract class MonsterCard implements Card {

    private int manaCost;
    private String name;
    protected Monster monster;
    protected CardType cardType;
    private String description;


    public MonsterCard() {
        this.cardType = CardType.MONSTER;
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
