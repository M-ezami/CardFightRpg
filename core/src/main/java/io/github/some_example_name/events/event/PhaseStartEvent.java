package io.github.some_example_name.events.event;

import io.github.some_example_name.businessLogic.RoundPhase;

public class PhaseStartEvent {

    private final RoundPhase roundPhase;
    private float roundTimer;

    public PhaseStartEvent(final RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
        System.out.println("new PhaseStartEvent" + roundPhase);
        if (roundPhase.equals(RoundPhase.ENEMY_TURN)) {
            roundTimer = 4f;
        }
    }

    public float getRoundTimer() {
        return roundTimer;
    }

    public RoundPhase getRoundPhase() {
        return roundPhase;
    }


}
