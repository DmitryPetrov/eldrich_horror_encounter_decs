package main.java.horror;

import main.java.dealer.SourceLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    protected Stack<Card> cards;
    protected Stack<Card> discardedCards;

    protected Deck() {}

    public Deck(CardType cardType, SourceLoader dealer) {
        this.discardedCards = new Stack<>();
        this.cards = new Stack<>();
        this.cards.addAll(dealer.getCards(cardType));
        reshuffle();
    }

    public Card get() {
        if (cards.isEmpty()) {
            reshuffle();
        }
        Card top = cards.pop();
        discardedCards.add(top);
        return top;
    }

    protected void reshuffle() {
        List<Card> allCards = new ArrayList<>(cards.size() + discardedCards.size());
        allCards.addAll(cards);
        allCards.addAll(discardedCards);
        Collections.shuffle(allCards);
        discardedCards.clear();
        cards.clear();
        cards.addAll(allCards);
    }

    public List<Card> showDeck() {
        return new ArrayList<>(cards);
    }

    public List<Card> showDiscardDeck() {
        return new ArrayList<>(discardedCards);
    }

    public List<Card> showAllCards() {
        List<Card> allCards = new ArrayList<>(cards.size() + discardedCards.size());
        allCards.addAll(cards);
        allCards.addAll(discardedCards);
        return allCards;
    }
}
