package io.github.some_example_name.screens;

import io.github.some_example_name.cards.SimpleMonster;
import io.github.some_example_name.cards.cardRelated.parents.MonsterCard;

public class SimpleMonsterCard extends MonsterCard {

    public SimpleMonsterCard() {
        super("joesMAMA",  "Play a simple monster", 1, new SimpleMonster());
    }


}
