package io.github.some_example_name.cards.SpellCards;

import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.MonsterDamageBuff;
import io.github.some_example_name.effects.MultipleRoundsEffect;
import io.github.some_example_name.target.cardRelated.AllMonstersTarget;

public class MonsterGainDamageCard extends SpellCard {
    private static final String name = MonsterGainDamageCard.class.getSimpleName();

    public MonsterGainDamageCard() {
        super(name, "all monsters gain 2 aadditonal damage for 2 turns",1);
        MonsterDamageBuff monsterDamageBuff = new MonsterDamageBuff(2,  new AllMonstersTarget(), new MultipleRoundsEffect(3) {
        });
        this.addEffect(monsterDamageBuff);
    }


}
