package io.github.some_example_name.inputs.InputController;

import io.github.some_example_name.view.CardView;

public class CardController implements Controller<CardView> {

    private CardView selectedCard;

    // Mouse/finger position during dragging
    private float pressX;
    private float pressY;

    // Card position when dragging started
    private float startX;
    private float startY;

    private boolean dragging;

    @Override
    public void touchDown(CardView card, float x, float y) {
        selectedCard = card;

        // Remember the card's original position
        startX = card.getX();
        startY = card.getY();

        // Remember the initial mouse/finger position
        pressX = x;
        pressY = y;

        dragging = false;

        card.moveBy(0, 100f);
    }

    @Override
    public void touchDragged(CardView card, float x, float y) {
        float dx = x - pressX;
        float dy = y - pressY;

        if (!dragging && Math.sqrt(dx * dx + dy * dy) > 10) {
            dragging = true;
        }

        if (dragging) {
            card.moveBy(dx, dy);

            pressX = x;
            pressY = y;
        }
    }

    @Override
    public void touchUp(CardView card, float x, float y) {

        if (dragging) {
            System.out.println("Dragged!");

            // Return the card to where it was before dragging
            card.setPosition(startX, startY);
        } else {
            System.out.println("Clicked!");
        }

        dragging = false;
        selectedCard = null;
    }
}
