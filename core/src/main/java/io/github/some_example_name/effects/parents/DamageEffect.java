package io.github.some_example_name.effects.parents;


import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.MultipleRoundsEffect;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public class DamageEffect extends Effect {
protected int damageAmount;
    public DamageEffect(int amount, TargetingStrategy targetingStrategy,int damageAmount) {
        super(amount, targetingStrategy);
        this.damageAmount = damageAmount;
    }

    public DamageEffect(int amount, TargetingStrategy targetingStrategy, int damage,MultipleRoundsEffect multipleRoundsEffect ) {
        super(amount, targetingStrategy,multipleRoundsEffect);
        this.damageAmount = damage;
    }



    private void dealDamage(Targatable target) {
        target.takeDamage(damageAmount);
    }

    @Override
    public void applyEffectToTarget(Targatable target, GameState state) {
        dealDamage(target);
    }


    @Override
    public String getDescription() {
        return amount + "damage effect";
    }





}

