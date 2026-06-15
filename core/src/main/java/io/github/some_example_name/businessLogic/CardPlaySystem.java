package io.github.some_example_name.businessLogic;

import io.github.some_example_name.CardContext;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardParents.MonsterCard;
import io.github.some_example_name.cards.cardParents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.Effect;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.screens.MonsterPlayedEvent;


public class CardPlaySystem {
    private final EventBus eventBus;
    private final GameState gameState;
    private final Player player;

    public CardPlaySystem(GameState gameState) {
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.eventBus = EventBus.getInstance();
        subscribe();
    }

    public void subscribe() {
        eventBus.subscribe(CardPlayedEvent.class, event -> onCardPlayed(event.cardContext()));
    }

    public void onCardPlayed(CardContext ctx) {
        Card card = ctx.card();
        if (!ctx.isMonsterField() && ctx.target() == null || manaCheck(card)) return;

        switch (card.getCardType()) {
            case SPELL -> {
                if (ctx.target() != null) {
                    onSpellCardPlayed((SpellCard) card);
                }
            }
            case MONSTER -> {
                if (ctx.isMonsterField()) {
                    onMonsterCardPlayed((MonsterCard) card);
                }
            }
            default -> System.out.println(CardPlaySystem.class + " : card needs a type");
        }
    }



    public boolean manaCheck(Card card) {
        if (player.getCurrentMana() < card.getManaCost()) {
            System.out.println("Not enough mana!");
            return true;
        }
        return false;
    }

    public void onMonsterCardPlayed(MonsterCard card) {
        //could maybe emit a new monstercardplayed event
        // perhaps pass in monster
        player.getMonsters().add(card.getMonster());
        player.spendMana(card.getManaCost());
        player.getDeck().discard(card);
        eventBus.emit(new MonsterPlayedEvent());
    }


    public void onSpellCardPlayed(SpellCard card) {
        Player player = gameState.getPlayer();

        for (Effect effect : card.getEffects()) {
            System.out.println("Applying effect: " + effect.getClass().getSimpleName());
            effect.apply(gameState);
        }

        player.spendMana(card.getManaCost());
        player.getDeck().discard(card);
    }


}
