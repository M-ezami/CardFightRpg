package io.github.some_example_name.data;

import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.targets.Targatable;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    // if we ever decide that deck or monsters live outside of combat they should bem oved to player

    private final Player player;
    private final List<Opponent> opponents;
    private final List<Card> selectedCards;
    private RoundPhase roundPhase = RoundPhase.SPELL_PHASE;

    public GameState(Player player, List<Opponent> opponents) {
        this.selectedCards = new ArrayList<>();
        this.player = player;
        this.opponents = opponents;
    }


    public RoundPhase getRoundPhase() {
        return roundPhase;
    }

    public void setRoundPhase(RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public List<Monster> getMonsters() {
        return player.getMonsters();

    }

    public void remove(Targatable targatable) {
        if(targatable instanceof Monster) {
            getMonsters().remove((Monster) targatable);
        }
        if(targatable instanceof Opponent) {
            opponents.remove((Opponent) targatable);
        }
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


    public Cards getDeck() {
        return player.getDeck();
    }

    public List<Card> getHand() {
        return player.getDeck().getHand();
    }
}
