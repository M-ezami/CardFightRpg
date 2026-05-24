package io.github.some_example_name.events;

import io.github.some_example_name.data.Card;
import io.github.some_example_name.entiteRelated.Targatable;

import java.util.List;

public class CardPlayedEvent {
    public final Card card;
    public List<Card> updatedHand;
    public Targatable target;

    public CardPlayedEvent(Card card) {
        this.card = card;
    }


    public Card getCard() {
        return card;
    }

    public List<Card> getUpdatedHand() {
        return updatedHand;
    }

    public Targatable getTarget() {
        return target;
    }

    public CardPlayedEvent(Card card, Targatable target) {
        this.card = card;
        this.target = target;
    }




}
