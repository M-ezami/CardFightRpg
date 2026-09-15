package io.github.some_example_name.view;

import io.github.some_example_name.cards.cardRelated.parents.Card;
import java.util.List;

public class HandView2 extends ContainerView<CardView> {

    public HandView2(List<Card> hand) {
        super(createViews(hand, CardView::new));
    }

    @Override
    public void refresh() {
    }
}
g
