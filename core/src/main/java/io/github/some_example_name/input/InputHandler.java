package io.github.some_example_name.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.data.CardContext;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.events.event.CardPlayedEvent;
import io.github.some_example_name.events.event.phaseEvents.FightEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.view.*;

public abstract class InputHandler extends InputAdapter {

    protected BoardView boardView;
    protected Vector2 touchPos = new Vector2();
    protected HandView handView;
    protected Viewport viewport;
    protected EventBus eventBus;
    protected MonsterFieldView monsterFieldView;
    public CardView selectedCard;
    public MonsterView selectedMonster;
    public GameState gameState;

    public InputHandler(BoardView boardView, Viewport viewport) {
        this.viewport = viewport;
        this.boardView = boardView;
        this.handView = boardView.getHandView();
        this.eventBus = EventBus.getInstance();
        this.monsterFieldView = boardView.getMonsterFieldView();
    }

    protected void pixelsToWorld(int screenX, int screenY) {
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);
    }

    protected boolean dragCard() {
        if (selectedCard == null) return false;
        boardView.setDraggedCard(
            new DraggedCard(selectedCard, touchPos.x, touchPos.y)
        );
        return true;
    }

    protected boolean dragAttack() {
        if (selectedMonster != null) {
            boardView.setDraggedMonster(
                new DraggedMonster(selectedMonster, touchPos.x, touchPos.y)
            );
            return true;
        }
        return false;
    }


    protected boolean touchDownOnCard() {
        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView != null  ) {
            selectedCard = cardView;
            selectedMonster = null;
            return true;
        }
        return false;
    }

    protected boolean touchDownOnSpellPhaseCard() {
        CardView cardView = handView.getCardAtPosition(touchPos.x, touchPos.y);
        if (cardView != null && cardView.getCardType() == CardType.SPELL  ) {
            selectedCard = cardView;
            selectedMonster = null;
            return true;
        }
        return false;
    }


    protected boolean touchDownOnMonster() {
        MonsterView monsterView = monsterFieldView.getMonsterAtPosition(touchPos.x, touchPos.y);
        if (monsterView != null) {
            selectedMonster = monsterView;
            selectedCard = null;
            return true;
        }
        return false;
    }

    protected boolean touchUpOnMonster() {
        if (selectedMonster != null) {
            Opponent opponent =
                boardView.getOpponentView().getOpponentAt(touchPos.x, touchPos.y);

            if (opponent != null) {
                eventBus.emit(new FightEvent(selectedMonster.getMonster(), opponent));
            }

            boardView.setDraggedMonster(null);
            selectedMonster = null;
            return true;
        }

        return false;
    }

    protected boolean touchUpOnCard() {
        if (selectedCard == null) return false;
        Card card = selectedCard.getCard();
        Opponent opponent = boardView.getOpponentView().getOpponentAt(touchPos.x, touchPos.y);
        if(opponent != null && selectedCard.getCardType() == CardType.MONSTER) return false;
        boolean isInMonsterField = boardView.monsterViewDimensions().contains(touchPos.x, touchPos.y);

        eventBus.emit(new CardPlayedEvent(
            new CardContext(isInMonsterField, opponent, card)));
        boardView.setDraggedCard(null);
        selectedCard = null;
        return true;
    }


    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        return touchDown();
    }


    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        pixelsToWorld(screenX, screenY);
        return touchDragged();
    }


    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        return touchUp();

    }

    public abstract boolean touchDragged();

    public abstract boolean touchDown();

    protected abstract boolean touchUp();

}
