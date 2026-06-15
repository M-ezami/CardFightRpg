package io.github.some_example_name.data;

import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.Targatable;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    // if we ever decide that deck or monsters live outside of combat they should bem oved to player

    private final Player player;
    private final List<Opponent> opponents;
    private Opponent targetOpponent;
    private RoundPhase roundPhase;

    public GameState(Player player, List<Opponent> opponents) {
        this.player = player;
        this.opponents = opponents;
        this.targetOpponent = opponents.get(0);

    }

    public List<Monster> getMonsters() {
        return player.getMonsters();

    }

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


    public Cards getDeck() {
        return player.getDeck();
    }

    public List<Card> getHand() {
        return player.getDeck().getHand();
    }
}
