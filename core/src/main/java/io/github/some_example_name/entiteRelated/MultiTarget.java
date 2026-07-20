package io.github.some_example_name.entiteRelated;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.targets.Targatable;
import io.github.some_example_name.entiteRelated.targets.TargetingStrategy;

import java.util.List;

public class MultiTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state) {
        return state.getTargets();
    }
}
