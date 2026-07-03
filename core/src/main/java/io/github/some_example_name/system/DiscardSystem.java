package io.github.some_example_name.system;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.events.event.PhaseStartEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.ui.DiscardEvent;

public class DiscardSystem {
    private final EventBus eventBus;
    private final GameState gameState;

    public DiscardSystem(GameState gameState) {
        eventBus = EventBus.getInstance();
        this.gameState = gameState;
        subscribe();
    }

    private void subscribe() {
        eventBus.subscribe(DiscardEvent.class, e -> {
                eventBus.emit(new PhaseStartEvent(RoundPhase.ENEMY_TURN));
                for (Card card : gameState.getSelectedCards() )
                gameState.getPlayer().discardCard(card);
            }
        );
        gameState.getSelectedCards().clear();

    }
}
