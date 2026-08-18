package io.github.some_example_name.cards.cardRelated.parents;

import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.Effect;
import io.github.some_example_name.target.cardRelated.AllEnemysTarget;
import io.github.some_example_name.target.cardRelated.AllMonstersTarget;
import io.github.some_example_name.target.cardRelated.SingleMonsterTarget;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingMode;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

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
    public void emitEffects(GameState state, Targatable target) {
        for (Effect effect : effects) {
            effect.apply(state, target);
            //effect.damageOrSpellEvent(target);
        }
    }
    //definetly needs a rewrite becaue every targetingmode will nned modfiying this method not good architecture

    public TargetingMode getTargetingMode() {
        boolean hasSingleTarget = false;
        boolean hasMultipleTargets = false;

        for (Effect effect : effects) {
            TargetingStrategy targets = effect.getTargetingStrategy();

            if (targets instanceof AllEnemysTarget ||
                targets instanceof AllMonstersTarget) {
                hasMultipleTargets = true;
            }

            if (targets instanceof SingleMonsterTarget) {
                hasSingleTarget = true;
            }
        }

        if (hasSingleTarget && hasMultipleTargets) {
            return TargetingMode.SINGLE_AND_MULTIPLE;
        }

        if (hasMultipleTargets) {
            return TargetingMode.MULTIPLE;
        }

        if (hasSingleTarget) {
            return TargetingMode.SINGLE_OWN_MONSTER;
        }

        return TargetingMode.NO_TARGET;
    }

    public List<Effect> getEffects() {
        return effects;
    }

}
