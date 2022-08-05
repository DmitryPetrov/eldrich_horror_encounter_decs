package main.java.horror.ancient;

import main.java.horror.myth.MythosColor;
import main.java.horror.myth.MythosCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class AncientOne {

    private final int TOP_GREEN_COUNT;
    private final int TOP_YELLOW_COUNT;
    private final int TOP_BLUE_COUNT;
    private final int MID_GREEN_COUNT;
    private final int MID_YELLOW_COUNT;
    private final int MID_BLUE_COUNT;
    private final int BOTTOM_GREEN_COUNT;
    private final int BOTTOM_YELLOW_COUNT;
    private final int BOTTOM_BLUE_COUNT;

    public AncientOne(
            int TOP_GREEN_COUNT,
            int TOP_YELLOW_COUNT,
            int TOP_BLUE_COUNT,
            int MID_GREEN_COUNT,
            int MID_YELLOW_COUNT,
            int MID_BLUE_COUNT,
            int BOTTOM_GREEN_COUNT,
            int BOTTOM_YELLOW_COUNT,
            int BOTTOM_BLUE_COUNT
    ) {
        this.TOP_GREEN_COUNT = TOP_GREEN_COUNT;
        this.TOP_YELLOW_COUNT = TOP_YELLOW_COUNT;
        this.TOP_BLUE_COUNT = TOP_BLUE_COUNT;
        this.MID_GREEN_COUNT = MID_GREEN_COUNT;
        this.MID_YELLOW_COUNT = MID_YELLOW_COUNT;
        this.MID_BLUE_COUNT = MID_BLUE_COUNT;
        this.BOTTOM_GREEN_COUNT = BOTTOM_GREEN_COUNT;
        this.BOTTOM_YELLOW_COUNT = BOTTOM_YELLOW_COUNT;
        this.BOTTOM_BLUE_COUNT = BOTTOM_BLUE_COUNT;
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

    public abstract void win();
    
    public abstract boolean inFirstPhase();

    public abstract void lose();
}
