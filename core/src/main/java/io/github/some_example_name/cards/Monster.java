package io.github.some_example_name.cards;

import io.github.some_example_name.cards.cardRelated.MonsterType;
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

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    public String getName() {
        return name;
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
