package io.github.some_example_name.businessLogic;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardParents.MonsterCard;
import io.github.some_example_name.cards.cardParents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.Effect;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.Targatable;


public class CombatSystem {

    private final GameState gameState;

    public CombatSystem(GameState gameState) {
        this.gameState = gameState;
    }


    public void endPlayerTurn() {
        gameState.getDeckState().discardHand();
    }

    public void runEnemyTurn() {
        for (Opponent opponent : gameState.getOpponents()) {
            opponent.getRandomEffect().apply(gameState);
        }
        System.out.println("Player HP: " + gameState.getPlayer().getHealth());
    }

    public void startPlayerTurn() {
        gameState.getPlayer().resetMana();
        gameState.getDeckState().drawHand();
    }


    public boolean manaCheck(Card card) {
        Player player = gameState.getPlayer();

        if (player.getCurrentMana() < card.getManaCost()) {
            System.out.println("Not enough mana!");
            return true;
        }
        return false;
    }

    public boolean onPlaySpellCard(SpellCard card, Targatable target) {
        Player player = gameState.getPlayer();

        if (manaCheck(card)) return false;

        gameState.setTargetOpponent((Opponent) target);

        for (Effect effect : card.getEffects()) {
            System.out.println("Applying effect: " + effect.getClass().getSimpleName());
            effect.apply(gameState);
        }

        player.spendMana(card.getManaCost());
        gameState.getDeckState().discard(card);
        System.out.println("Discarded: " + card.getName());

        checkEnemyDeath();
        return true;
    }

    public boolean isOutOfMana() {
        return gameState.getPlayer().getCurrentMana() <= 0;
    }


    public boolean checkEnemyDeath() {
        Opponent target = gameState.getTargetOpponent();
        if (target == null || !target.isDead()) return false;
        gameState.setTargetOpponent(null);
        gameState.getOpponents().remove(target);

        System.out.println("Enemy defeated!");

        return true;
    }

    public boolean onPlayMonsterCard(MonsterCard card) {
        if (manaCheck(card)) return false;

        gameState.getMonsters().add(card.getMonster());
        gameState.getDeckState().discard(card);

        return true;
    }
}
