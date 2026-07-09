package io.github.some_example_name.system;

import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.event.phaseEvents.FightEvent;
import io.github.some_example_name.events.utilities.EventBus;

public class CombatSystem {

    private final GameState gameState;
    private final EventBus eventBus;
    private final Player player;

    private Monster playerMonster;
    private Opponent targetOpponent;

    public CombatSystem(GameState gameState) {
        this.gameState = gameState;
        this.eventBus = EventBus.getInstance();
        this.player = gameState.getPlayer();
        subscribe();
    }

    private void subscribe(){
        eventBus.subscribe(FightEvent.class,event->{
            this.targetOpponent = event.targetOpponent();
            this.playerMonster = event.playerMonster();
           attack();
        });
    }
    private void attack(){
        deathCheck();
        this.targetOpponent.takeDamage(this.playerMonster.getAttack());
        this.player.spendMana(1);
        this.targetOpponent= null;
        this.playerMonster = null;

    }

    private void deathCheck(){
        if (this.targetOpponent.getHealth()<=0){
            gameState.getOpponents().remove(this.targetOpponent);
        }
    }




}
