//package io.github.some_example_name.view;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import io.github.some_example_name.cards.cardRelated.parents.Card;
//import io.github.some_example_name.data.GameState;
//import io.github.some_example_name.ui.Assets;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TempSpellCardsView {
//    private static final float CARD_VIEW_SCALE_VALUE= 0.3f;
//
//
//
//    private final GameState gameState;
//    private final List<Card> tempSpellCardsList;
//    private final Assets assets;
//    private final List<CardView> cardViews;
//
//
//    public TempSpellCardsView(Assets assets, GameState gameState) {
//        this.gameState = gameState;
//        this.assets = assets;
//        this.tempSpellCardsList = gameState.getTempSpellFieldCards();
//        this.cardViews = new ArrayList<>();
//    }
//
//    public void update() {
//        cardViews.clear();
//        for (Card card : tempSpellCardsList) {
//            CardView cv = new CardView(card, assets);
//            cardViews.add(cv);
//        }
//    }
//
//    private void drawDebug(ShapeRenderer shapeRenderer, float x, float y, float width, float height, float marginCardToCard){
//        shapeRenderer.setColor(Color.BLUE);
//        for (int i = 0; i < cardViews.size(); i++) {
//            shapeRenderer.rect(x, y ,width,height);
//            shapeRenderer.rect(x+marginCardToCard,y,width,height);
//        }
//
//    }
//
//
//    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
//        if (cardViews.isEmpty()) return;
//        float spacing = 1f;
//        float totalSpacing = spacing * (cardViews.size() - 1);
//
//        for (int i = 0; i < cardViews.size(); i++) {
//            CardView card = cardViews.get(i);
//
//
//            float baseY = y;
//
//            card.draw(batch);
//        }
//    }
//
//}
