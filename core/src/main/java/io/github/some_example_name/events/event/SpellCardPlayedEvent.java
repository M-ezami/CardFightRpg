package io.github.some_example_name.events.event;

import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.target.parentsOrOthers.Targatable;


public record SpellCardPlayedEvent(SpellCard spellCard,
                                   Targatable target) {

    }
