package io.github.some_example_name.system;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.utilities.EventBus;

public class CombatSystem {

    private final GameState gameState;
    private final EventBus eventBus;


    public CombatSystem( GameState gameState) {
        this.gameState = gameState;
        this.eventBus = EventBus.getInstance();
    }




}
