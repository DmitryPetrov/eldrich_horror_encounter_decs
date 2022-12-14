package main.java.horror.ancient;

import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Deck;
import main.java.horror.myth.MythosColor;
import main.java.horror.myth.MythosCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AncientOne {

    protected int TOP_GREEN_COUNT;
    protected int TOP_YELLOW_COUNT;
    protected int TOP_BLUE_COUNT;
    protected int MID_GREEN_COUNT;
    protected int MID_YELLOW_COUNT;
    protected int MID_BLUE_COUNT;
    protected int BOTTOM_GREEN_COUNT;
    protected int BOTTOM_YELLOW_COUNT;
    protected int BOTTOM_BLUE_COUNT;

    public AncientOne() {
    }

    public Stack<MythosCard> buildMythDeck(List<MythosCard> cards) {
        Stack<MythosCard> greenCards = extractAndShuffle(cards, MythosColor.GREEN);
        Stack<MythosCard> yellowCards = extractAndShuffle(cards, MythosColor.YELLOW);
        Stack<MythosCard> blueCards = extractAndShuffle(cards, MythosColor.BLUE);

        List<MythosCard> bottomLayer = new ArrayList<>();
        moveCards(greenCards, bottomLayer, BOTTOM_GREEN_COUNT);
        moveCards(yellowCards, bottomLayer, BOTTOM_YELLOW_COUNT);
        moveCards(blueCards, bottomLayer, BOTTOM_BLUE_COUNT);
        Collections.shuffle(bottomLayer);

        List<MythosCard> midLayer = new ArrayList<>();
        moveCards(greenCards, midLayer, MID_GREEN_COUNT);
        moveCards(yellowCards, midLayer, MID_YELLOW_COUNT);
        moveCards(blueCards, midLayer, MID_BLUE_COUNT);
        Collections.shuffle(midLayer);

        List<MythosCard> topLayer = new ArrayList<>();
        moveCards(greenCards, topLayer, TOP_GREEN_COUNT);
        moveCards(yellowCards, topLayer, TOP_YELLOW_COUNT);
        moveCards(blueCards, topLayer, TOP_BLUE_COUNT);
        Collections.shuffle(topLayer);

        Stack<MythosCard> mythosDeck = new Stack<>();
        mythosDeck.addAll(bottomLayer);
        mythosDeck.addAll(midLayer);
        mythosDeck.addAll(topLayer);

        return mythosDeck;
    }

    private void moveCards(Stack<MythosCard> source, List<MythosCard> target, int count) {
        for (int i = 0; i < count; i++) {
            target.add(source.pop());
        }
    }

    private Stack<MythosCard> extractAndShuffle(List<MythosCard> cards, MythosColor mythosColor) {
        List<MythosCard> filtred = cards
                .stream()
                .filter(mythosCard -> mythosCard.mythosColor.equals(mythosColor))
                .collect(Collectors.toList());
        Collections.shuffle(filtred);
        Stack<MythosCard> mythosCards = new Stack<>();
        mythosCards.addAll(filtred);
        return mythosCards;
    }

    protected Deck buildDeckOrGetNull(Map<String, byte[]> ancientCards, String deckType) {
        byte[] researchShirt = new byte[0];
        Set<Card> researchCards = new HashSet<>();

        Set<String> cardsByType = ancientCards
                .keySet()
                .stream()
                .filter(name -> name.contains(deckType))
                .collect(Collectors.toSet());
        if (cardsByType.isEmpty()) {
            return null;
        }

        for (String fileName: cardsByType) {
            if (fileName.contains("back")) {
                researchShirt = ancientCards.get(fileName);
                continue;
            }
            researchCards.add(new Card(UUID.randomUUID(), CardType.RESEARCH, ancientCards.get(fileName)));
        }
        return new Deck(researchCards, researchShirt);
    }

    public abstract byte[] getFace();

    public abstract byte[] getBack();

    public abstract Deck getResearchDeck();

    public abstract Deck getMysteryDeck();

    public abstract Optional<Deck> getSpecialDeckOne();

    public abstract Optional<Deck> getSpecialDeckTwo();

}
