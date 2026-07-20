package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.view.BoardView;

public class EnemyTurnInputHandler extends InputHandler {

    public EnemyTurnInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }

    @Override
    public boolean touchDown() {
        return false;
    }

    @Override
    public boolean touchDragged() {
        return false;
    }

    @Override
    protected boolean touchUp() {
        return false;
    }


}
