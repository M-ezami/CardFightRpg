package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.view.BoardView;

public class FightInputHandler extends InputHandler {

    public FightInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }

    @Override
    public boolean touchDown() {
        return touchDownOnMonster();
    }

    @Override
    public boolean touchDragged() {
        return dragAttack();

    }

    @Override
    protected boolean touchUp() {
        return touchUpOnMonster();
    }
}
