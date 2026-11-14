package io.github.some_example_name.cards.SpellCards;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

import java.util.List;

public class SingleEnemyTarget implements TargetingStrategy {
    @Override
    public List<Targatable> getTargets(GameState gameState) {
        return List.of();
    }

    @Override
    public boolean isValidTarget(Targatable target) {
        return false;
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }
}
