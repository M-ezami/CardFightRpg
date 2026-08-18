package io.github.some_example_name.effects;


import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.DamageEffect;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public class SimpleDamageEffect extends DamageEffect {

    public SimpleDamageEffect(int amount, TargetingStrategy targetingStrategy) {
        super(amount, targetingStrategy);
        this.amount = amount;
    }


    @Override
    public void applyEffectToTarget(Targatable target, GameState state) {
        target.takeDamage(amount);
    }
}



