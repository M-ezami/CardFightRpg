package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.HandView;
import io.github.some_example_name.view.MonsterFieldView;

public abstract class InputHandler extends InputAdapter {

    protected BoardView boardView;
    protected Vector2 touchPos = new Vector2();
    protected HandView handView;
    protected Viewport viewport;
    protected EventBus eventBus;
    protected MonsterFieldView monsterFieldView;


    public InputHandler(BoardView boardView) {
        this.boardView = boardView;
        this.handView = boardView.getHandView();
        this.eventBus = EventBus.getInstance();
        this.monsterFieldView = boardView.getMonsterFieldView();
    }

    void pixelsToWorld(int screenX, int screenY) {
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);
    }

    @Override
    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);


    @Override
    public abstract boolean touchDragged(int screenX, int screenY, int pointer);


    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        return onCardReleased();

    }


    protected abstract boolean onCardReleased();

}
