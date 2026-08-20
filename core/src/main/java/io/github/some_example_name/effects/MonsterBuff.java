package io.github.some_example_name.effects;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.Effect;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public abstract class MonsterBuff extends Effect {
   //as of now this class is useless

    public MonsterBuff(int amount, TargetingStrategy targetingStrategy) {
        super(amount, targetingStrategy);
    }
    public MonsterBuff(int amount, TargetingStrategy targetingStrategy, MultipleRoundsEffect multipleRoundsEffect) {
        super(amount, targetingStrategy, multipleRoundsEffect);
    }


    @Override
    public final void applyEffectToTarget(Targatable target, GameState state) {
    applyMonsterBuff((Monster) target);
}


    public abstract void applyMonsterBuff(Monster monster);
}


