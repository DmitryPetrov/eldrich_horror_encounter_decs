package main.java.horror.expedition;

import main.java.dealer.SourceLoader;
import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExpeditionDeck extends Deck {

    public List<Card> box;
    public final Map<ExpeditionLocation, Card> shirts;

    public ExpeditionDeck(SourceLoader dealer) {
        this.discardedCards = new Stack<>();
        this.cards = new Stack<>();
        this.cards.addAll(dealer.getCards(CardType.EXPEDITION));
        reshuffle();
        this.shirts = dealer.getCardExpeditionShirts();
        this.shirt = shirts.get(((ExpeditionCard) this.cards.peek()).location);
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

    public ExpeditionCard showNextCardLocation() {
        if (cards.empty()) {
            this.reshuffle();
        }
        return (ExpeditionCard) shirts.get(((ExpeditionCard) cards.peek()).location);
    }
}
