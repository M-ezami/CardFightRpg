package io.github.some_example_name.input;


import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.CardView;

import java.util.List;

public class DiscardInputHandler extends InputHandler {

    // we do need a better solution for seperating card selection and input
    // inputhandlers should not know that cards exist they should just know coordination and pass it to another system
    // that system should handle and based on boardview/boardlayout it should know whether a card has been selected or not
    // for example we pass coordinatins in touchdown and that system verifieyes whether a card has been selected or not if it has add it to a list or whatever
    // very much more scalable than this because we can use coordinations more efficient and pass them to systems
    // but I think that this is currently very nice easy and a working solution

    private final List<Card> selectedCards;

    public DiscardInputHandler(BoardView boardView, Viewport viewport, GameState gameState) {
        super(boardView, viewport);
        selectedCards = boardView.getSelectedCards();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);

        if (cardView == null) return false;

        Card card = cardView.getCard();
        if (card == null) return false;
        if (!selectedCards.contains(card)) {
            selectedCards.add(card);
            System.out.println("Selected Card: " + cardView);
        } else {
            selectedCards.remove(card);
        }

        return true;
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
