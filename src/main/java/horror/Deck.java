package main.java.horror;

import main.java.dealer.SourceLoader;

import java.util.*;

public class Deck {

    protected Stack<Card> cards;
    protected Stack<Card> discardedCards;
    protected Card shirt;

    protected Deck() {}

    public Deck(CardType cardType, SourceLoader dealer) {
        this.discardedCards = new Stack<>();
        this.cards = new Stack<>();
        this.cards.addAll(dealer.getCards(cardType));
        reshuffle();
        this.shirt = dealer.getCardShirts().get(cardType);
    }

    public Deck(Set<Card> cards, byte[] shirt) {
        this.discardedCards = new Stack<>();
        this.cards = new Stack<>();
        this.cards.addAll(cards);
        reshuffle();
        this.shirt = new Card(UUID.randomUUID(), this.cards.peek().type, shirt);
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

    public Card getShirt() {
        return shirt;
    }
}
