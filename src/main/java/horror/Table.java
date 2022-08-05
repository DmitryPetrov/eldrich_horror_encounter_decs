package main.java.horror;

import main.java.dealer.SourceLoader;
import main.java.horror.ancient.AncientOne;
import main.java.horror.expedition.ExpeditionDeck;
import main.java.horror.expedition.ExpeditionLocation;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosDeck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private Map<CardType, Deck> decks = new HashMap<>();

    public final Map<CardType, Card> cardShirts;
    public final Map<ExpeditionLocation, Card> expeditionCardShirts;

    private final AncientOne ancient;
    private final MythosDeck myths;
    //private final MysteryDeck mysteries;

    public Table(AncientOne ancient, SourceLoader dealer, boolean easyMod) {
        this.ancient = ancient;
        for (CardType cardType: CardType.values()) {
            if (cardType.special) {
                continue;
            }
            decks.put(cardType, new Deck(cardType, dealer));
        }
        decks.put(CardType.EXPEDITION, new ExpeditionDeck(dealer));
        myths = new MythosDeck(dealer, ancient, easyMod);
        //mysteries = new MysteryDeck(getCards(CardType.MYSTERY), ancient);
        cardShirts = dealer.getCardShirts();
        expeditionCardShirts = dealer.getCardExpeditionShirts();
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
