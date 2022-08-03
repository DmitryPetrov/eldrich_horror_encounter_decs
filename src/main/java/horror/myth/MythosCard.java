package main.java.horror.myth;

import main.java.horror.Card;
import main.java.horror.CardType;

import java.util.UUID;

public class MythosCard extends Card {

    public final Complexity complexity;
    public final Color color;
    public final boolean event;

    public MythosCard(UUID id, CardType type, byte[] content, Complexity complexity, Color color, boolean event) {
        super(id, type, content);
        this.complexity = complexity;
        this.color = color;
        this.event = event;
    }

}
