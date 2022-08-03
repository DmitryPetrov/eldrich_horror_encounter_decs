package main.java.horror.expedition;

import main.java.horror.Card;
import main.java.horror.CardType;

import java.util.UUID;

public class ExpeditionCard extends Card {

    public final ExpeditionLocation location;

    public ExpeditionCard(UUID id, CardType type, byte[] content, ExpeditionLocation location) {
        super(id, type, content);
        this.location = location;
    }
}
