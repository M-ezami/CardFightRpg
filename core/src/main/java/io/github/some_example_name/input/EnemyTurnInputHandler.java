package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.view.BoardView;

public class EnemyTurnInputHandler extends InputHandler {

    public EnemyTurnInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView,viewport);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    protected boolean touchUp() {
        return false;
    }


}
