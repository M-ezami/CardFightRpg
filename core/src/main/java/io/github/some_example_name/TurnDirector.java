/*
package io.github.some_example_name;

import com.badlogic.gdx.utils.Timer;


import io.github.some_example_name.cards.cardParents.MonsterCard;
import io.github.some_example_name.cards.cardParents.SpellCard;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.*;

// (PlayerTurnStartEvent());  SUBSCRIBERS ARE: Hud: Showbanner with player, CombatScreen : RefreshHand
// new PlayerTurnReadyEvent(); SUBSCRIBERS ARE: CombatScreen: refreshHand();, hud.hideBanner();


public class TurnDirector {

    private final CombatSystem combatSystem;

    private final EventBus eventBus;

    public TurnDirector(CombatSystem combatSystem, EventBus eventBus) {
        this.eventBus = eventBus;
        this.combatSystem = combatSystem;
    }

    // ---- Player intent ----

    public void onPlayMonsterCard(MonsterCard card) {
        System.out.println("reached");
        boolean played =  combatSystem.onPlayMonsterCard(card);
        System.out.println(played + " s" + card);
    }

    public void onPlaySpellCard(SpellCard spellCard, Targatable target) {
        boolean played = combatSystem.onPlaySpellCard(spellCard, target);
        System.out.println(played);
        if (played) {
            eventBus.emit(new CardPlayedEvent(spellCard, target));
            //banner show damage of player subscribe to this
        }
        if (combatSystem.checkEnemyDeath()) {
            eventBus.emit(new EnemyDiedEvent());
        }

    }


    // ---- Turn sequencing ----

    public void onPlayerEndTurn() {
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

    //move to delta
    private static void delay(float seconds, Runnable action) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                action.run();
            }
        }, seconds);
    }
}
*/
