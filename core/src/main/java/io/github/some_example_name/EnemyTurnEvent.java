package io.github.some_example_name;

public class EnemyTurnEvent {
    private final Opponent opponent;

    public EnemyTurnEvent(Opponent opponent) {
        this.opponent = opponent;
    }

    public Opponent getEnemy() {
        return opponent;
    }
}
