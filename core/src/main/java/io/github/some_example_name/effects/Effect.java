package io.github.some_example_name.effects;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.targets.TargetingStrategy;

public abstract class Effect {

    protected TargetingStrategy targetingStrategy;
    protected int amount;

    public Effect(int amount, TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
        this.amount = amount;
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    @Override
    public String toString() {
       return getClass().getSimpleName();
    }

    public abstract String getDescription();

    public abstract void apply(GameState state);


}
