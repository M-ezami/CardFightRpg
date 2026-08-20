package io.github.some_example_name.enitites;

import io.github.some_example_name.cards.cardRelated.MonsterType;
import io.github.some_example_name.effects.parents.MonsterEffect;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public abstract class Monster extends Targatable implements MonsterEffect {

    protected int damage;

    protected TargetingStrategy targetingStrategy;
    protected String name;
    protected MonsterType type;

    public Monster(int health, int maxHealth) {
        super(health, maxHealth);
    }



    public MonsterType getType() {
        return type;
    }


    public int getDamage() {
        return damage;
    }

    public void gainDamageAmount(int addDamage) {
        this.damage += addDamage;
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
