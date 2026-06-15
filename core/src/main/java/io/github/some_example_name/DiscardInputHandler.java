package io.github.some_example_name;


import io.github.some_example_name.ui.BoardView;
import io.github.some_example_name.ui.CardView;

public class DiscardInputHandler extends InputHandler {


    public DiscardInputHandler(BoardView boardView) {
        super(boardView);
    }

    @Override
    protected boolean onCardTouched(CardView cardView) {
        return false;
    }

    @Override
    protected boolean onCardReleased() {
        return false;
    }



    @Override
    protected boolean dragCard() {
        return false;
    }


}
