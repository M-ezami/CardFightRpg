package io.github.some_example_name;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.EventBus;

public class DrawSystem {

    //private final GameState gameState;
    private final EventBus eventBus;

    public DrawSystem() {
        this.eventBus = EventBus.getInstance();

    }

/*
    public void subcribe() {
        eventBus.subscribe(drawCard.class, event -> {
            drawCards(event.drawContext());
        });
    }

    private void drawCards(CardDrawContext cDCtx) {
    if(cDCtx.isMonsterField()) {}

    }

 */


}
