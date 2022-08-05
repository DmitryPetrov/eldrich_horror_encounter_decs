package main.java.horror.myth;

import main.java.horror.Card;
import main.java.horror.CardType;

import java.util.UUID;

public class MythosCard extends Card {

    public final Complexity complexity;
    public final MythosColor mythosColor;
    public final boolean event;

    public MythosCard(UUID id, CardType type, byte[] content, Complexity complexity, MythosColor mythosColor, boolean event) {
        super(id, type, content);
        this.complexity = complexity;
        this.mythosColor = mythosColor;
        this.event = event;
    }

}
