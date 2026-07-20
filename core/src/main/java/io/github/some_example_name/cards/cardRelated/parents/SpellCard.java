package io.github.some_example_name.cards.cardRelated.parents;

import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellCard extends AbstractCard {
    /*private Mood mood;
    private int age;
    */

    private final List<Effect> effects;

    protected SpellCard(String name, String description, int manaCost) {
        super(name, description, manaCost);
        this.effects = new ArrayList<>();
        this.cardType = CardType.SPELL;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public List<Effect> getEffects() {
        return effects;
    }

}
