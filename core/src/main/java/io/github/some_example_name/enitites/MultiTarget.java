package io.github.some_example_name.enitites;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.Targatable;
import io.github.some_example_name.target.TargetingStrategy;

import java.util.List;

public class MultiTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state) {
        return state.getTargets();
    }
}
