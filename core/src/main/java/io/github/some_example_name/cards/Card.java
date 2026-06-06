package io.github.some_example_name.cards;

import io.github.some_example_name.cards.cardParents.CardType;
import io.github.some_example_name.effects.Effect;

public interface Card {

    int getManaCost();
    CardType getCardType();
    String getName();
    String getDescription();

}
