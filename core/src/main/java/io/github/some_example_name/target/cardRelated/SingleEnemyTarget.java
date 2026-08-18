package io.github.some_example_name.target.cardRelated;

import io.github.some_example_name.enitites.Opponent;
import io.github.some_example_name.target.parentsOrOthers.SingleTargetStrategy;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

public class SingleEnemyTarget extends SingleTargetStrategy {

    @Override
    public boolean isValidTarget(Targatable target) {
        return target instanceof Opponent;
    }
}

