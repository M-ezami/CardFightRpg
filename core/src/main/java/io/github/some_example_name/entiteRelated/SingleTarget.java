package io.github.some_example_name.entiteRelated;

import io.github.some_example_name.data.GameState;

import java.util.List;

public class SingleTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state) {
        return List.of(state.getTargetOpponent());
    }
}
