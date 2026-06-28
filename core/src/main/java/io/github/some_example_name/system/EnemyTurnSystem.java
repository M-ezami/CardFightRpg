package io.github.some_example_name.system;

import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.event.PhaseStartEvent;
import io.github.some_example_name.events.utilities.EventBus;

import java.util.List;

public class EnemyTurnSystem {
    private final EventBus eventBus;
    private final GameState gameState;
    private final Timer timer;

    public EnemyTurnSystem(GameState gameState) {
        this.eventBus = EventBus.getInstance();
        this.gameState = gameState;
        subscribe();
        timer = new Timer();
    }


    private void subscribe() {
        eventBus.subscribe(PhaseStartEvent.class, event -> {
            if (event.getRoundPhase() == RoundPhase.ENEMY_TURN) {
                enemyTurn(event.getRoundTimer());
            }
        });
    }

    private void enemyTurn(float timeGoal) {

        System.out.println("enemyturn is running");
        List<Opponent> opponents = gameState.getOpponents();
        for (Opponent opponent : opponents) {
            if (opponent.isDead()) {
                System.out.println("no opponent");
            }
            opponent.getRandomEffect().apply(gameState);

        }
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                eventBus.emit(new PhaseStartEvent(RoundPhase.SPELL_PHASE));
            }
        };
       Timer.schedule(task,timeGoal);



    }


}
