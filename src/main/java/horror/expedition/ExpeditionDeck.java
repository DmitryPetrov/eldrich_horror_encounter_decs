package main.java.horror.expedition;

import main.java.dealer.SourceLoader;
import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpeditionDeck extends Deck {

    public List<Card> box;

    public ExpeditionDeck(SourceLoader dealer) {
        super(CardType.EXPEDITION, dealer);
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
        if (cards.empty()) {
            this.reshuffle();
        }
        return ((ExpeditionCard) cards.peek()).location;
    }
}
