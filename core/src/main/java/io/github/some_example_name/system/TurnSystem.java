package io.github.some_example_name.system;

import io.github.some_example_name.events.PhaseStartEvent;

import io.github.some_example_name.events.AdvancePhaseEvent;
import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.events.MonsterPlayedEvent;

public class TurnSystem {

    private final EventBus eventBus;
    private RoundPhase currentPhase = RoundPhase.SPELL_PHASE;

    public TurnSystem() {
        this.eventBus = EventBus.getInstance();

        subscribe();
        emitPhaseStart();
    }




    public void subscribe() {
        eventBus.subscribe(AdvancePhaseEvent.class, e -> {
            advancePhase();
        });
        eventBus.subscribe(MonsterPlayedEvent.class, e -> {
            if(currentPhase.equals(RoundPhase.SPELL_PHASE)) {
                currentPhase = RoundPhase.PLAY_PHASE;
            }
        });
    }

    public void advancePhase() {
        currentPhase = switch (currentPhase) {
            case DRAW_PHASE -> RoundPhase.SPELL_PHASE;
            case SPELL_PHASE -> RoundPhase.PLAY_PHASE;
            case PLAY_PHASE -> RoundPhase.FIGHT_PHASE;
            case FIGHT_PHASE -> RoundPhase.DISCARD_PHASE;
            case DISCARD_PHASE -> RoundPhase.ENEMY_TURN;
            case ENEMY_TURN -> RoundPhase.DRAW_PHASE;
        };

        emitPhaseStart();
    }

    private void emitPhaseStart() {
        eventBus.emit(new PhaseStartEvent(currentPhase));
    }


}
