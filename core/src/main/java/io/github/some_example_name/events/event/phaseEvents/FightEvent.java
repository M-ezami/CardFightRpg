package io.github.some_example_name.events.event.phaseEvents;

import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.entiteRelated.Opponent;


public record FightEvent(Monster playerMonster, Opponent targetOpponent){

}
