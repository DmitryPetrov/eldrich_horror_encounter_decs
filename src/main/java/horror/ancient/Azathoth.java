package main.java.horror.ancient;

import main.java.dealer.SourceLoader;
import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Deck;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        byte[] researchShirt = new byte[0];
        Set<Card> researchCards = new HashSet<>();
        for (String fileName: ancientCards.keySet().stream().filter(name -> name.contains("research")).collect(Collectors.toSet())) {
            if (fileName.contains("back")) {
                researchShirt = ancientCards.get(fileName);
                continue;
            }
            researchCards.add(new Card(UUID.randomUUID(), CardType.RESEARCH, ancientCards.get(fileName)));
        }
        researchDeck = new Deck(researchCards, researchShirt);

        byte[] mysteryShirt = new byte[0];
        Set<Card> mysteryCards = new HashSet<>();
        for (String fileName: ancientCards.keySet().stream().filter(name -> name.contains("mystery")).collect(Collectors.toSet())) {
            if (fileName.contains("back")) {
                mysteryShirt = ancientCards.get(fileName);
                continue;
            }
            mysteryCards.add(new Card(UUID.randomUUID(), CardType.RESEARCH, ancientCards.get(fileName)));
        }
        mysteryDeck = new Deck(mysteryCards, mysteryShirt);

        face = ancientCards.get("ancient.jpg");
        back = ancientCards.get("ancient_back.jpg");

        int t = 0;
    }

    @Override
    public void win() {
        return;
    }

    @Override
    public boolean inFirstPhase() {
        return true;
    }

    @Override
    public void lose() {

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
}
