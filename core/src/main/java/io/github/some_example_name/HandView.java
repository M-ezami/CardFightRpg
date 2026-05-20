package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class HandView {
    private List<CardView> cardViews = new ArrayList<>();
    private Assets assets;


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


    public void draw(SpriteBatch batch, CardView selectedCard) {
        for (int i = 0; i < cardViews.size(); i++) {
            CardView card = cardViews.get(i);
            float x = 1f + i * 2.2f;
            float y = 0.5f;

            if (card == selectedCard) {
                y += 0.5f;
                batch.setColor(1f, 1f, 0.7f, 1f);
            }

            card.draw(batch, x, y);
            batch.setColor(Color.WHITE);
        }
    }



    public List<CardView> getCardViews() {
        return cardViews;
    }
}

