package io.github.some_example_name.system;

import io.github.some_example_name.data.Cards;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.event.phaseEvents.PlayerTurnBeginEvent;
import io.github.some_example_name.events.utilities.EventBus;

public class PlayerSystem {

    private final Player player;
    private final EventBus eventBus;
    private final Cards cards;

    public PlayerSystem(Player player) {
        this.player = player;
        this.cards = player.getCards();
        this.eventBus = EventBus.getInstance();
        subscribe();

    }


    private void subscribe(){
        eventBus.subscribe(PlayerTurnBeginEvent.class,event -> {
            atStartTurn();
        });
    }

    private void atStartTurn(){
        this.cards.drawHand();
    }

}
