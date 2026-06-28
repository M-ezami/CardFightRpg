package io.github.some_example_name.system;

import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.events.event.MonsterPlayedEvent;
import io.github.some_example_name.events.event.PhaseStartEvent;
import io.github.some_example_name.events.utilities.EventBus;

public class TurnSystem {

    private final EventBus eventBus;
    private static RoundPhase currentPhaseInstance = RoundPhase.SPELL_PHASE;

    public TurnSystem() {
        this.eventBus = EventBus.getInstance();
        subscribe();
        emitPhaseStart();
    }

    public static RoundPhase getRoundPhase(){
        return currentPhaseInstance;
    }

    public void subscribe() {

        eventBus.subscribe(MonsterPlayedEvent.class, e -> {
            if (currentPhaseInstance.equals(RoundPhase.SPELL_PHASE)) {
                currentPhaseInstance = RoundPhase.PLAY_PHASE;
            }
        });

    }

    private void emitPhaseStart() {
        eventBus.emit(new PhaseStartEvent(currentPhaseInstance));
        System.out.println(currentPhaseInstance + "hello");
    }


}
