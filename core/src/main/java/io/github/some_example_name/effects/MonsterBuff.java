package io.github.some_example_name.effects;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.effects.parents.Effect;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.target.cardRelated.AllMonstersTarget;
import io.github.some_example_name.target.cardRelated.SingleMonsterTarget;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

public abstract class MonsterBuff extends Effect {


        public MonsterBuff(int amount, boolean allMonsters) {
            super(amount, allMonsters ? new AllMonstersTarget() : new SingleMonsterTarget());
        }

        @Override
        public final void applyEffectToTarget(Targatable target, GameState state) {
            applyMonsterBuff((Monster) target);
        }

        public abstract void applyMonsterBuff(Monster monster);
    }
