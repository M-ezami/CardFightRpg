package io.github.some_example_name.cards;


import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.DamageEffect;
import io.github.some_example_name.entiteRelated.targets.SingleTarget;
import io.github.some_example_name.entiteRelated.targets.TargetingStrategy;

public class FireCard extends SpellCard {

    private static final String name = FireCard.class.getSimpleName();

    public FireCard() {
        super(name, "spell card deals x damage",1);
        TargetingStrategy damageTargetingStrategy = new SingleTarget();
        DamageEffect damageEffect = new DamageEffect(3, damageTargetingStrategy);
        this.addEffect(damageEffect);
    }


}
