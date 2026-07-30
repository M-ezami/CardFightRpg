package io.github.some_example_name.effects;


import io.github.some_example_name.data.GameState;

import io.github.some_example_name.effects.parents.DamageEffect;
import io.github.some_example_name.effects.parents.Effect;
import io.github.some_example_name.target.Targatable;
import io.github.some_example_name.target.TargetingStrategy;

public class SimpleDamageEffect extends DamageEffect {

    public SimpleDamageEffect(int amount, TargetingStrategy targetingStrategy) {
        super(amount, targetingStrategy);
        this.amount = amount;
    }



    @Override
    public void apply(GameState state) {
        for (Targatable target : targetingStrategy.getTargets(state)) {
            System.out.println("Damaging: " + target);
            target.takeDamage(amount);
        }
    }


}

