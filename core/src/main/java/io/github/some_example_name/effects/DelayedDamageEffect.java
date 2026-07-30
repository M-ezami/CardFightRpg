package io.github.some_example_name.effects;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.DamageEffect;
import io.github.some_example_name.events.event.phaseEvents.EnemyTurnStartEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.events.utilities.EventListener;
import io.github.some_example_name.target.Targatable;
import io.github.some_example_name.target.TargetingStrategy;

public class DelayedDamageEffect extends DamageEffect {

    private final int specifiedTurns;
    private final int damagePerTurn;

    private int turnCounter = 0;
    private boolean done = false;

    private final EventBus eventBus;

    private EventListener<EnemyTurnStartEvent> listener;

    public DelayedDamageEffect(int amount,
                               TargetingStrategy targetingStrategy,
                               int specifiedTurns) {
        super(amount, targetingStrategy);

        this.specifiedTurns = specifiedTurns;
        this.damagePerTurn = amount / specifiedTurns;
        this.eventBus = EventBus.getInstance();
    }

    private void subscribe(GameState state) {

        listener = e -> {

            turnCounter++;
            dealDamage(state);

            if (turnCounter >= specifiedTurns) {
                done = true;
                turnCounter = 0;
                eventBus.unsubscribe(EnemyTurnStartEvent.class, listener);
            }
        };

        eventBus.subscribe(EnemyTurnStartEvent.class, listener);
    }

    private void dealDamage(GameState state) {
        for (Targatable target : targetingStrategy.getTargets(state)) {
            System.out.println("Damaging: " + target);
            target.takeDamage(damagePerTurn);
        }
    }

    @Override
    public void apply(GameState state) {
        done = false;
        turnCounter = 0;
        subscribe(state);
    }


}
