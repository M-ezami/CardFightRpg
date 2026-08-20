package io.github.some_example_name.target.enemy;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.enitites.Player;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

import java.util.ArrayList;
import java.util.List;

public class PlayerOrMonsterTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(
        GameState state,
        Targatable selectedTarget) {

        if (state.getMonsters() != null &&
            !state.getMonsters().isEmpty()) {

            return new ArrayList<>(state.getMonsters());
        }

        if (state.getPlayer() != null) {
            return List.of(state.getPlayer());
        }

        return List.of();
    }

    @Override
    public boolean isValidTarget(Targatable target) {
        return target instanceof Player;
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }
}




