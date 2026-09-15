package io.github.some_example_name.view;

import io.github.some_example_name.enitites.Opponent;

import java.util.List;

public class OpponentView extends ContainerView{

    public OpponentView(List<Opponent> opponents) {
        super(createViews(opponents, MonsterView :: new));
    }

    @Override
    public void refresh() {

    }
}
