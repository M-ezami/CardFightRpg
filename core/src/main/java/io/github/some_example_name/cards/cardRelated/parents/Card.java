package io.github.some_example_name.cards.cardRelated.parents;

import io.github.some_example_name.cards.cardRelated.CardType;

public interface Card {

    int getManaCost();
    CardType getCardType();
    String getName();
    String getDescription();

}
