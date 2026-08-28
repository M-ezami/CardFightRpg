package io.github.some_example_name.view;


import java.util.List;

public class HandView2 extends BaseView {
    private final List<CardView> cardViews;

    public HandView2(List<CardView> cardViews) {
        this.cardViews = cardViews;

    }


    @Override
    public void refresh() {

    }

    @Override
    public void createUI() {
        left();
        for (CardView cardView : cardViews)
            this.add(cardView).spaceRight(10);
    }
}

