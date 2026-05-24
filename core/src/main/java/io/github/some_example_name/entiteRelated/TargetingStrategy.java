package io.github.some_example_name.entiteRelated;

import io.github.some_example_name.data.GameState;

import java.util.List;

@FunctionalInterface
public interface TargetingStrategy {

    List<Targatable> getTargets(GameState state);
}
