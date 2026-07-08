package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.data.CardContext;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.events.event.phaseEvents.FightEvent;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.CardView;
import io.github.some_example_name.view.DraggedMonster;
import io.github.some_example_name.view.MonsterView;

public class MonsterPhaseInputHandler extends InputHandler {

    private CardView selectedCard;
    private MonsterView selectedMonster;

    public MonsterPhaseInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);

        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView != null && cardView.getCardType() != CardType.SPELL) {
            selectedCard = cardView;
            selectedMonster = null;
            return true;
        }

        MonsterView monsterView = monsterFieldView.getMonsterAtPosition(touchPos.x, touchPos.y);
        if (monsterView != null) {
            selectedMonster = monsterView;
            selectedCard = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pixelsToWorld(screenX, screenY);

        if (selectedCard != null) {
            boardView.setDraggedCard(
                new DraggedCard(selectedCard, touchPos.x, touchPos.y)
            );
            return true;
        }

        if (selectedMonster != null) {
            boardView.setDraggedMonster(
                new DraggedMonster(selectedMonster, touchPos.x, touchPos.y)
            );
            return true;
        }

        return false;
    }

    @Override
    protected boolean touchUp() {
        if (selectedCard != null) {
            Card card = selectedCard.getCard();
            boolean isInMonsterField =
                boardView.monsterViewDimensions().contains(touchPos.x, touchPos.y);

            eventBus.emit(new CardPlayedEvent(new CardContext(isInMonsterField, null, card)));

            boardView.setDraggedCard(null);
            selectedCard = null;
            return true;
        }

        if (selectedMonster != null && boardView.getOpponentView().getOpponentAt(touchPos.x, touchPos.y) != null) {
            eventBus.emit(new FightEvent());
            boardView.setDraggedMonster(null);
            selectedMonster = null;
            return true;
        }

        return false;
    }
}
