package io.github.some_example_name.data;

import io.github.some_example_name.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Cards {

    public static int HANDSIZE = 5;

    private final List<Card> hand;
    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final List<Card> cardDeck;

    public Cards(List<Card> deck) {
        this.cardDeck = new ArrayList<>(deck);
        this.hand = new ArrayList<>();
        this.drawPile = new ArrayList<>(cardDeck);
        this.discardPile = new ArrayList<>();
        drawHand();
    }

    public void addCard(final Card card) {
        this.hand.add(card);
    }

    public void drawHand() {
        System.out.println(drawPile.size() +"drawPileSIze");
        System.out.println(discardPile.size() +"");
        if (HANDSIZE <= 0) return;
        if (drawPile.isEmpty()) reshuffle();
        int cardsToDraw = Math.min(HANDSIZE, drawPile.size());

        for (int i = 0; i < cardsToDraw; i++) {
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

    public List<Card> getCardDeck() {
        return cardDeck;
    }
}
