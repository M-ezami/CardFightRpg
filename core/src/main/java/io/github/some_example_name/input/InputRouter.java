package io.github.some_example_name.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.ui.Hud;
import io.github.some_example_name.view.BoardView;

import java.util.HashMap;
import java.util.Map;

public class InputRouter {

    private final EventBus eventBus;
    private final Map<RoundPhase, InputHandler> handlers = new HashMap<>();
    private final Viewport viewport;
    private final BoardView boardView;
    private final InputMultiplexer inputMultiplexer;
    private final Hud hud;
    private final GameState gameState;
    private  RoundPhase lastPhase;
    private InputHandler activeHandler;


    public InputRouter(Viewport viewport, BoardView boardView, Hud hud, GameState gameState) {
        this.eventBus = EventBus.getInstance();
        this.gameState = gameState;
        this.hud = hud;
        this.boardView = boardView;
        this.viewport = viewport;
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        debug();
    }


    public void debug() {
        if (Gdx.input.getInputProcessor() != null) {
            System.out.println("input processor found");
            System.out.println("ACTIVE INPUT PROCESSOR = " + Gdx.input.getInputProcessor());
        } else {
            System.out.println("input processor not found");
        }
    }
    public void update() {
        RoundPhase current = gameState.getRoundPhase();

        if (current != lastPhase) {
            setInputHandler(current);
            lastPhase = current;
        }
    }



    public void setInputHandler(RoundPhase phase) {
        inputMultiplexer.clear();
        lastPhase = phase;
        if (phase != RoundPhase.ENEMY_TURN) {
            inputMultiplexer.addProcessor(hud.getStage());
        }

        InputHandler newHandler = handlers.computeIfAbsent(phase, this::createHandler);

        if (activeHandler != null) {
            inputMultiplexer.removeProcessor(activeHandler);
        }
        activeHandler = newHandler;
        inputMultiplexer.addProcessor(activeHandler);

        // debug
        System.out.println("inputHandler was set" + phase.toString());
        if (inputMultiplexer.getProcessors().size == 0) {
            System.out.println("No input handler found");
        }
        System.out.println("ACTIVE HANDLER = " + newHandler);
        System.out.println("MULTIPLEX SIZE = " + inputMultiplexer.size());

    }

        // inputhandlers shouldnt know boardview the only reason they do right now is because of detecting where cards are
        // instead they should talk to the higher level which is card via gamestate
    private InputHandler createHandler(RoundPhase phase) {
        return switch (phase) {
            case SPELL_PHASE -> new SpellPhaseInputHandler(boardView,viewport);
            case MONSTER_PHASE ->  new MonsterPhaseInputHandler(boardView, viewport);
            case FIGHT_PHASE -> new FightInputHandler(boardView,viewport);
            case DISCARD_PHASE -> new DiscardInputHandler(boardView, viewport);
            case ENEMY_TURN -> new EnemyTurnInputHandler(boardView,viewport);
        };

    }


}
