package io.github.some_example_name.events.event.phaseEvents;

import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.enitites.Opponent;


public record FightEvent(Monster playerMonster, Opponent targetOpponent){

}
