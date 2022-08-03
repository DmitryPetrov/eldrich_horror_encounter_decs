package main.java.horror;

import java.util.UUID;

public class Card {

    public final UUID id;
    public final CardType type;
    public final byte[] content;

    public Card(UUID id, CardType type, byte[] content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }
}
