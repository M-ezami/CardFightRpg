package io.github.some_example_name.events;

import io.github.some_example_name.cards.Card;
import io.github.some_example_name.entiteRelated.Targatable;

public record CardPlayedAttemptEvent(Card card, Targatable target) {
}
