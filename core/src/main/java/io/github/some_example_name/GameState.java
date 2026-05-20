package io.github.some_example_name;
import io.github.some_example_name.Effects.Effect;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    // ---- Fields ----
    private final Player player;
    private final List<Opponent> opponents;
    private Opponent targetOpponent;
    private DeckState deckState;
    public Phase currentPhase;

    // ---- Constructor ----
    public GameState(Player player, List<Opponent> opponents) {
        this.player = player;
        this.opponents = opponents;
        this.targetOpponent = opponents.get(0);
        this.currentPhase = Phase.PLAYERTURN;
        this.deckState = new DeckState(player.getDeck());
    }

    // ---- Getters ----
    public Player getPlayer() {
        return player;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public List<Targatable> getTargets() {
        return new ArrayList<>(opponents); // computed on the fly
    }

    public Opponent getTargetOpponent() {
        return targetOpponent;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public DeckState getDeckState() {
        return deckState;
    }

    // ---- Setters ----
    public void setTargetOpponent(Opponent targetOpponent) {
        this.targetOpponent = targetOpponent;
    }

    public void setCurrentPhase(Phase phase) {
        this.currentPhase = phase;
    }

    // ---- Utility ----
    public Effect getCurrentEnemyEffect(Opponent opponent) {
        return opponent.getRandomEffect();
    }
}
