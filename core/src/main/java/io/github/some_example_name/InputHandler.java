package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.ui.BoardView;
import io.github.some_example_name.ui.HandView;

public abstract class InputHandler extends InputAdapter {

    protected BoardView boardView;
    protected Vector2 touchPos = new Vector2();
    protected HandView handView;
    protected Viewport viewport;
    protected EventBus eventBus;

    public InputHandler(BoardView boardView) {
        this.boardView = boardView;
        this.handView = boardView.getHandView();
        this.eventBus = EventBus.getInstance();
    }

    @Override
    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

    @Override
    public abstract boolean touchDragged(int screenX, int screenY, int pointer);

    @Override
    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);
}
