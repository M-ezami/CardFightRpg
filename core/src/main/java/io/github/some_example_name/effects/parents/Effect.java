package io.github.some_example_name.effects.parents;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

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

    public abstract String getDescription();

    public final void apply(GameState state, Targatable selectedTarget) {
        for (Targatable target : targetingStrategy.getTargets(state, selectedTarget)) {
            applyEffectToTarget(target, state);
        }
    }

    public abstract void applyEffectToTarget(
        Targatable target,
        GameState state
    );
}
