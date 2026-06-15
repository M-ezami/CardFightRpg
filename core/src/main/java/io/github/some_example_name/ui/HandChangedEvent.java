package io.github.some_example_name.ui;

import io.github.some_example_name.cards.Card;

import java.util.List;

public record HandChangedEvent(List<Card> hand){}
