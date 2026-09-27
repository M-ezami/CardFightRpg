package io.github.some_example_name.system;

import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.cards.cardRelated.parents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.parentsOrOthers.Targatable;

import java.util.List;

public class CardSystem {
    private final GameState gameState;

    public CardSystem(GameState gameState){
        this.gameState = gameState;

    }

public List<Targatable> getValidTargets(Card card){
if (card instanceof SpellCard spellCard){
     spellCard.getTargets();
}
}

}
