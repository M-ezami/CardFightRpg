package io.github.some_example_name.ui;

import io.github.some_example_name.entiteRelated.EnemyAnimationState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.targets.Targatable;
import io.github.some_example_name.events.event.EnemyDiedEvent;
import io.github.some_example_name.events.event.spellEffect;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.system.DamageEvent;

import java.util.List;

public class AnimationDirector {
    private final EventBus eventBus;
    private final List<Opponent> opponents;

    public AnimationDirector(List<Opponent> opponents) {
        this.eventBus = EventBus.getInstance();
        this.opponents = opponents;
        subscribe();
    }

    //could possibly rewrite into working for all targets
    private void hurtOrAttack(Targatable target) {
        if (target.isOpponent()) opponents.get(0).setAnimationState(EnemyAnimationState.HURT);
        else opponents.get(0).setAnimationState(EnemyAnimationState.ATTACK);
    }

    private void subscribe() {

        eventBus.subscribe(DamageEvent.class, e -> {
            hurtOrAttack(e.target());
        });
        eventBus.subscribe(spellEffect.class, e -> {
            hurtOrAttack(e.target());
        });

        eventBus.subscribe(EnemyDiedEvent.class, e -> {
            opponents.get(0).setAnimationState(EnemyAnimationState.DEATH);
        });
    }
}

