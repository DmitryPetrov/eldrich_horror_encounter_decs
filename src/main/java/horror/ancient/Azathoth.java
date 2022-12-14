package main.java.horror.ancient;

import main.java.dealer.SourceLoader;
import main.java.horror.Deck;

import java.util.Map;
import java.util.Optional;

public class Azathoth extends AncientOne {
    {
        TOP_GREEN_COUNT = 1;
        TOP_YELLOW_COUNT = 2;
        TOP_BLUE_COUNT = 1;
        MID_GREEN_COUNT = 2;
        MID_YELLOW_COUNT = 3;
        MID_BLUE_COUNT = 1;
        BOTTOM_GREEN_COUNT = 2;
        BOTTOM_YELLOW_COUNT = 4;
        BOTTOM_BLUE_COUNT = 0;
    }

    private final Deck researchDeck;
    private final Deck mysteryDeck;
    private final byte[] face;
    private final byte[] back;

    public Azathoth(SourceLoader dealer) {
        Map<String, byte[]> ancientCards = dealer.getAncientCards(AncientName.AZATHOTH);
        researchDeck = buildDeckOrGetNull(ancientCards, "research");
        mysteryDeck = buildDeckOrGetNull(ancientCards, "mystery");
        face = ancientCards.get("ancient.jpg");
        back = ancientCards.get("ancient_back.jpg");
    }

    public byte[] getFace() {
        return face;
    }

    public byte[] getBack() {
        return back;
    }

    public Deck getResearchDeck() {
        return researchDeck;
    }

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
