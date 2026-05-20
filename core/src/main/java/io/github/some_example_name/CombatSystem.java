package io.github.some_example_name;

import io.github.some_example_name.Effects.Effect;
import java.util.List;

public class CombatSystem {

    // ---- Fields ----
    private final GameState gameState;

    // ---- Constructor ----
    public CombatSystem(GameState gameState) {
        this.gameState = gameState;
    }

    // ---- Getters ----
    public GameState getGameState() {
        return gameState;
    }

    public List<Targatable> getOpponents() {
        return gameState.getTargets();
    }

    public List<Card> getPlayerHand() {
        return gameState.getDeckState().getHand();
    }

    // ---- Setters ----
    public void setSelectedOpponent(Targatable target) {
        // safe cast — only opponents appear in the opponent list
        gameState.setTargetOpponent((Opponent) target);
    }

    // ---- Turn flow ----

    public void startPlayerTurn() {
        gameState.setCurrentPhase(Phase.PLAYERTURN);
        gameState.getPlayer().resetMana();
        gameState.getDeckState().drawHand();

    }



    public void startEnemyTurn() {
        gameState.getDeckState().discardHand();



        for (Opponent opponent : gameState.getOpponents()) {
            gameState.getCurrentEnemyEffect(opponent).apply(gameState);

        }


        System.out.println(gameState.getPlayer().getHealth());
    }

    // ---- Card actions ----
    public void onPlayCard(Card card) {
        Player player = gameState.getPlayer();
        if (player.getCurrentMana() < card.getManaCost()) {
            System.out.println("Not enough mana!");
            return;
        }
        for (Effect effect : card.getEffects()) {
            System.out.println("Applying effect: " + effect.getClass().getSimpleName());
            effect.apply(gameState);
        }
        player.spendMana(card.getManaCost());
        gameState.getDeckState().discard(card);
        System.out.println("discarded " + card.getName());
        checkEnemyDeath();
    }

    // ---- Death check ----
    public void checkEnemyDeath() {
        Opponent target = gameState.getTargetOpponent();
        if (target == null) return;
        if (!target.isDead()) return;
        gameState.setTargetOpponent(null);
        gameState.getOpponents().remove(target);
        System.out.println("Enemy defeated!");
    }
}
