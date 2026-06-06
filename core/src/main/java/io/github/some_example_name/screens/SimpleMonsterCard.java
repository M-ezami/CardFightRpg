package io.github.some_example_name.screens;

import io.github.some_example_name.cards.SimpleMonster;
import io.github.some_example_name.cards.cardParents.MonsterCard;

public class SimpleMonsterCard extends MonsterCard {

    public SimpleMonsterCard() {
        this.monster = new SimpleMonster();
    }


    @Override
    public int getManaCost() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
