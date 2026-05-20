package io.github.some_example_name;

import java.util.List;

public class MultiTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state) {
        return state.getTargets();
    }
}
