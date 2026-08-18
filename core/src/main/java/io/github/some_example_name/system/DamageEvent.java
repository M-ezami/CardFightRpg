package io.github.some_example_name.system;

import io.github.some_example_name.target.parentsOrOthers.Targatable;

public record DamageEvent(Targatable target, int amount) {
}
