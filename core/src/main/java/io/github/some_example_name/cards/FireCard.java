package io.github.some_example_name.cards;


import io.github.some_example_name.data.Card;
import io.github.some_example_name.effects.DamageEffect;
import io.github.some_example_name.entiteRelated.SingleTarget;

public class FireCard extends Card {

    private final DamageEffect damageEffect;
    private static String name = FireCard.class.getSimpleName();
    private String description;

    public FireCard(String description) {
        super(name ,1);
        this.description = description;
        this.targetingStrategy = new SingleTarget();
        this.damageEffect= new DamageEffect(3, targetingStrategy);
        this.addEffect(damageEffect);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
