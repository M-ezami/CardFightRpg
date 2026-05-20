package io.github.some_example_name;

import java.util.List;

public class PlayerTarget implements TargetingStrategy {


    @Override
    public List<Targatable> getTargets(GameState state) {
        return List.of(state.getPlayer());
    }
}
