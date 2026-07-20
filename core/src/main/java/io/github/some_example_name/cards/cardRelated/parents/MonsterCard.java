package io.github.some_example_name.cards.cardRelated.parents;


import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.cards.SimpleMonster;
import io.github.some_example_name.cards.cardRelated.CardType;

public abstract class MonsterCard extends AbstractCard {

    protected Monster monster;

    public MonsterCard(String name, String description, int manaCost, Monster monster) {
        super(name, description, manaCost);
        this.monster = monster;
        this.cardType = CardType.MONSTER;

    }

    public Monster getMonster() {
        return monster;
    }




}
