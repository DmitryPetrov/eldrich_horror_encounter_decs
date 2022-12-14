package main.java.horror.myth;

import main.java.dealer.SourceLoader;
import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Deck;
import main.java.horror.ancient.AncientOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;

public class MythosDeck extends Deck {

    private AncientOne elder;
    private List<MythosCard> inGame = new ArrayList<>();

    public MythosDeck(SourceLoader dealer, AncientOne ancientOne, boolean easyMod) {
        List<MythosCard> mythCards = dealer.getCards(CardType.MYTHOS)
                .stream()
                .map(card -> (MythosCard) card)
                .collect(Collectors.toList());
        if (easyMod) {
            mythCards = mythCards
                    .stream()
                    .filter(mythCard -> !mythCard.complexity.equals(Complexity.HARD))
                    .collect(Collectors.toList());
        }
        this.cards = (Stack) ancientOne.buildMythDeck(mythCards);
        this.discardedCards = new Stack<>();
        this.elder = ancientOne;
        this.shirt = dealer.getCardShirts().get(CardType.MYTHOS);
    }

    public Card get() {
        if (cards.empty()) {
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

    public int discardCard(UUID cardId) {
        inGame
                .stream()
                .filter(mythCard -> mythCard.id.equals(cardId))
                .findFirst()
                .ifPresent(discardedCards::add);

        int index = -1;
        for (int i = 0; i < inGame.size(); i ++) {
            MythosCard card = inGame.get(i);
            if (card.id.equals(cardId)) {
                 inGame.remove(card);
                 index = i;
                 break;
            }
        }
        return index;
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
