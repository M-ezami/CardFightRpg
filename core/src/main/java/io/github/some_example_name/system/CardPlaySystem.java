package io.github.some_example_name.system;

import io.github.some_example_name.cards.cardRelated.parents.MonsterCard;
import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.enitites.Player;
import io.github.some_example_name.events.event.SpellCardPlayedEvent;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.events.event.MonsterCardPlayedEvent;
import io.github.some_example_name.events.event.phaseEvents.MonsterPlayedEvent;
import io.github.some_example_name.events.utilities.EventBus;

public class CardPlaySystem {
    private final EventBus eventBus;
    private final GameState gameState;
    private final Player player;

    public CardPlaySystem(GameState gameState) {
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.eventBus = EventBus.getInstance();
        subscribe();
    }

    public void subscribe() {
        eventBus.subscribe(MonsterCardPlayedEvent.class, event -> onMonsterCardPlayed(event.monsterCard()));
        eventBus.subscribe(SpellCardPlayedEvent.class, event -> playSpellCard(event.spellCard(), event.target()));
    }

    private void playCard(SpellCard spellCard, Targatable target) {
        if (player.getCurrentMana() < spellCard.getManaCost()) return;
        spellCard.emitEffects(gameState, target);
        player.playCard(spellCard);

    }

    private void playSpellCard(SpellCard spellCard, Targatable target) {
       playCard(spellCard, target);
        }



    public void onMonsterCardPlayed(MonsterCard card) {
        //could maybe emit a new monstercardplayed event
        // perhaps pass in monster
        if (player.getCurrentMana() < card.getManaCost()) return;
        if (card.getMonster().getHealth() <= 0) card.getMonster().setHealth(card.getMonster().getMaxHealth());
        player.getMonsters().add(card.getMonster());
        player.playCard(card);
        eventBus.emit(new MonsterPlayedEvent());

    }
// perhaps if there is a single target then we pass the target otherwise we try to play the
// card simply and this works if card isnt played in a specific field
    //current rewreting


}
