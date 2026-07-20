package io.github.some_example_name.cards;

import io.github.some_example_name.cards.cardRelated.MonsterType;
import io.github.some_example_name.effects.MonsterEffect;
import io.github.some_example_name.entiteRelated.targets.Targatable;
import io.github.some_example_name.entiteRelated.targets.TargetingStrategy;

public abstract class Monster extends Targatable implements MonsterEffect {

    protected int attack;

    protected TargetingStrategy targetingStrategy;
    protected String name;
    protected MonsterType type;

    public Monster(int health, int maxHealth) {
        super(health, maxHealth);
    }



    public MonsterType getType() {
        return type;
    }


    public int getAttack() {
        return attack;
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
