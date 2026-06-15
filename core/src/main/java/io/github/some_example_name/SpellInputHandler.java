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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);

        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView != null && cardView.getCardType() == CardType.SPELL) {
            selectedCard = cardView;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (selectedCard == null) return false;
        System.out.println("touchDragged: " + touchPos.x + ", " + touchPos.y);
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);

        boardView.setDraggedCard(
            new DraggedCard(selectedCard, touchPos.x, touchPos.y)
        );

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (selectedCard == null) return false;

        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);

        Opponent opponent = boardView.getOpponentView()
            .getOpponentAt(touchPos.x, touchPos.y);

        Card card = selectedCard.getCard();

        boolean monsterFieldClicked =
            boardView.monsterViewDimensions().contains(touchPos.x, touchPos.y);

        eventBus.emit(new CardPlayedEvent(
            new CardContext(monsterFieldClicked, opponent, card)
        ));

        selectedCard = null;
        boardView.setDraggedCard(null);

        return true;
    }
}
