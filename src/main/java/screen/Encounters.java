package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.horror.CardType;
import main.java.horror.Table;
import main.java.horror.expedition.ExpeditionDeck;

import java.io.ByteArrayInputStream;

public class Encounters {

    Table table;
    Pane screen;


    public Encounters(Table table) {
        this.table = table;
    }


    public Pane buildContactsDeck() {
        VBox lines = new VBox();
        lines.setPadding(new Insets(10, 10, 10, 10));
        lines.setSpacing(10);

        CardPlace cardFrontPlace = new CardPlace(table.getCardShirts(CardType.GENERAL));
        HBox cardRow = new HBox();
        cardRow.getChildren().add(cardFrontPlace);

        HBox buttonRow = new HBox();
        for (CardType type: CardType.values()) {
            if (type.isSpecial()) {
                continue;
            }
            buttonRow.getChildren().add(buildContactButtons(type, cardFrontPlace));
        }
        buttonRow.getChildren().add(buildExpeditionButton(cardFrontPlace));
        buttonRow.getChildren().add(buildContactButtons(CardType.RESEARCH, cardFrontPlace));

        lines.getChildren().addAll(buttonRow, cardRow);

        screen = lines;
        return lines;
    }

    Button buildContactButtons(CardType type, CardPlace cardFrontPlace) {
        Button mythosButton = new CardButton(table.getCardShirts(type));
        cardFrontPlace.updateCard(table.getCardShirts(type));
        mythosButton.setOnAction(
                e -> cardFrontPlace.updateCard(table.getCard(type))
        );
        return mythosButton;
    }

    Button buildExpeditionButton(CardPlace cardPlace) {
        ExpeditionDeck expeditionDeck = table.getExpeditionDeck();

        Image cardShirtImage = new Image(new ByteArrayInputStream(expeditionDeck.showNextCardLocation().content));
        ImageView cardShirt = new ImageView(cardShirtImage);
        cardShirt.setFitWidth(82);
        cardShirt.setFitHeight(126);
        Button mythosButton = new Button("", cardShirt);

        mythosButton.setOnAction(e -> {
            cardPlace.updateCard(table.getCard(CardType.EXPEDITION));
            cardShirt.setImage(new Image(new ByteArrayInputStream(expeditionDeck.showNextCardLocation().content)));
        });
        return mythosButton;
    }

}
