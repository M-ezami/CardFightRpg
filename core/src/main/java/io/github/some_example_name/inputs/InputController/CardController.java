package io.github.some_example_name.inputs.InputController;

import io.github.some_example_name.cards.cardRelated.parents.Card;

import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.systems.CardSystem;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.CardView;

public class CardController implements Controller<CardView> {

    private CardView selectedCardView;
    private Card selectedCard;

    private float startX;
    private float startY;

    private final CardSystem cardSystem;
    private final BoardView boardView;

    public CardController(CardSystem cardSystem, BoardView boardView) {
        this.cardSystem = cardSystem;
        this.boardView = boardView;
    }

    @Override
    public void touchDown(CardView cardView, float x, float y) {
        if (selectedCardView != null) {
            selectedCardView.setPosition(startX, startY);
        }

        selectedCardView = cardView;
        this.selectedCard = selectedCardView.getCard();
        if (selectedCard instanceof SpellCard spellCard) {
//            boardView.highlight(cardSystem.getValidTargets(spellCard));
        }



        startX = cardView.getX();
        startY = cardView.getY();

        cardView.moveBy(0, 100f);
    }


    @Override
    public void touchUp(CardView card, float x, float y) {


    }
}
