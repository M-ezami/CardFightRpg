package io.github.some_example_name.target.cardRelated;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.target.parentsOrOthers.SingleTargetStrategy;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

import java.util.List;

public class SingleMonsterTarget extends SingleTargetStrategy {

    @Override
    public List<Targatable> getTargets(GameState gameState) {
        return List.of();
    }

    @Override
        public boolean isValidTarget(Targatable target) {
            return target instanceof Monster;
        }
    }

