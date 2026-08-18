package io.github.some_example_name.target.cardRelated;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

import java.util.ArrayList;
import java.util.List;

public class AllMonstersTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state, Targatable target) {
        System.out.println("MONSTER COUNT: " + state.getMonsters().size());
        if (state.getMonsters() != null && !state.getMonsters().isEmpty()) {
            return new ArrayList<>(state.getMonsters());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isValidTarget(Targatable target) {
        return true;
    }
    @Override
    public boolean requiresTarget() {
        return false;
    }

}
