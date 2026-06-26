package io.github.some_example_name.cards.cardRelated.parents;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.entiteRelated.TargetingStrategy;
import io.github.some_example_name.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellCard implements Card {
    private  int manaCost;
    private final CardType cardType;
    /*private Mood mood;
    private int age;
    */
    private List<Effect> effects;
    private final String name;
    protected TargetingStrategy targetingStrategy;
    private String description;

    protected SpellCard(String name, int manaCost) {
        this.cardType = CardType.SPELL;
        this.effects = new ArrayList<>();
        this.name = name;
        this.manaCost = manaCost;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public int getManaCost() {
        return manaCost;
    }
}
