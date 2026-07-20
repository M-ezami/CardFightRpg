package io.github.some_example_name.system;

import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.targets.Targatable;
import io.github.some_example_name.events.event.EnemyEffectAppliedEvent;
import io.github.some_example_name.events.event.phaseEvents.EnemyTurnStartEvent;
import io.github.some_example_name.events.event.phaseEvents.FightEvent;
import io.github.some_example_name.events.event.phaseEvents.PlayerTurnBeginEvent;
import io.github.some_example_name.events.utilities.EventBus;

import java.util.List;

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

    private void subscribe() {
        eventBus.subscribe(EnemyTurnStartEvent.class, event -> {
            enemyTurn();
        });
        eventBus.subscribe(FightEvent.class, event -> {
            this.targetOpponent = event.targetOpponent();
            System.out.println("target opponent is " + this.targetOpponent);
            this.playerMonster = event.playerMonster();
            attack();
        });
    }

    private void enemyTurn() {
        System.out.println("enemys turn is running");
        List<Opponent> opponents = gameState.getOpponents();
        int i = 0;
        for (Opponent opponent : opponents) {
            i++;
            if (opponent.isDead()) {
                System.out.println("no opponent" + i);
            }
            for (Targatable target : opponent.getRandomEffect().getTargetingStrategy().getTargets(gameState)) {
                System.out.println("before" + target.getHealth());
            }
            opponent.getRandomEffect().apply(gameState);
            deathCheck(opponent.getRandomEffect().getTargetingStrategy().getTargets(gameState));
            for (Targatable target : opponent.getRandomEffect().getTargetingStrategy().getTargets(gameState)) {
                System.out.println("after" + target.getHealth());
            }
            eventBus.emit(new EnemyEffectAppliedEvent());

        }
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                eventBus.emit(new PlayerTurnBeginEvent());
            }
        };
        float timeGoal = 2f;
        Timer.schedule(task, timeGoal);


    }

    private void attack() {
        System.out.println("opponent health before" + targetOpponent.getHealth());
        if (this.player.getMana() > 0) {
            this.targetOpponent.takeDamage(this.playerMonster.getAttack());
            System.out.println("our monster damage amount" + this.playerMonster.getAttack());
            System.out.println("opponent health after" + targetOpponent.getHealth());
            this.player.spendMana(1);
            eventBus.emit(new EnemyTakesDamageEvent(targetOpponent));
            deathCheck(List.of(this.targetOpponent));
            this.targetOpponent = null;
            this.playerMonster = null;
        }
    }

    private void deathCheck(List<Targatable> targatables) {
        for (Targatable targatable : targatables) {
            if (targatable.getHealth() <= 0)
                gameState.remove(targatable);
        }

    }


}
