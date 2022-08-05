package main.java;

import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.expedition.ExpeditionCard;
import main.java.horror.expedition.ExpeditionLocation;
import main.java.horror.myth.MythosColor;
import main.java.horror.myth.Complexity;
import main.java.horror.myth.MythosCard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameBuilder {

    public final Map<CardType, Card> cardShirts;
    public final Map<ExpeditionLocation, Card> expeditionCardShirts;

    private final List<String> contactPaths;
    private final String CARDS_PATH = "cards";
    private final String COMPONENTS_PATH = "./components";

    public GameBuilder() {
        contactPaths = List.of("america", "asia", "europe", "expedition", "general", "mythos");
        cardShirts = new HashMap<>();
        expeditionCardShirts = new HashMap<>();
    }


    public Set<Card> readCards() {
        Set<Card> cards = new HashSet<>();

        Set<File> bases = Stream.of(Objects.requireNonNull(new File(COMPONENTS_PATH).listFiles()))
                .collect(Collectors.toSet());

        for (File base: bases) {
            if (base.getName().equals("card_shirts")) {
                readCardShirts(base);
                continue;
            }

            File cardsDir = Arrays.stream(Objects.requireNonNull(base.listFiles()))
                    .filter(file -> file.getName().equalsIgnoreCase(CARDS_PATH))
                    .findFirst()
                    .orElseThrow();

            File[] cardTypes = cardsDir.listFiles();
            for (File cardTypeDir: cardTypes) {
                if (cardTypeDir.getName().equals("mythos")) {
                    cards.addAll(readMythCards(cardTypeDir));
                }
                if (cardTypeDir.getName().equals("america")) {
                    cards.addAll(readContactCards(cardTypeDir, CardType.AMERICA));
                }
                if (cardTypeDir.getName().equals("asia")) {
                    cards.addAll(readContactCards(cardTypeDir, CardType.ASIA));
                }
                if (cardTypeDir.getName().equals("europe")) {
                    cards.addAll(readContactCards(cardTypeDir, CardType.EUROPE));
                }
                if (cardTypeDir.getName().equals("gate")) {
                    cards.addAll(readContactCards(cardTypeDir, CardType.GATE));
                }
                if (cardTypeDir.getName().equals("general")) {
                    cards.addAll(readContactCards(cardTypeDir, CardType.GENERAL));
                }
                if (cardTypeDir.getName().equals("expedition")) {
                    cards.addAll(readExpeditionCards(cardTypeDir));
                }
            }
        }
        return cards;
    }

    private Set<Card> readExpeditionCards(File cardTypeDir) {
        List<File> mythCards = Arrays.stream(Objects.requireNonNull(cardTypeDir.listFiles()))
                .collect(Collectors.toList());
        return mythCards.stream()
                .map(this::readExpeditionCard)
                .collect(Collectors.toSet());
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
        return new ExpeditionCard(UUID.randomUUID(), CardType.EXPEDITION, getContent(file), location);
    }

    private void readCardShirts(File cardShirtDir) {
        List<File> cardShirtFiles = Arrays.stream(Objects.requireNonNull(cardShirtDir.listFiles()))
                .collect(Collectors.toList());
        for (File shirtFile: cardShirtFiles) {
            if (shirtFile.getName().contains("america")) {
                addCardShirt(shirtFile, CardType.AMERICA);
            }
            if (shirtFile.getName().contains("asia")) {
                addCardShirt(shirtFile, CardType.ASIA);
            }
            if (shirtFile.getName().contains("europe")) {
                addCardShirt(shirtFile, CardType.EUROPE);
            }
            if (shirtFile.getName().contains("gate")) {
                addCardShirt(shirtFile, CardType.GATE);
            }
            if (shirtFile.getName().contains("general")) {
                addCardShirt(shirtFile, CardType.GENERAL);
            }
            if (shirtFile.getName().contains("mythos")) {
                addCardShirt(shirtFile, CardType.MYTH);
            }
            if (shirtFile.getName().contains("exp_am")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_AMAZON);
            }
            if (shirtFile.getName().contains("exp_an")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.ANTARCTICA);
            }
            if (shirtFile.getName().contains("exp_hm")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_HIMALAYAS);
            }
            if (shirtFile.getName().contains("exp_hoa")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_HEARTH_OF_AFRICA);
            }
            if (shirtFile.getName().contains("exp_pr")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.THE_PYRAMIDS);
            }
            if (shirtFile.getName().contains("exp_tg")) {
                addExpeditionCardShirt(shirtFile, CardType.EXPEDITION, ExpeditionLocation.TUNGUSKA);
            }
        }
    }

    private void addCardShirt(File shirtFile, CardType type) {
        cardShirts.put(
                type,
                new Card(UUID.randomUUID(), type, getContent(shirtFile))
        );
    }

    private void addExpeditionCardShirt(File shirtFile, CardType type, ExpeditionLocation location) {
        expeditionCardShirts.put(
                location,
                new ExpeditionCard(UUID.randomUUID(), type, getContent(shirtFile), location)
        );
    }

    private Set<Card> readContactCards(File cardTypeDir, CardType type) {
        List<File> cardFiles = Arrays.stream(Objects.requireNonNull(cardTypeDir.listFiles()))
                .collect(Collectors.toList());
        Set<Card> cards = new HashSet<>();
        for (File file: cardFiles) {
            cards.add(new Card(UUID.randomUUID(), type, getContent(file)));
        }
        return cards;
    }

    private Set<Card> readMythCards(File mythCardsDir) {
        List<File> mythCards = Arrays.stream(Objects.requireNonNull(mythCardsDir.listFiles()))
                .collect(Collectors.toList());
        return mythCards.stream()
                .map(this::readMythosCard)
                .collect(Collectors.toSet());
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
                CardType.MYTH,
                getContent(file),
                complexity,
                mythosColor,
                event
        );
        return card;
    }

    private byte[] getContent(File file) {
        try {
            return new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Read card file: " + file.getPath() + file.getName());
    }
}
