package io.github.some_example_name.cards.SpellCards;


import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.DelayedDamageEffect;
import io.github.some_example_name.effects.SimpleDamageEffect;
import io.github.some_example_name.target.SingleTarget;
import io.github.some_example_name.target.TargetingStrategy;

public class SlowBurn extends SpellCard {

    private static final String name = SlowBurn.class.getSimpleName();

    public SlowBurn() {
        super(name, "spell card: deals a total of 6 damage, 2 damage for the next 3 turns",1);
        TargetingStrategy damageTargetingStrategy = new SingleTarget();
        DelayedDamageEffect delayedDamageEffect = new DelayedDamageEffect(3, damageTargetingStrategy,3);
        this.addEffect(delayedDamageEffect);
    }


}
