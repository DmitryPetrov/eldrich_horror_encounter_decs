package main.java.horror;

import main.java.dealer.SourceLoader;
import main.java.horror.ancient.AncientName;
import main.java.horror.ancient.AncientOne;
import main.java.horror.ancient.MysteryDeck;
import main.java.horror.ancient.OuterSpace;
import main.java.horror.expedition.ExpeditionDeck;
import main.java.horror.expedition.ExpeditionLocation;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosDeck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private Map<CardType, Deck> decks = new HashMap<>();

    private final AncientOne ancient;
    private final MythosDeck myths;

    private final byte[] tableSurface;

    public Table(AncientName name, SourceLoader dealer, boolean easyMod) {
        this.ancient = OuterSpace.callTheAncientOne(name, dealer);
        decks.put(CardType.RESEARCH, ancient.getResearchDeck());
        decks.put(CardType.MYSTERY, ancient.getMysteryDeck());
        for (CardType cardType: CardType.values()) {
            if (cardType.special) {
                continue;
            }
            decks.put(cardType, new Deck(cardType, dealer));
        }
        decks.put(CardType.EXPEDITION, new ExpeditionDeck(dealer));
        myths = new MythosDeck(dealer, ancient, easyMod);
        this.tableSurface = dealer.getTableSurface();
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

    public MythosDeck getMythosDeck() {
        return myths;
    }

    public Card getCardShirts(CardType type) {
        return decks.get(type).getShirt();
    }

    public AncientOne getAncient() {
        return ancient;
    }

    public byte[] getTableSurface() {
        return tableSurface;
    }
}
