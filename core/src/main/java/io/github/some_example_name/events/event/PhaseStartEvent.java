package io.github.some_example_name.events.event;

import io.github.some_example_name.events.utilities.RoundPhase;

public class PhaseStartEvent {

    private final RoundPhase roundPhase;
    private float duration;

    public PhaseStartEvent(final RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
        System.out.println("new PhaseStartEvent" + roundPhase);
        if (roundPhase.equals(RoundPhase.ENEMY_TURN)) {
            duration = 1f;
        }
    }

    public float getDuration() {
        return duration;
    }

    public RoundPhase getRoundPhase() {
        return roundPhase;
    }


}
