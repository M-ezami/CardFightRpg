package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;

public class HandView {
    // something weird with these 3 lists can be simplified
    private final List<Card> selectedCards;
    private final List<CardView> cardViews = new ArrayList<>();
    private final List<Card> hand;
    private final Assets assets;
    private final GameState gameState;
    private boolean isDiscardPhase;

    public HandView(Assets assets, GameState gameState) {
        this.gameState = gameState;
        this.assets = assets;
        this.hand = gameState.getHand();
        this.selectedCards = gameState.getSelectedCards();
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }



    private void updateRoundPhase() {
        RoundPhase roundPhase = gameState.getRoundPhase();
        isDiscardPhase = roundPhase.equals(RoundPhase.DISCARD_PHASE);
    }


    public void update() {
        cardViews.clear();
        for (Card card : hand) {
            CardView cv = new CardView(card, assets);
            cardViews.add(cv);
        }
        updateRoundPhase();
    }


    // this method is so ugly it makes me cry and needs refactoring
    public void draw(SpriteBatch batch, float x, float y, float width, float height, DraggedCard draggedCard) {
        if (cardViews.isEmpty()) return;
        float spacing = 1f;
        float totalSpacing = spacing * (cardViews.size() - 1);
        float cardWidth = (width - totalSpacing) / cardViews.size();

        CardView draggedCardView = null;
        float draggedCardWidth = cardWidth;

        for (int i = 0; i < cardViews.size(); i++) {
            CardView card = cardViews.get(i);
            float baseX = x + i * (cardWidth + spacing);
            float baseY = y;

            boolean isDragged = draggedCard != null && card.getCard() == draggedCard.draggedCardView().getCard();
            if (isDiscardPhase) {
                baseY = y + 2f;
                isDragged = false;
                if (selectedCards.contains(card.getCard())) {
                    System.out.println("Selected Card found");
                    System.out.println(selectedCards.size());
                    baseY += 2f;
                } else {
                    System.out.println(selectedCards.size());
                    System.out.println("Selected Card not found");

                }
            }
            card.setBounds(baseX, baseY, cardWidth, height);

            if (isDragged) {
                draggedCardView = card;
                draggedCardWidth = cardWidth;
            } else {
                card.draw(batch);
            }
        }


        if (draggedCardView != null) {
            draggedCardView.setBounds(draggedCard.x(), draggedCard.y(), draggedCardWidth, height);
            draggedCardView.draw(batch);
        }

    }

    public CardView getCardAtPosition(float x, float y) {
        for (CardView cv : cardViews) {
            if (cv.contains(x, y)) {
                return cv;
            }
        }
        return null;
    }


    public List<CardView> getCardViews() {
        return cardViews;
    }
}

