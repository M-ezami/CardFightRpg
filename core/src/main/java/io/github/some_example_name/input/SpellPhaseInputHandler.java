package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.view.BoardView;

public class SpellPhaseInputHandler extends InputHandler {


    // i think this shouldnt talk to boardview boardview should only be rendering
    // this should talk to some layoutData class about positions and update them and boardview shuld read it
    //but aint that bad
    public SpellPhaseInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }


    @Override
    public boolean touchDown() {
        return touchDownOnMonster() || touchDownOnCard();
    }


    @Override
    public boolean touchDragged() {
        return dragCard() || dragAttack();
    }


    @Override
    protected boolean touchUp() {
        return touchUpOnCard() || touchUpOnMonster();
    }
}
