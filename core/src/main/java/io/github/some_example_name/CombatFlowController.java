package io.github.some_example_name;

import com.badlogic.gdx.utils.Timer;

public class CombatFlowController {
    private CombatSystem combatSystem;
    private CombatScreen ui;

    public CombatFlowController(CombatSystem combatSystem, CombatScreen ui) {
        this.combatSystem = combatSystem;
        this.ui = ui;
    }

    public void onEndTurn() {
        setState(Phase.ENEMY_INTRO);
    }

    private void setState(Phase phase) {
        switch (phase) {
            case ENEMY_INTRO:
                ui.showEnemyIntro();
                schedule(1f, () -> setState(Phase.ENEMYTURN));
                break;
            case ENEMYTURN:
                combatSystem.getGameState().setCurrentPhase(Phase.ENEMYTURN);
                combatSystem.startEnemyTurn();
                ui.onHandChanged();
                schedule(1f, () -> setState(Phase.PLAYERTURN));
                break;
            case PLAYERTURN:
                combatSystem.getGameState().setCurrentPhase(Phase.PLAYERTURN);
                combatSystem.startPlayerTurn();
                ui.onHandChanged();
                ui.showPlayerTurnBanner();
                schedule(1.5f, () -> {
                    ui.hideBanner();
                    ui.enableEndturn();
                });
                break;
        }
    }

    private void schedule(float seconds, Runnable action) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                action.run();
            }
        }, seconds);
    }
}
