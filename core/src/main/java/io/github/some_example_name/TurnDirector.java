package io.github.some_example_name;

import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.businessLogic.CombatSystem;
import io.github.some_example_name.data.Card;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.*;


public class TurnDirector {

    private final CombatSystem combatSystem;

    private final EventBus eventBus;

    public TurnDirector(CombatSystem combatSystem, EventBus eventBus) {
        this.eventBus = eventBus;
        this.combatSystem = combatSystem;
    }

    // ---- Player intent ----

    public void onPlayCard(Card card, Targatable target) {
        boolean played = combatSystem.playCard(card, target);
        if (played) {
            eventBus.emit(new CardPlayedEvent(card, target));
        }
        if (combatSystem.checkEnemyDeath()) {
            eventBus.emit(new EnemyDiedEvent());
        }
        if(combatSystem.isOutOfMana()) turnSequence();
    }

    // ---- Turn sequencing ----

    public void turnSequence() {
        combatSystem.endPlayerTurn();
        eventBus.emit(new EnemyTurnStartEvent());                 // visual: red banner, disable input

        delay(1f, () -> {
            combatSystem.runEnemyTurn();
            // show the effect that enemy has
            eventBus.emit(new EnemyEffectAppliedEvent());
            delay(1f, () -> {
                // visual: green banner

                eventBus.emit(new PlayerTurnStartEvent());
                delay(1.5f, () -> {
                    combatSystem.startPlayerTurn();
                    eventBus.emit(new PlayerTurnReadyEvent());   // visual: hide banner, enable input, refresh hand
                });
            });
        });
    }

    // ---- Internal ----

    private void delay(float seconds, Runnable action) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                action.run();
            }
        }, seconds);
    }
}
