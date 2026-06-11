package io.github.some_example_name.businessLogic;

import io.github.some_example_name.events.EventBus;

public class TurnSystem {

    private final EventBus eventBus;
    private RoundPhase currentRoundPhase;

    // subcribes to events that advance phases

    public TurnSystem(EventBus eventBus) {
        this.eventBus = eventBus;
        this.currentRoundPhase = RoundPhase.DRAW_PHASE;
    }

    public void subscribe() {

    }


    public void advancePhase() {
        switch (currentRoundPhase) {
            case DRAW_PHASE -> currentRoundPhase = RoundPhase.SPELL_PHASE;
            case SPELL_PHASE -> currentRoundPhase = RoundPhase.PLAY_PHASE;
            case PLAY_PHASE -> currentRoundPhase = RoundPhase.FIGHT_PHASE;
            case FIGHT_PHASE -> currentRoundPhase = RoundPhase.ENEMY_TURN;
            case ENEMY_TURN -> currentRoundPhase = RoundPhase.DRAW_PHASE;
        }
        onEnter();
    }

    private void onEnter() {

    }


}
