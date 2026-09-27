package io.github.some_example_name.target.parentsOrOthers;

import io.github.some_example_name.data.GameState;

import java.util.List;
public abstract class TargetingStrategy {

    private final GameState gameState;

    public TargetingStrategy(){
        this.gameState = GameState.get();
    }
    public abstract List<Targatable> getTargets();

    protected abstract boolean isValidTarget(Targatable target);

    public abstract boolean requiresTarget();
}
