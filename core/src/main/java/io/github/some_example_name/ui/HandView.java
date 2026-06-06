package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class HandView {
    private final List<CardView> cardViews = new ArrayList<>();
    private final Assets assets;


    public HandView(Assets assets) {
        this.assets = assets;
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
                     float x,
                     float y,
                     float width,
                     float height,
                     CardView selectedCard) {

        if (cardViews.isEmpty()) return;

        float spacing = 1f;

        float totalSpacing = spacing * (cardViews.size() - 1);
        float cardWidth = (width - totalSpacing) / cardViews.size();

        for (int i = 0; i < cardViews.size(); i++) {
            CardView card = cardViews.get(i);

            float drawX = x + i * (cardWidth + spacing);
            float drawY = y;

            if (card == selectedCard) {
                drawY += 0.5f;
                batch.setColor(1f, 1f, 0.7f, 1f);
            }else{
                batch.setColor(1f, 1f, 1f, 1f);
            }
            card.setBounds(drawX, drawY, cardWidth, height);

            card.draw(batch);
        }


    }


    public List<CardView> getCardViews() {
        return cardViews;
    }
}

