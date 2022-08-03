package main.java.horror.myth;

import main.java.horror.Card;
import main.java.horror.Deck;
import main.java.horror.ancient.AncientOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MythosDeck extends Deck {

    private AncientOne elder;
    private List<MythosCard> inGame = new ArrayList<>();

    public MythosDeck(List<Card> cards, AncientOne elder, boolean easyMod) {
        List<MythosCard> mythCards = cards
                .stream()
                .map(card -> (MythosCard) card)
                .collect(Collectors.toList());
        if (easyMod) {
            mythCards = mythCards
                    .stream()
                    .filter(mythCard -> !mythCard.complexity.equals(Complexity.HARD))
                    .collect(Collectors.toList());
        }
        this.cards = (Stack) elder.buildMythDeck(mythCards);
        this.discardedCards = new Stack<>();
        this.elder = elder;
    }

    public Card get() {
        if (cards.empty()) {
            elder.win();
            return null;
        }
        MythosCard top = (MythosCard) cards.pop();
        if (top.event) {
            discardedCards.add(top);
        } else {
            inGame.add(top);
        }
        return top;
    }

    public void discardCard(int cardId) {
        inGame
                .stream()
                .filter(mythCard -> mythCard.id.equals(cardId))
                .findFirst()
                .ifPresent(discardedCards::add);
    }

    public List<Card> showInGameCards() {
        return new ArrayList<>(inGame);
    }

    public List<Card> showAllCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(inGame);
        allCards.addAll(discardedCards);
        allCards.addAll(cards);
        return allCards;
    }
}
