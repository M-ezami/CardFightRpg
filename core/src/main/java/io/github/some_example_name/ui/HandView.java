package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.DraggedCard;
import io.github.some_example_name.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class HandView {

    private final List<CardView> cardViews = new ArrayList<>();
    private final Assets assets;

    private DraggedCard draggedCard;

    public HandView(Assets assets) {
        this.assets = assets;
    }

    public void setDraggedCard(DraggedCard draggedCard) {
        this.draggedCard = draggedCard;
    }

    public void update(List<Card> hand) {
        cardViews.clear();
        for (Card card : hand) {
            CardView cv = new CardView(card, assets);
            cardViews.add(cv);
        }
    }

    // move to a laayout class so we dont calc position every time in draw
    public void draw(SpriteBatch batch,
                     float x, float y,
                     float width, float height,
                     DraggedCard draggedCard) {
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
            boolean isDragged =
                draggedCard != null &&
                    card.getCard() == draggedCard.draggedCard().getCard();

            card.setBounds(baseX, baseY, cardWidth, height);

            if (isDragged) {
                System.out.println("draggedCard: " + draggedCard);
                draggedCardView = card;
                draggedCardWidth = cardWidth;
            } else {
                card.draw(batch);
            }
        }

        // draw dragged card last so it renders on top
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

