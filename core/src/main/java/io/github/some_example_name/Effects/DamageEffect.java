package io.github.some_example_name.Effects;


import io.github.some_example_name.GameState;

import io.github.some_example_name.Targatable;
import io.github.some_example_name.TargetingStrategy;

public class DamageEffect extends Effect {


    public DamageEffect(int amount, TargetingStrategy targetingStrategy) {
        super(amount, targetingStrategy);
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return amount + " damage effect";
    }


    @Override
    public void apply(GameState state) {
        for (Targatable target : targetingStrategy.getTargets(state)) {
            target.takeDamage(amount);

        }
    }


}

