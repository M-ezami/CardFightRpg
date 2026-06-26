package io.github.some_example_name.input;

import io.github.some_example_name.view.BoardView;

public class EnemyTurnInputHandler extends InputHandler {

    public EnemyTurnInputHandler(BoardView boardView) {
        super(boardView);
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
    protected boolean onCardReleased() {
        return false;
    }
}
