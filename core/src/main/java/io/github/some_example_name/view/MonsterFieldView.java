package io.github.some_example_name.view;


import io.github.some_example_name.enitites.Monster;

import java.util.List;

public class MonsterFieldView extends ContainerView{

    public MonsterFieldView(List<Monster> monsters) {
        super(createViews(monsters, MonsterView :: new));
    }





    @Override
    public void refresh() {

    }
}
