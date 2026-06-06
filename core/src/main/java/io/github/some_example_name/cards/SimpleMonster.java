package io.github.some_example_name.cards;

import io.github.some_example_name.MonsterType;
import io.github.some_example_name.entiteRelated.SingleTarget;

public class SimpleMonster extends Monster {

    public SimpleMonster() {
        this.attack = 2;
        this.health = 5;
        this.targetingStrategy = new SingleTarget();
        this.name = "simpleMonster";
        this.type = MonsterType.MAGE;

    }





}
