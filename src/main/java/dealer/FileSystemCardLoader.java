package main.java.dealer;

import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.GameBase;
import main.java.horror.expedition.ExpeditionCard;
import main.java.horror.expedition.ExpeditionLocation;
import main.java.horror.myth.Complexity;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileSystemCardLoader {
    private final String COMPONENTS_PATH = "./components";
    private final String CARDS_PATH = "cards";
    private final List<GameBase> bases;
    private Set<File> selectedBaseDirs = new HashSet<>();

    private final Map<GameBase, String> gameBasePaths = new HashMap<>();
    private final Map<CardType, String> cardTypePaths = new HashMap<>();
    private final Map<String, ExpeditionLocation> expLocationMarks = new HashMap<>();

    public FileSystemCardLoader(List<GameBase> bases) {
        gameBasePathSetUp();
        cardTypePathsSetUp();
        expLocationMarksSetUp();
        this.bases = new ArrayList<>(bases);
        getBaseDirs();
    }

    private void gameBasePathSetUp() {
        gameBasePaths.put(GameBase.ORIGIN, GameBase.ORIGIN.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.FORSAKEN_LORE, GameBase.FORSAKEN_LORE.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.MOUNTAINS_OF_MADNESS, GameBase.MOUNTAINS_OF_MADNESS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.STRANGE_REMNANTS, GameBase.STRANGE_REMNANTS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.UNDER_THE_PYRAMIDS, GameBase.UNDER_THE_PYRAMIDS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.SIGNS_OF_CARCOSA, GameBase.SIGNS_OF_CARCOSA.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.DREAMLANDS, GameBase.DREAMLANDS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.CITIES_IN_RUIN, GameBase.CITIES_IN_RUIN.name().toLowerCase(Locale.ROOT));
    }

    private void cardTypePathsSetUp() {
        cardTypePaths.put(CardType.AMERICA, CardType.AMERICA.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.EUROPE, CardType.EUROPE.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.ASIA, CardType.ASIA.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.GENERAL, CardType.GENERAL.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.EXPEDITION, CardType.EXPEDITION.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.GATE, CardType.GATE.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.MYTHOS, CardType.MYTHOS.name().toLowerCase(Locale.ROOT));
        cardTypePaths.put(CardType.MYSTERY, CardType.MYSTERY.name().toLowerCase(Locale.ROOT));
    }

    private void expLocationMarksSetUp() {
        expLocationMarks.put("an", ExpeditionLocation.ANTARCTICA);
        expLocationMarks.put("am", ExpeditionLocation.THE_AMAZON);
        expLocationMarks.put("hoa", ExpeditionLocation.THE_HEARTH_OF_AFRICA);
        expLocationMarks.put("hm", ExpeditionLocation.THE_HIMALAYAS);
        expLocationMarks.put("pr", ExpeditionLocation.THE_PYRAMIDS);
        expLocationMarks.put("tg", ExpeditionLocation.TUNGUSKA);
    }

    private void getBaseDirs() {
        File[] dirs = new File(COMPONENTS_PATH).listFiles();
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(COMPONENTS_PATH + " folder is empty");
        }
        for (File dir: dirs) {
            if (bases
                    .stream()
                    .map(gameBasePaths::get)
                    .anyMatch(path -> path.equals(dir.getName()))
            ) {
                selectedBaseDirs.add(dir);
            }
        }
    }

    public Set<Card> getCards(CardType type) {
        Set<Card> cards = new HashSet<>();
        for (File baseDir : selectedBaseDirs) {
            File cardsDir = openDir(baseDir, CARDS_PATH);
            File typeCardsDir = openDir(cardsDir, cardTypePaths.get(type));
            cards.addAll(
                    collectCardFiles(typeCardsDir)
                            .stream()
                            .map(cardFile -> buildCards(cardFile, type))
                            .collect(Collectors.toSet())
            );
        }
        return cards;
    }

    private Set<File> collectCardFiles(File typeCardsDir) {
        return Arrays.stream(Objects.requireNonNull(typeCardsDir.listFiles()))
                .collect(Collectors.toSet());
    }

    private Card buildCards(File cardFile, CardType type) {
        if (type.equals(CardType.EXPEDITION)) {
            return readExpeditionCard(cardFile);
        } else if (type.equals(CardType.MYTHOS)) {
            return readMythosCard(cardFile);
        }
        return new Card(UUID.randomUUID(), type, readCardContent(cardFile));
    }

    private Card readExpeditionCard(File file) {
        String[] nameParts = file.getName().split("_");
        ExpeditionLocation location = expLocationMarks.get(nameParts[0]);
        return new ExpeditionCard(UUID.randomUUID(), CardType.EXPEDITION, readCardContent(file), location);
    }

    private Card readMythosCard(File file) {
        String[] cardInfo = file.getName().split("_");
        Complexity complexity = Complexity.valueOf(cardInfo[1].toUpperCase());
        MythosColor mythosColor = MythosColor.valueOf(cardInfo[0].toUpperCase());
        boolean event = false;
        if (cardInfo[2].equalsIgnoreCase("event")) {
            event = true;
        }
        Card card = new MythosCard(
                UUID.randomUUID(),
                CardType.MYTHOS,
                readCardContent(file),
                complexity,
                mythosColor,
                event
        );
        return card;
    }

    private byte[] readCardContent(File file) {
        try {
            return new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Read card error: " + file.getPath() + file.getName());
    }

    private File openDir(File baseDir, String dirName) {
        File[] dirs = baseDir.listFiles((dir, name) -> name.equals(dirName));
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(baseDir.getPath() + " does not contain [" + dirName + "] dir");
        }
        return dirs[0];
    }

    public Map<CardType, Card> getCardShirts() {
        Map<CardType, Card> shirts = new HashMap<>();
        File[] dirs = new File(COMPONENTS_PATH).listFiles((dir, name) -> name.equals("card_shirts"));
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(COMPONENTS_PATH + " folder is empty");
        }
        File cardShirtDir = dirs[0];
        List<File> cardShirtFiles = Arrays.stream(Objects.requireNonNull(cardShirtDir.listFiles()))
                .collect(Collectors.toList());
        for (CardType cardType: CardType.values()) {
            if (cardType.isSpecial()) {
                continue;
            }
            cardShirtFiles
                    .stream()
                    .filter(file -> file.getName().contains(cardType.name().toLowerCase()))
                    .findFirst()
                    .ifPresent(file -> addCardShirt(file, cardType, shirts));
        }
        cardShirtFiles
                .stream()
                .filter(file -> file.getName().contains(CardType.MYTHOS.name().toLowerCase()))
                .findFirst()
                .ifPresent(file -> addCardShirt(file, CardType.MYTHOS, shirts));
        return shirts;
    }

    private void addCardShirt(File shirtFile, CardType type, Map<CardType, Card> shirts) {
        shirts.put(type, new Card(UUID.randomUUID(), type, readCardContent(shirtFile)));
    }

    public Map<ExpeditionLocation, Card> getCardExpeditionShirts() {
        Map<ExpeditionLocation, Card> shirts = new HashMap<>();
        File[] dirs = new File(COMPONENTS_PATH).listFiles((dir, name) -> name.equals("card_shirts"));
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(COMPONENTS_PATH + " folder is empty");
        }
        File cardShirtDir = dirs[0];
        List<File> cardShirtFiles = Arrays.stream(Objects.requireNonNull(cardShirtDir.listFiles()))
                .collect(Collectors.toList());
        for (File shirtFile: cardShirtFiles) {
            if (!shirtFile.getName().contains("exp")) {
                continue;
            }
            String locationMark = shirtFile
                    .getName()
                    .split("_")[1]
                    .replace(".jpg", "");
            addExpeditionCardShirt(
                    shirtFile,
                    CardType.EXPEDITION,
                    expLocationMarks.get(locationMark),
                    shirts
            );
        }
        return shirts;
    }

    private void addExpeditionCardShirt(File shirtFile, CardType type, ExpeditionLocation location, Map<ExpeditionLocation, Card> shirts) {
        shirts.put(
                location,
                new ExpeditionCard(UUID.randomUUID(), type, readCardContent(shirtFile), location)
        );
    }
}
