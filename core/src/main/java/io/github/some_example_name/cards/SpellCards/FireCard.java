package io.github.some_example_name.cards.SpellCards;


import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.effects.SimpleDamageEffect;
import io.github.some_example_name.target.cardRelated.SingleEnemyTarget;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public class FireCard extends SpellCard {

    private static final String name = FireCard.class.getSimpleName();

    public FireCard() {
        super(name, "spell card deals x damage",1);
        TargetingStrategy damageTargetingStrategy = new SingleEnemyTarget();
        SimpleDamageEffect simpleDamageEffect = new SimpleDamageEffect(3, damageTargetingStrategy);
        this.addEffect(simpleDamageEffect);
    }


}
