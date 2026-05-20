package io.github.some_example_name;

import java.util.List;

@FunctionalInterface
public interface TargetingStrategy {

    List<Targatable> getTargets(GameState state);
}
