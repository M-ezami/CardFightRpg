package io.github.some_example_name.cards.SpellCards;


import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.MultipleRoundsEffect;
import io.github.some_example_name.effects.parents.DamageEffect;
import io.github.some_example_name.target.cardRelated.SingleEnemyTarget;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public class SlowBurn extends SpellCard {

    private static final String name = SlowBurn.class.getSimpleName();
    private final int  turns = 3;
    private final int damagePerTurn = 2;

    public SlowBurn() {
        super(name, "spell card: deals a total of 6 damage, 2 damage for the next 3 turns",1);
        TargetingStrategy damageTargetingStrategy = new SingleEnemyTarget();
        DamageEffect delayedDamageEffect = new DamageEffect(3, damageTargetingStrategy ,damagePerTurn, new MultipleRoundsEffect(turns));
        this.addEffect(delayedDamageEffect);
    }

}
