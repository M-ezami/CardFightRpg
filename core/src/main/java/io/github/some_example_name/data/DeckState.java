package io.github.some_example_name.data;

import io.github.some_example_name.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckState {

    public static int HANDSIZE = 5;

    private List<Card> hand;
    private List<Card> drawPile;
    private List<Card> discardPile;


    public DeckState(Deck deck) {
        this.hand = new ArrayList<>();
        this.drawPile = new ArrayList<>(deck.getCards());
        this.discardPile = new ArrayList<>();
        drawHand();
    }

    public void drawHand() {
        System.out.println(drawPile.size() +"drawPileSIze");
        System.out.println(discardPile.size() +"");
        if (HANDSIZE <= 0) return;
        if (drawPile.isEmpty()) reshuffle();

        for (int i = 0; i < HANDSIZE; i++) {
            Card card = drawPile.remove(0);
            hand.add(card);
        }
    }

    public void discard(Card card) {
        hand.remove(card);
        discardPile.add(card);
    }

    public void discardHand() {
        discardPile.addAll(hand);
        hand.clear();
    }


    public void reshuffle() {
        if (discardPile.isEmpty()) return;
        while (!discardPile.isEmpty()) {
            Card card = discardPile.remove(discardPile.size() - 1);
            drawPile.add(card);
        }

    }

    public List<Card> getHand() {
        return hand;
    }
}
