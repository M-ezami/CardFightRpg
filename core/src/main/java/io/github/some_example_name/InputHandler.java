package io.github.some_example_name;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.ui.BoardView;
import io.github.some_example_name.ui.CardView;
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
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);

        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView == null) return false;
        return onCardTouched(cardView);
    }

    private void pixelsToWorld(int screenX, int screenY) {
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);
    }


    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer){
        pixelsToWorld(screenX, screenY);
        return dragCard();
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button){
        pixelsToWorld(screenX, screenY);
       return onCardReleased();

    }

    protected abstract boolean dragCard();
    protected abstract boolean onCardTouched(CardView cardView);
    protected abstract boolean onCardReleased();

}
