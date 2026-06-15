package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.ui.BoardView;

import java.util.HashMap;
import java.util.Map;

public class InputRouter {

    private final EventBus eventBus;
    private final Map<RoundPhase, InputHandler> handlers = new HashMap<>();
    private final Viewport viewport;
    private final BoardView boardView;
    private final InputMultiplexer inputMultiplexer;
    private InputHandler activeHandler;

    public InputRouter(Viewport viewport, BoardView boardView) {
        this.eventBus = EventBus.getInstance();
        this.boardView = boardView;
        this.viewport = viewport;
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        subscribe();
        if (Gdx.input.getInputProcessor() != null) {
            System.out.println("input processor found");
            System.out.println("ACTIVE INPUT PROCESSOR = " + Gdx.input.getInputProcessor());
        } else {
            System.out.println("input processor not found");
        }
    }

    public void subscribe() {
        eventBus.subscribe(PhaseStartEvent.class, event -> {
            RoundPhase phase = event.roundPhase();
            setInputHandler(phase);
        });
    }

    public void setInputHandler(RoundPhase phase) {

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


    private InputHandler createHandler(RoundPhase phase) {
        return switch (phase) {
            case DRAW_PHASE -> null;
            case SPELL_PHASE -> new SpellInputHandler(boardView, viewport);
            case PLAY_PHASE -> new PlayInputHandler(null);
            case FIGHT_PHASE -> null;
            case DISCARD_PHASE -> new DiscardInputHandler(null);
            case ENEMY_TURN -> null;
        };
    }


}
