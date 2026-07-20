package io.github.some_example_name.cards.cardRelated.parents;


import io.github.some_example_name.cards.cardRelated.CardType;

public abstract class AbstractCard implements Card {
    protected String name;
    protected String description;
    protected int manaCost;
    protected CardType cardType;

    protected AbstractCard(String name, String description, int manaCost) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public int getManaCost() {
        return manaCost;
    }


}
