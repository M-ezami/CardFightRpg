package io.github.some_example_name.entiteRelated.targets;

import io.github.some_example_name.data.GameState;

import java.util.ArrayList;
import java.util.List;

public class PlayerOrMonsterTarget implements TargetingStrategy {

    @Override
    public List<Targatable> getTargets(GameState state) {
        System.out.println("MONSTER COUNT: " + state.getMonsters().size());
        if (state.getMonsters() != null && !state.getMonsters().isEmpty()) {
            return new ArrayList<>(state.getMonsters());
        }

        if (state.getPlayer() != null) {
            return List.of(state.getPlayer());
        }

        return List.of();
    }
}
