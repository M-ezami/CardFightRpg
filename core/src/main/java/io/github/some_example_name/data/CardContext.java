package io.github.some_example_name.data;

import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.entiteRelated.targets.Targatable;

public record CardContext(boolean isMonsterField, Targatable target, Card card) {
}
