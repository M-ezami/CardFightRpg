package io.github.some_example_name.effects;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.targets.Targatable;
import io.github.some_example_name.entiteRelated.targets.TargetingStrategy;
import io.github.some_example_name.events.event.spellEffect;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.system.DamageEvent;

public abstract class Effect {

    protected TargetingStrategy targetingStrategy;
    protected int amount;
    private EventBus eventBus;

    public Effect(int amount, TargetingStrategy targetingStrategy) {
        this.targetingStrategy = targetingStrategy;
        this.amount = amount;
        this.eventBus = EventBus.getInstance();
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }

    public boolean dealsDamage() {
        return this instanceof DamageEffect;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    public void damageOrSpellEvent( Targatable target) {
        if (this.dealsDamage()) {
            eventBus.emit(new DamageEvent(target));
        } else {
            eventBus.emit(new spellEffect(target));
        }
    }


    public abstract String getDescription();

    public abstract void apply(GameState state);


}
