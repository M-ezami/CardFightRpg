package io.github.some_example_name.target.parentsOrOthers;

import io.github.some_example_name.data.GameState;

import java.util.List;

public abstract class SingleTargetStrategy implements TargetingStrategy {



    //maybe if gettargets != null return true
    @Override
    public boolean requiresTarget() {
        return true;
    }
}
