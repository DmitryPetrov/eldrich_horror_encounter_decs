package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.horror.Card;
import main.java.horror.Table;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosDeck;

import java.io.ByteArrayInputStream;

public class Mythos {

    private Table table;
    private MythosDeck mythosDeck;
    private Pane processField;
    private Pane tablet;
    private Button newMythosButton;
    Pane screen;

    public Mythos(Table table) {
        this.table = table;
        this.mythosDeck = table.getMythosDeck();
        buildMythButton();

        HBox scene = new HBox();
        HBox tablet = new HBox();
        tablet.setPadding(new Insets(50, 50, 50, 50));
        tablet.setSpacing(50);
        tablet.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1)
        )));
        tablet.getChildren().addAll(newMythosButton);
        this.tablet = tablet;
        scene.getChildren().add(tablet);
        scene.setPadding(new Insets(0, 50, 0, 50));
        screen = scene;
    }

    private void buildMythButton() {
        Image shirt = new Image(new ByteArrayInputStream(mythosDeck.getShirt().content));
        ImageView cardPlace = new ImageView(shirt);
        cardPlace.setFitWidth(456);
        cardPlace.setFitHeight(712);
        newMythosButton = new Button("", cardPlace);
        newMythosButton.setBackground(null);
        newMythosButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(1)
        )));
        newMythosButton.setPadding(new Insets(15, 15, 15, 15));
        newMythosButton.setOnAction(e -> {
            MythosCard mythos = table.getMythos();
            if (mythos == null) {
                return;
            }
            cardPlace.setImage(new Image(new ByteArrayInputStream(mythos.content)));
            cardPlace.setFitWidth(456);
            cardPlace.setFitHeight(712);
            if (!mythos.event) {
                addProcessCard(mythos);
            }
        });
    }

    private void buildProcessField() {
        HBox box = new HBox();
        processField = box;
        box.setPadding(new Insets(15, 15, 15, 15));
        box.setSpacing(50);
        box.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(1)
        )));
        tablet.getChildren().add(processField);
    }

    private void addProcessCard(Card card) {
        if (processField == null) {
            buildProcessField();
        }
        VBox process = new VBox();
        process.setSpacing(20);
        process.setAlignment(Pos.TOP_CENTER);
        CardPlace processCard = new CardPlace(card);
        Button discardButton = new Button("Discard");
        discardButton.setBackground(null);
        discardButton.setFont(Font.font("Courier new bold", 30));
        discardButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        process.getChildren().addAll(buildCounter(), processCard, discardButton);
        discardButton.setOnAction(actionEvent -> {
            int index = mythosDeck.discardCard(card.id);
            processField.getChildren().remove(index);
        });
        processField.getChildren().add(process);
    }

    private HBox buildCounter() {
        int fontSize = 27;

        HBox counterPane = new HBox();
        counterPane.setAlignment(Pos.BASELINE_CENTER);

        Label label = new Label("0");
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setFont(Font.font("Courier new bold", 30));
        label.setMinWidth(100);

        Button plusButton = new Button("＋");
        plusButton.setFont(Font.font("Courier new bold", fontSize));
        plusButton.setBackground(null);
        plusButton.setMinWidth(100);
        plusButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        plusButton.setOnAction(event -> {
            int counter = Integer.parseInt(label.getText());
            label.setText(String.valueOf(counter + 1));
        });

        Button minusButton = new Button("－");
        minusButton.setBackground(null);
        minusButton.setFont(Font.font("Courier new bold", fontSize));
        minusButton.setMinWidth(100);
        minusButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        minusButton.setOnAction(event -> {
            int counter = Integer.parseInt(label.getText());
            label.setText(String.valueOf(counter - 1));
        });

        counterPane.getChildren().addAll(minusButton, label, plusButton);
        return counterPane;
    }

}
