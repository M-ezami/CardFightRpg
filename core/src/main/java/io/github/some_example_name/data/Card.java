package io.github.some_example_name.data;

import io.github.some_example_name.entiteRelated.TargetingStrategy;
import io.github.some_example_name.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {
    private int manaCost;
    private List<Effect> effects;
    private String name;
    protected TargetingStrategy targetingStrategy;
    private String description;

    public Card(String name, int manaCost) {
        this.effects = new ArrayList<>();
        this.name = name;
        this.manaCost = manaCost;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
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
