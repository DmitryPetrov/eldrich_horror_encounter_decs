package main.java.horror.ancient;

import main.java.dealer.SourceLoader;
import main.java.horror.Deck;

import java.util.Map;
import java.util.Optional;

public class ShubNiggurath extends AncientOne {
    {
        TOP_GREEN_COUNT = 1;
        TOP_YELLOW_COUNT = 2;
        TOP_BLUE_COUNT = 1;
        MID_GREEN_COUNT = 3;
        MID_YELLOW_COUNT = 2;
        MID_BLUE_COUNT = 1;
        BOTTOM_GREEN_COUNT = 2;
        BOTTOM_YELLOW_COUNT = 4;
        BOTTOM_BLUE_COUNT = 0;
    }

    private final Deck researchDeck;
    private final Deck mysteryDeck;
    private final byte[] face;
    private final byte[] back;

    public ShubNiggurath(SourceLoader dealer) {
        Map<String, byte[]> ancientCards = dealer.getAncientCards(AncientName.SHUB_NIGGURATH);
        researchDeck = buildDeckOrGetNull(ancientCards, "research");
        mysteryDeck = buildDeckOrGetNull(ancientCards, "mystery");
        face = ancientCards.get("ancient.jpg");
        back = ancientCards.get("ancient_back.jpg");
    }

    @Override
    public byte[] getFace() {
        return face;
    }

    @Override
    public byte[] getBack() {
        return back;
    }

    @Override
    public Deck getResearchDeck() {
        return researchDeck;
    }

    @Override
    public Deck getMysteryDeck() {
        return mysteryDeck;
    }

    @Override
    public Optional<Deck> getSpecialDeckOne() {
        return Optional.empty();
    }

    @Override
    public Optional<Deck> getSpecialDeckTwo() {
        return Optional.empty();
    }
}
