package io.github.some_example_name.cards;

import io.github.some_example_name.cards.cardRelated.MonsterType;
import io.github.some_example_name.entiteRelated.targets.SingleTarget;

public class SimpleMonster extends Monster {

    public SimpleMonster() {
        super(5,5);
        this.attack = 2;
        this.targetingStrategy = new SingleTarget();
        this.name = "simpleMonster";

        this.type = MonsterType.MAGE;

    }



}
