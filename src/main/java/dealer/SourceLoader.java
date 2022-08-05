package main.java.dealer;

import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.expedition.ExpeditionLocation;

import java.util.Map;
import java.util.Set;

public interface SourceLoader {

    Set<Card> getCards(CardType type);

    Map<CardType, Card> getCardShirts();

    Map<ExpeditionLocation, Card> getCardExpeditionShirts();
}
