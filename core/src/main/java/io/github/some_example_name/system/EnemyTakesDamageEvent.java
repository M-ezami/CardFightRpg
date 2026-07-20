package io.github.some_example_name.system;

import io.github.some_example_name.entiteRelated.targets.Targatable;

import javax.management.modelmbean.InvalidTargetObjectTypeException;
import java.lang.annotation.Target;

public record EnemyTakesDamageEvent(Targatable target) {
}
