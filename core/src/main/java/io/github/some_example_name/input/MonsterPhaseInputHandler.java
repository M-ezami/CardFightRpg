package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.view.BoardView;

public class MonsterPhaseInputHandler extends InputHandler {


    public MonsterPhaseInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }

    @Override
    public boolean touchDown() {
        return touchDownOnCard() || touchDownOnMonster();
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
