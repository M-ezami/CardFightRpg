package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.CardContext;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.CardView;

public class SpellPhaseInputHandler extends InputHandler {


    // i think this shouldnt talk to boardview boardview should only be rendering
    // this should talk to some layoutData class about positions and update them and boardview shuld read it
    //but aint that bad
    public SpellPhaseInputHandler(BoardView boardView, Viewport viewport) {
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
