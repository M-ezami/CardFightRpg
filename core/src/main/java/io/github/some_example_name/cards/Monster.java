package io.github.some_example_name.cards;

import io.github.some_example_name.MonsterType;
import io.github.some_example_name.effects.MonsterEffect;
import io.github.some_example_name.entiteRelated.TargetingStrategy;

public abstract class Monster implements MonsterEffect {

    protected int attack;
    protected int health;
    protected TargetingStrategy targetingStrategy;
    protected String name;
    protected MonsterType type;

    public Monster() {

    }

    public MonsterType getType() {
        return type;
    }

    @Override
    public void onStartTurn() {

    }

    @Override
    public void onEndTurn() {

    }

    @Override
    public void onAttack() {

    }

    @Override
    public void onBeingAttacked() {

    }
}
