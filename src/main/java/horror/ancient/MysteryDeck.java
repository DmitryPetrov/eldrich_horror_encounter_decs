package main.java.horror.ancient;

import main.java.horror.Card;
import main.java.horror.Deck;

import java.util.List;

public class MysteryDeck extends Deck {

    private AncientOne ancient;
    private Card current;

    public MysteryDeck(List<Card> cards, AncientOne ancient) {
        this.ancient = ancient;
    }

    public void nextMystery() {
        discardedCards.add(current);
        if ((discardedCards.size() == 3) && ancient.inFirstPhase()) {
            ancient.lose();
        }
        current = super.get();
    }

    public Card showCurrentMystery() {
        return current;
    }
}
