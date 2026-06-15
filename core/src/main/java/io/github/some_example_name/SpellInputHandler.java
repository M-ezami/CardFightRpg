package io.github.some_example_name;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardParents.CardType;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.ui.BoardView;
import io.github.some_example_name.ui.CardView;

public class SpellInputHandler extends InputHandler {

    private CardView selectedCard = null;

    // i think this shouldnt talkt o boardview boardview should only be rendering
    // this should talk to some layoutdata class about positions and update them and boardview shuld read it
    //but aint that bad
    public SpellInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView);
        this.viewport = viewport;
    }

    @Override
    protected boolean onCardTouched(CardView cardView) {
        if (cardView.getCardType() == CardType.SPELL) {
            selectedCard = cardView;
            return true;
        }
        return false;
    }

    @Override
    public boolean dragCard() {

        if (selectedCard == null) return false;
        System.out.println("touchDragged: " + touchPos.x + ", " + touchPos.y);


        boardView.setDraggedCard(
            new DraggedCard(selectedCard, touchPos.x, touchPos.y)
        );

        return true;
    }


    @Override
    protected boolean onCardReleased() {
        if (selectedCard == null) return false;

        Card card = selectedCard.getCard();
        Opponent opponent = boardView.getOpponentView().getOpponentAt(touchPos.x, touchPos.y);

        eventBus.emit(new CardPlayedEvent(
            new CardContext(false, opponent, card)));
        boardView.setDraggedCard(null);
        selectedCard = null;
        return true;
    }
}
