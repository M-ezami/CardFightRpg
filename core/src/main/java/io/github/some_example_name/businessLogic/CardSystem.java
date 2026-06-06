package io.github.some_example_name.businessLogic;

import io.github.some_example_name.CardContext;
import io.github.some_example_name.cards.cardParents.CardType;
import io.github.some_example_name.data.GameState;

import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.events.EventBus;

public class CardSystem {
    private final EventBus eventBus;
    private final GameState gameState;

    public CardSystem(GameState gameState, EventBus eventBus) {
        this.gameState = gameState;
        this.eventBus = eventBus;
        subscribe();
    }

    public void subscribe() {
        eventBus.subscribe(CardPlayedEvent.class, event -> {
            onCardPlayed(event.cardContext());
        });
    }

    // this is kinda ugly but works hopefully
    // also if a spell can be played without target then this makes no freaking sense

    public void onCardPlayed(CardContext ctx) {

        CardType cardType = ctx.card().getCardType();
        if (!ctx.isMonsterField() && ctx.target() == null) return;
        switch (cardType) {
            case SPELL:
                if (ctx.target() != null) {
                    OnSpellCardPlayed();
                    System.out.println("Playing spell");
                    break;
                }
            case MONSTER:
                if(ctx.isMonsterField()) {
                    onMonsterCardPlayed();
                }
            case null:
                System.out.println(CardSystem.class.toString() + " : " + "card needs a type");
                break;
        }
    }

    public void onMonsterCardPlayed() {

    }


    public void OnSpellCardPlayed() {

    }


}
