package io.github.some_example_name.cards.SpellCards;

import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.MonsterDamageBuff;

public class MonsterGainDamageCard extends SpellCard {
    private static final String name = MonsterGainDamageCard.class.getSimpleName();

    public MonsterGainDamageCard() {
        super(name, "all monsters gain 2 aadditonal damage for 2 turns",1);
        MonsterDamageBuff monsterDamageBuff = new MonsterDamageBuff(2,2, true);
        this.addEffect(monsterDamageBuff);
    }


}
