package io.github.some_example_name.businessLogic;

import io.github.some_example_name.PhaseStartEvent;

import io.github.some_example_name.events.EventBus;

public class TurnSystem {

    private final EventBus eventBus;
    private RoundPhase currentPhase = RoundPhase.SPELL_PHASE;

    public TurnSystem() {
        this.eventBus = EventBus.getInstance();

        eventBus.subscribe(AdvancePhaseEvent.class, e -> {
            advancePhase();
        });

        emitPhaseStart();
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
