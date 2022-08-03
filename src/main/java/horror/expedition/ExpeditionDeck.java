package main.java.horror.expedition;

import main.java.horror.Card;
import main.java.horror.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpeditionDeck extends Deck {

    public List<Card> box;

    public ExpeditionDeck(List<Card> cards) {
        super(cards);
    }

    public void moveToBox(ExpeditionLocation excluded) {
        List<Card> cardsForBox = cards
                .stream()
                .map(card -> (ExpeditionCard) card)
                .filter(card -> card.location.equals(excluded))
                .collect(Collectors.toList());
        box.addAll(cardsForBox);
        cards.removeAll(cardsForBox);
    }

    public List<Card> showCardsInBox() {
        return new ArrayList<>(box);
    }

    @Override
    public List<Card> showAllCards() {
        List<Card> allCards = super.showAllCards();
        allCards.addAll(box);
        return allCards;
    }

    public ExpeditionLocation showNextCardLocation() {
        return ((ExpeditionCard) cards.peek()).location;
    }
}
