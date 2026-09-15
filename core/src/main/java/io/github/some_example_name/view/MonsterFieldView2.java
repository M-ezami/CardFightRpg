package io.github.some_example_name.view;


import io.github.some_example_name.enitites.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterFieldView2 extends ContainerView{

    public MonsterFieldView2(List<Monster> monsters) {
        super(createViews(monsters, MonsterView :: new));
    }





    @Override
    public void refresh() {

    }
}
