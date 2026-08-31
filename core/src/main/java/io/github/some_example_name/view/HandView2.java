package io.github.some_example_name.view;

import io.github.some_example_name.cards.cardRelated.parents.Card;

import java.util.ArrayList;
import java.util.List;

public class HandView2 extends ContainerView {

    public HandView2(List<Card> hand) {
        super(createCardViews(hand));
    }
        //could be more generic for monster aswell but probably overkill also
        // no idea how to do it good
    private static List<CardView> createCardViews(List<Card> hand) {
        List<CardView> cardViews = new ArrayList<>();

        for (Card card : hand) {
            cardViews.add(new CardView(card));
        }
        return cardViews;
    }

    @Override
    public void refresh() {

    }
}
