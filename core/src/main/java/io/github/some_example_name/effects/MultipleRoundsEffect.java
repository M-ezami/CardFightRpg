package io.github.some_example_name.effects;

import io.github.some_example_name.events.event.phaseEvents.EnemyTurnStartEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.events.utilities.EventListener;

public class MultipleRoundsEffect {

        private final int specifiedTurns;
        private final EventBus eventBus;

        private EventListener<EnemyTurnStartEvent> listener;
        private int turnCounter = 0;

        public MultipleRoundsEffect(int specifiedTurns) {
            this.specifiedTurns = specifiedTurns;
            this.eventBus = EventBus.getInstance();
        }

        public void subscribe(Runnable action) {

            turnCounter = 0;

            listener = e -> {
                turnCounter++;

                action.run();

                if (turnCounter >= specifiedTurns) {
                    eventBus.unsubscribe(
                        EnemyTurnStartEvent.class,
                        listener
                    );
                }
            };

            eventBus.subscribe(
                EnemyTurnStartEvent.class,
                listener
            );
        }
    }
