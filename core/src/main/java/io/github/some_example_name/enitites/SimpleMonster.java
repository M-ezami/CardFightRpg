package io.github.some_example_name.enitites;

import io.github.some_example_name.cards.SpellCards.SingleEnemyTarget;
import io.github.some_example_name.cards.cardRelated.MonsterType;


public class SimpleMonster extends Monster {

    public SimpleMonster() {
        super(5,5);
        this.damage = 2;
        this.targetingStrategy = new SingleEnemyTarget();
        this.name = "simpleMonster";

        this.type = MonsterType.MAGE;

    }



}
