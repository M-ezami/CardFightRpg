package io.github.some_example_name.ui;

import io.github.some_example_name.entiteRelated.EasyEnemy;
import io.github.some_example_name.entiteRelated.EnemyAnimationState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.events.event.EnemyDiedEvent;
import io.github.some_example_name.events.event.EnemyEffectAppliedEvent;
import io.github.some_example_name.events.utilities.EventBus;

import java.util.List;

public class AnimationDirector {
    public AnimationDirector(EventBus eventBus, List<Opponent> opponents) {

        eventBus.subscribe(CardPlayedEvent.class, e -> {
            if (e.cardContext().target() != null) {
                ((EasyEnemy) e.cardContext().target()).setAnimationState(EnemyAnimationState.HURT);
            }
        });

        eventBus.subscribe(EnemyEffectAppliedEvent.class, e -> {
            opponents.get(0).setAnimationState(EnemyAnimationState.ATTACK);
        });

        // registered once, not nested
        eventBus.subscribe(EnemyDiedEvent.class, e -> {
            opponents.get(0).setAnimationState(EnemyAnimationState.DEATH);
        });
    }
}
