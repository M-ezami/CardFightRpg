package io.github.some_example_name.system;

import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.cards.cardRelated.parents.MonsterCard;
import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.data.CardContext;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.Effect;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.events.event.phaseEvents.MonsterPlayedEvent;
import io.github.some_example_name.events.utilities.EventBus;

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
        if (!ctx.isMonsterField()) System.out.println("monster is empty");

        if (ctx.target() == null) {
            System.out.println("target is empty");
        }

        if (manaCheck(card)) {
            System.out.println("mana is empty");
        }

        if (!ctx.isMonsterField() && ctx.target() == null || manaCheck(card)) {
            System.out.println("something is wrong");
            return;
        }


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
        if(player.getCurrentMana() < card.getManaCost()) return;
        if(card.getMonster().getHealth()<=0) card.getMonster().setHealth(card.getMonster().getMaxHealth());
        player.getMonsters().add(card.getMonster());
        player.playCard(card);
        eventBus.emit(new MonsterPlayedEvent());

    }

    public void onSpellCardPlayed(SpellCard card) {
        if(player.getCurrentMana() < card.getManaCost()) return;

        for (Effect effect : card.getEffects()) {
            System.out.println("Applying effect: " + effect.getClass().getSimpleName());
            effect.apply(gameState);
        }

        player.playCard(card);
    }


}
