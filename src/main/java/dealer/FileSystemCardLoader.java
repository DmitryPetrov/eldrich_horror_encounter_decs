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
    private final Map<ExpeditionLocation, String> expLocationMarks = new HashMap<>();
    private final Map<MythosColor, String> mythosColorMarks = new HashMap<>();
    private final Map<Complexity, String> mythosComplexityMarks = new HashMap<>();

    public FileSystemCardLoader(List<GameBase> bases) {
        gameBasePathSetUp();
        cardTypePathsSetUp();
        expLocationMarksSetUp();
        mythosColorMarksSetUp();
        mythosComplexityMarksSetUp();
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
        //cardTypePaths.put(CardType.RESEARCH, CardType.RESEARCH.name().toLowerCase(Locale.ROOT));
        //cardTypePaths.put(CardType.SPECIAL, CardType.SPECIAL.name().toLowerCase(Locale.ROOT));
    }

    private void expLocationMarksSetUp() {
        expLocationMarks.put(ExpeditionLocation.ANTARCTICA, "an");
        expLocationMarks.put(ExpeditionLocation.THE_AMAZON, "am");
        expLocationMarks.put(ExpeditionLocation.THE_HEARTH_OF_AFRICA, "hoa");
        expLocationMarks.put(ExpeditionLocation.THE_HIMALAYAS, "hm");
        expLocationMarks.put(ExpeditionLocation.THE_PYRAMIDS, "pr");
        expLocationMarks.put(ExpeditionLocation.TUNGUSKA, "tg");
    }

    private void mythosColorMarksSetUp() {
        mythosColorMarks.put(MythosColor.BLUE, MythosColor.BLUE.name().toLowerCase(Locale.ROOT));
        mythosColorMarks.put(MythosColor.GREEN, MythosColor.GREEN.name().toLowerCase(Locale.ROOT));
        mythosColorMarks.put(MythosColor.YELLOW, MythosColor.YELLOW.name().toLowerCase(Locale.ROOT));
    }

    private void mythosComplexityMarksSetUp() {
        mythosComplexityMarks.put(Complexity.EASY, Complexity.EASY.name().toLowerCase(Locale.ROOT));
        mythosComplexityMarks.put(Complexity.MID, Complexity.MID.name().toLowerCase(Locale.ROOT));
        mythosComplexityMarks.put(Complexity.HARD, Complexity.HARD.name().toLowerCase(Locale.ROOT));
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
        ExpeditionLocation location = null;
        if (file.getName().contains("am")) {
            location = ExpeditionLocation.THE_AMAZON;
        } else if (file.getName().contains("an")) {
            location = ExpeditionLocation.ANTARCTICA;
        } else if (file.getName().contains("hm")) {
            location = ExpeditionLocation.THE_HIMALAYAS;
        } else if (file.getName().contains("hoa")) {
            location = ExpeditionLocation.THE_HEARTH_OF_AFRICA;
        } else if (file.getName().contains("pr")) {
            location = ExpeditionLocation.THE_PYRAMIDS;
        } else if (file.getName().contains("tg")) {
            location = ExpeditionLocation.TUNGUSKA;
        } else {
            System.out.println("!!!!!!!!!!!!!! ExpeditionLocation: "  + file.getName());
        }
        return new ExpeditionCard(UUID.randomUUID(), CardType.EXPEDITION, readCardContent(file), location);
    }

    private Card readMythosCard(File file) {
        String[] cardInfo = file.getName().split("_");
        Complexity complexity = null;
        if (cardInfo[1].equalsIgnoreCase(Complexity.EASY.name())) {
            complexity = Complexity.EASY;
        } else if (cardInfo[1].equalsIgnoreCase(Complexity.MID.name())) {
            complexity = Complexity.MID;
        } else if (cardInfo[1].equalsIgnoreCase(Complexity.HARD.name())) {
            complexity = Complexity.HARD;
        } else {
            System.out.println("!!!!!!!!!!!!!! complexity: " + file.getName());
        }
        MythosColor mythosColor = null;
        if (cardInfo[0].equalsIgnoreCase(MythosColor.YELLOW.name())) {
            mythosColor = MythosColor.YELLOW;
        } else if (cardInfo[0].equalsIgnoreCase(MythosColor.BLUE.name())) {
            mythosColor = MythosColor.BLUE;
        } else if (cardInfo[0].equalsIgnoreCase(MythosColor.GREEN.name())) {
            mythosColor = MythosColor.GREEN;
        } else {
            System.out.println("!!!!!!!!!!!!!! color: " + file.getName());
        }
        boolean event = false;
        if (cardInfo[3].equalsIgnoreCase("event")) {
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
        for (File shirtFile : cardShirtFiles) {
            if (shirtFile.getName().contains("america")) {
                addCardShirt(shirtFile, CardType.AMERICA, shirts);
            }
            if (shirtFile.getName().contains("asia")) {
                addCardShirt(shirtFile, CardType.ASIA, shirts);
            }
            if (shirtFile.getName().contains("europe")) {
                addCardShirt(shirtFile, CardType.EUROPE, shirts);
            }
            if (shirtFile.getName().contains("gate")) {
                addCardShirt(shirtFile, CardType.GATE, shirts);
            }
            if (shirtFile.getName().contains("general")) {
                addCardShirt(shirtFile, CardType.GENERAL, shirts);
            }
            if (shirtFile.getName().contains("mythos")) {
                addCardShirt(shirtFile, CardType.MYTHOS, shirts);
            }
        }
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
            if (shirtFile.getName().contains("exp_am")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_AMAZON, shirts);
            }
            if (shirtFile.getName().contains("exp_an")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.ANTARCTICA, shirts);
            }
            if (shirtFile.getName().contains("exp_hm")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_HIMALAYAS, shirts);
            }
            if (shirtFile.getName().contains("exp_hoa")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_HEARTH_OF_AFRICA, shirts);
            }
            if (shirtFile.getName().contains("exp_pr")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_PYRAMIDS, shirts);
            }
            if (shirtFile.getName().contains("exp_tg")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.TUNGUSKA, shirts);
            }
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
