package io.github.some_example_name.effects.parents;


import io.github.some_example_name.data.GameState;

import io.github.some_example_name.target.Targatable;
import io.github.some_example_name.target.TargetingStrategy;

public abstract class DamageEffect extends Effect {

    public DamageEffect(int amount, TargetingStrategy targetingStrategy) {
        super(amount, targetingStrategy);
        this.amount = amount;
    }


    public void setAmount(final int amount) {
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return amount + "damage effect";
    }





}

