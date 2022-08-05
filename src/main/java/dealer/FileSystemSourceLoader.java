package main.java.dealer;

import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.GameBase;
import main.java.horror.ancient.AncientName;
import main.java.horror.expedition.ExpeditionLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileSystemSourceLoader implements SourceLoader {

    private final List<GameBase> bases;

    private final FileSystemCardLoader cardloader;
    private final FileSystemAncientLoader ancientLoader;

    public FileSystemSourceLoader(List<GameBase> bases) {
        this.bases = new ArrayList<>(bases);
        this.cardloader = new FileSystemCardLoader(bases);
        this.ancientLoader = new FileSystemAncientLoader(bases);
    }

    @Override
    public Set<Card> getCards(CardType type) {
        return cardloader.getCards(type);
    }

    @Override
    public Map<CardType, Card> getCardShirts() {
        return cardloader.getCardShirts();
    }

    @Override
    public Map<ExpeditionLocation, Card> getCardExpeditionShirts() {
        return cardloader.getCardExpeditionShirts();
    }

    @Override
    public Map<String, byte[]> getAncientCards(AncientName name) {
        return ancientLoader.getAncientCards(name);
    }

}
