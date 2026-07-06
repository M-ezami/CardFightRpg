package io.github.some_example_name.system;

import io.github.some_example_name.data.Cards;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.utilities.EventBus;

public class DrawSystem {

    private final EventBus eventBus;
    private final GameState gameState;
    private final Cards deck;

    public DrawSystem(GameState gameState) {
        this.gameState = gameState;
        this.eventBus = EventBus.getInstance();
        this.deck = this.gameState.getPlayer().getCards();
    }


    private void subscribe(){

    }


}
