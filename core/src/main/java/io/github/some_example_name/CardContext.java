package io.github.some_example_name;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.entiteRelated.Targatable;

public record CardContext(boolean isMonsterField, Targatable target, Card card) {
}
