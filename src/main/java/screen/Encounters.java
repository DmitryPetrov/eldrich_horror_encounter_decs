package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import main.java.horror.CardType;
import main.java.horror.Table;
import main.java.horror.expedition.ExpeditionDeck;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class Encounters {

    private final Table table;
    public final Pane screen;

    private Pane encounterDecs;

    public Encounters(Table table) {
        this.table = table;
        buildContactsDeck();
        HBox scene = new HBox();
        HBox tablet = new HBox();
        tablet.setPadding(new Insets(50, 50, 50, 50));
        tablet.setSpacing(50);
        tablet.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1)
        )));
        tablet.getChildren().add(encounterDecs);
        scene.getChildren().add(tablet);
        scene.setPadding(new Insets(0, 50, 0, 50));
        screen = scene;
    }


    public void buildContactsDeck() {
        HBox encounters = new HBox();
        encounters.setSpacing(50);
        CardPlace cardFrontPlace = new CardPlace(table.getCardShirts(CardType.GENERAL), true);
        HBox card = new HBox();
        card.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        card.setPadding(new Insets(15, 15, 15, 15));
        card.getChildren().add(cardFrontPlace);
        VBox buttonRows = new VBox();
        buttonRows.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        buttonRows.setPadding(new Insets(15, 15, 15, 15));
        buttonRows.setSpacing(30);
        ArrayList<Button> buttonList = new ArrayList<>();
        for (CardType type : table.getDeckTypes()) {
            if (type.equals(CardType.EXPEDITION)) {
                buttonList.add(buildExpeditionButton(cardFrontPlace));
                continue;
            }
            if (type.equals(CardType.MYSTERY)) {
                continue;
            }
            buttonList.add(buildContactButtons(type, cardFrontPlace));
        }
        for (int i = 0; i < buttonList.size(); i = i + 3) {
            HBox buttonRow = new HBox();
            buttonRow.setSpacing(30);
            buttonRows.getChildren().add(buttonRow);
            for (int j = 0; j < 3; j++) {
                if (i + j >= buttonList.size()) {
                    break;
                }
                buttonRow.getChildren().add(buttonList.get(i + j));
            }
        }
        encounters.getChildren().addAll(buttonRows, card);
        encounterDecs = encounters;
    }

    Button buildContactButtons(CardType type, CardPlace cardFrontPlace) {
        Button button = new CardButton(table.getCardShirts(type));
        cardFrontPlace.updateCard(table.getCardShirts(type));
        button.setOnAction(
                e -> cardFrontPlace.updateCard(table.getCard(type))
        );
        return button;
    }

    Button buildExpeditionButton(CardPlace cardPlace) {
        ExpeditionDeck expeditionDeck = table.getExpeditionDeck();
        Image cardShirtImage = new Image(new ByteArrayInputStream(expeditionDeck.showNextCardLocation().content));
        ImageView cardShirt = new ImageView(cardShirtImage);
        cardShirt.setFitWidth(114);
        cardShirt.setFitHeight(178);
        Button button = new Button("", cardShirt);
        button.setBackground(null);
        button.setOnMouseEntered(e -> {});
        button.setOnAction(e -> {
            cardPlace.updateCard(table.getCard(CardType.EXPEDITION));
            cardShirt.setImage(new Image(new ByteArrayInputStream(expeditionDeck.showNextCardLocation().content)));
        });
        return button;
    }

}
