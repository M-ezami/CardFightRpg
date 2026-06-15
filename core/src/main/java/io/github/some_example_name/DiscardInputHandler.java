package io.github.some_example_name;


import io.github.some_example_name.ui.BoardView;

public class DiscardInputHandler extends InputHandler {


    public DiscardInputHandler(BoardView boardView) {
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
