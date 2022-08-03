package main.java.horror;

import main.java.horror.ancient.AncientOne;
import main.java.horror.expedition.ExpeditionDeck;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosDeck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Table {

    private Set<Card> allCards;
    private Map<CardType, Deck> decks = new HashMap<>();

    private final AncientOne ancient;
    private final MythosDeck myths;
    //private final MysteryDeck mysteries;

    public Table(AncientOne ancient, Set<Card> allCards, boolean easyMod) {
        this.ancient = ancient;
        this.allCards = allCards;
        for (CardType cardType: CardType.values()) {
            if (cardType.special) {
                continue;
            }
            decks.put(cardType, new Deck(getCards(cardType)));
        }
        decks.put(CardType.EXPEDITION, new ExpeditionDeck(getCards(CardType.EXPEDITION)));
        myths = new MythosDeck(getCards(CardType.MYTH), ancient, easyMod);
        //mysteries = new MysteryDeck(getCards(CardType.MYSTERY), ancient);
    }

    private List<Card> getCards(CardType cardType) {
        return allCards
                .stream()
                .filter(card -> card.type.equals(cardType))
                .collect(Collectors.toList());
    }

    public Card getCard(CardType cardType) {
        return decks.get(cardType).get();
    }

    public ExpeditionDeck getExpeditionDeck() {
        return (ExpeditionDeck) decks.get(CardType.EXPEDITION);
    }

    public MythosCard getMythos() {
        return (MythosCard) myths.get();
    }

    public List<Card> showMythosDeck() {
        return myths.showDeck();
    }
}
