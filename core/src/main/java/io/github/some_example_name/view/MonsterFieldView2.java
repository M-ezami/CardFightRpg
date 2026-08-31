package io.github.some_example_name.view;


import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.enitites.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterFieldView2 extends ContainerView{

    public MonsterFieldView2(List<Monster> monsters) {
        super(createMonsterViews(monsters));
    }

    private static List<MonsterView> createMonsterViews(List<Monster> monsters) {
        List<MonsterView> monsterViews = new ArrayList<>();

        for (Monster monster : monsters) {
            monsterViews.add(new MonsterView(monster));
        }

        return monsterViews;
    }



    @Override
    public void refresh() {

    }
}
