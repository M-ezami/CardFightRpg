package io.github.some_example_name.data;

import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.enitites.Opponent;
import io.github.some_example_name.enitites.Player;
import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    // if we ever decide that deck or monsters live outside of combat they should bem oved to player

    private final Player player;
    private final List<Opponent> opponents;
    private final List<Card> selectedCards;
    private RoundPhase roundPhase = RoundPhase.SPELL_PHASE;
    private static GameState instance;

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

    public static void start() {
        if (instance != null) {
            throw new IllegalStateException("GameState is already active");
        }

    }

    public static GameState get() {
        if (instance == null) {
            throw new IllegalStateException("GameState has not been started");
        }

        return instance;
    }

    public static void end() {
        instance = null;
    }


    public Player getPlayer() {
        return player;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }


    public Cards getCards() {
        return player.getCards();
    }

    public List<Card> getHand() {
        return player.getCards().getHand();
    }

    public List<Card> getTempSpellFieldCards(){
        return player.getCards().getTempFieldSpellCards();
    }

    public List<Card> getTempMonsterFieldSpellCards(){
        return player.getCards().getTempFieldMonsterSpellCards();
    }

}
