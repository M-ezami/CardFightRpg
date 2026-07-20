package io.github.some_example_name.ui;

import io.github.some_example_name.entiteRelated.EnemyAnimationState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.event.EnemyDiedEvent;
import io.github.some_example_name.events.event.EnemyEffectAppliedEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.system.EnemyTakesDamageEvent;

import java.util.List;

public class AnimationDirector {
    private final EventBus eventBus;
    private final List<Opponent> opponents;

    public AnimationDirector(List<Opponent> opponents) {
        this.eventBus = EventBus.getInstance();
        this.opponents = opponents;
        subscribe();
    }

    private void subscribe() {

        eventBus.subscribe(EnemyTakesDamageEvent.class, e -> {
            opponents.get(0).setAnimationState(EnemyAnimationState.HURT);
        });


        eventBus.subscribe(EnemyEffectAppliedEvent.class, e -> {

            System.out.println("reaching enemyeffect");

            opponents.get(0).setAnimationState(EnemyAnimationState.ATTACK);
        });


        eventBus.subscribe(EnemyDiedEvent.class, e -> {
            opponents.get(0).setAnimationState(EnemyAnimationState.DEATH);
        });
    }
}

