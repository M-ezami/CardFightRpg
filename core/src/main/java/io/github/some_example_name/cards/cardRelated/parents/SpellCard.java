package io.github.some_example_name.cards.cardRelated.parents;

import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.Effect;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

import java.util.ArrayList;
import java.util.List;


//should move the stuff into effect maybe
public abstract class SpellCard extends AbstractCard {
    /*private Mood mood;
    private int age;
    */

    private final List<Effect> effects;
    private final List<List<Targatable>> targets;

    protected SpellCard(String name, String description, int manaCost) {
        super(name, description, manaCost);
        this.effects = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.cardType = CardType.SPELL;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void emitEffects(GameState state, Targatable target) {
        for (Effect effect : effects) {
            effect.apply(state, target);
        }
    }



    public boolean isOverMultipleRounds(){
        for (Effect effect : effects) {
            if(effect.isOverMultipleRounds()){
                return true;
            }
        }
        return false;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public List<List<Targatable>> getTargets() {
        List<List<Targatable>> targets = new ArrayList<>();

        for (Effect effect : effects) {
            if (effect.getTargetingStrategy().requiresTarget()) {
                targets.add(effect.getTargetingStrategy().getTargets());
            }
        }

        return targets;
    }

}
