package io.github.some_example_name.system;

import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.event.phaseEvents.EnemyTurnStartEvent;
import io.github.some_example_name.events.event.phaseEvents.FightEvent;
import io.github.some_example_name.events.event.phaseEvents.MonsterPlayedEvent;
import io.github.some_example_name.events.event.phaseEvents.PlayerTurnBeginEvent;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.events.utilities.RoundPhase;
import io.github.some_example_name.ui.ChooseCardsToDiscardEvent;
import io.github.some_example_name.ui.DiscardEvent;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TurnSystem {

    private final EventBus eventBus;
    private final GameState gameState;

    // one row per event type; each row maps "phase we must be in" -> "phase we move to"
    private final Map<Class<?>, Map<RoundPhase, RoundPhase>> transitions;

    {
        new EnumMap<>(RoundPhase.class);
        transitions = buildTransitions();
    }

    public TurnSystem(GameState gameState) {
        this.eventBus = EventBus.getInstance();
        this.gameState = gameState;
        subscribe();
    }

    private Map<Class<?>, Map<RoundPhase, RoundPhase>> buildTransitions() {
        Map<Class<?>, Map<RoundPhase, RoundPhase>> table = new HashMap<>();

        table.put(PlayerTurnBeginEvent.class, Map.of(
            RoundPhase.ENEMY_TURN, RoundPhase.SPELL_PHASE
        ));

        table.put(ChooseCardsToDiscardEvent.class, Map.of(
            RoundPhase.SPELL_PHASE, RoundPhase.DISCARD_PHASE,
            RoundPhase.FIGHT_PHASE, RoundPhase.DISCARD_PHASE,
            RoundPhase.MONSTER_PHASE, RoundPhase.DISCARD_PHASE
        ));

        table.put(DiscardEvent.class, Map.of(
            RoundPhase.DISCARD_PHASE, RoundPhase.ENEMY_TURN
        ));

        table.put(MonsterPlayedEvent.class, Map.of(
            RoundPhase.SPELL_PHASE, RoundPhase.MONSTER_PHASE
        ));

        table.put(FightEvent.class, Map.of(
            RoundPhase.MONSTER_PHASE, RoundPhase.FIGHT_PHASE,
            RoundPhase.SPELL_PHASE, RoundPhase.FIGHT_PHASE
        ));

        return table;
    }


    private void subscribe() {
        eventBus.subscribe(PlayerTurnBeginEvent.class, e -> tryTransition(PlayerTurnBeginEvent.class));
        eventBus.subscribe(ChooseCardsToDiscardEvent.class, e -> tryTransition(ChooseCardsToDiscardEvent.class));
        eventBus.subscribe(MonsterPlayedEvent.class, e -> tryTransition(MonsterPlayedEvent.class));
        eventBus.subscribe(FightEvent.class, e -> tryTransition(FightEvent.class));

        eventBus.subscribe(DiscardEvent.class, e -> {
            if (tryTransition(DiscardEvent.class)) {
                eventBus.emit(new EnemyTurnStartEvent());
            }
        });

    }

    private boolean tryTransition(Class<?> eventType) {
        RoundPhase current = gameState.getRoundPhase();
        RoundPhase next = transitions.get(eventType).get(current);
        if(next == current) {
            return false;
        }
        if (next == null) {
            return false;
        }
        gameState.setRoundPhase(next);
        return true;
    }
}
