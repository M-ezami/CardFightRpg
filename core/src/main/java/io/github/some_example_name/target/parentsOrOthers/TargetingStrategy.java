package io.github.some_example_name.target.parentsOrOthers;

import io.github.some_example_name.data.GameState;

import java.util.List;
public interface  TargetingStrategy {

    List<Targatable> getTargets(GameState gameState);

     boolean isValidTarget(Targatable target);

     boolean requiresTarget();
}
