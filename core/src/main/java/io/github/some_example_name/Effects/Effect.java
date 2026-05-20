package io.github.some_example_name.Effects;

import io.github.some_example_name.GameState;
import io.github.some_example_name.TargetingStrategy;

public abstract class Effect {
    protected TargetingStrategy targetingStrategy;
    protected int amount;

    public Effect(int amount, TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
        this.amount = amount;
    }


    @Override
    public String toString() {
       return getClass().getSimpleName();
    }

    public abstract String getDescription();

    public abstract void apply(GameState state);
}
