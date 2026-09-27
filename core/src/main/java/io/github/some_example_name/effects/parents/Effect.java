package io.github.some_example_name.effects.parents;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.MonsterBuff;
import io.github.some_example_name.effects.MultipleRoundsEffect;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public abstract class Effect {

    protected TargetingStrategy targetingStrategy;
    protected int amount;
    protected MultipleRoundsEffect multipleRoundsEffect;
    protected EventBus eventBus;

    public Effect(int amount, TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
        this.amount = amount;
        this.eventBus = EventBus.getInstance();
    }
    public Effect(int amount, TargetingStrategy targetingStrategy, MultipleRoundsEffect multipleRoundsEffect) {
        this.targetingStrategy = targetingStrategy;
        this.amount = amount;
        this.multipleRoundsEffect = multipleRoundsEffect;
    }

    public boolean isOverMultipleRounds(){
        return multipleRoundsEffect != null;
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    public abstract String getDescription();

    //this gets the actual target and it also sort of sets the target if its a single target like a signle enemy
    public final void apply(GameState state, Targatable selectedTarget) {
        for (Targatable target : targetingStrategy.getTargets(state, selectedTarget)) {
            if (isOverMultipleRounds()) {
                multipleRoundsEffect.subscribe(
                    ()-> applyEffectToTarget(target, state));
            }else{
                applyEffectToTarget(target, state);
            }
        }
    }

    public abstract void applyEffectToTarget(Targatable target, GameState state);
}
