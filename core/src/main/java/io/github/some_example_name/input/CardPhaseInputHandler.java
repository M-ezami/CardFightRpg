package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.CardContext;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.CardView;

public class CardPhaseInputHandler extends InputHandler {

    private CardView selectedCard = null;

    // i think this shouldnt talk to boardview boardview should only be rendering
    // this should talk to some layoutData class about positions and update them and boardview shuld read it
    //but aint that bad
    public CardPhaseInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView);
        this.viewport = viewport;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView == null) return false;
        selectedCard = cardView;
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pixelsToWorld(screenX, screenY);
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
        boolean isMonsterClicked = boardView.monsterViewDimensions().contains(touchPos.x, touchPos.y);

        eventBus.emit(new CardPlayedEvent(
            new CardContext(isMonsterClicked, opponent, card)));
        boardView.setDraggedCard(null);
        selectedCard = null;
        return true;
    }
}
