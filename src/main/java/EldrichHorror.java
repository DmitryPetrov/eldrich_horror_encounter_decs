package main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.horror.Card;
import main.java.horror.CardType;
import main.java.horror.Table;
import main.java.horror.ancient.AncientOne;
import main.java.horror.ancient.Azathoth;
import main.java.horror.expedition.ExpeditionDeck;
import main.java.horror.myth.MythosCard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class EldrichHorror extends Application {

    Table table;
    GameBuilder gameBuilder;

    public EldrichHorror() {

        gameBuilder = new GameBuilder();
        AncientOne azathoth = new Azathoth();
        Set<Card> allCards = gameBuilder.readCards();
        table = new Table(azathoth, allCards, false);

    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        //buildMythDeck(primaryStage);
        //new ScanCards().scan();
        buildContactsDeck(primaryStage);
    }

    private void buildContactsDeck(Stage primaryStage) {
        VBox lines = new VBox();
        lines.setPadding(new Insets(10, 10, 10, 10));
        lines.setSpacing(10);

        ImageView cardFrontPlace = new ImageView();
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

        lines.getChildren().addAll(buttonRow, cardRow);

        primaryStage.setTitle("Древний Ужас");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setScene(new Scene(lines));
        primaryStage.show();
    }

    Button buildExpeditionButton(ImageView cardFrontPlace) {
        ExpeditionDeck expeditionDeck = table.getExpeditionDeck();

        Image cardShirtImage = new Image(new ByteArrayInputStream(
                gameBuilder.expeditionCardShirts.get(expeditionDeck.showNextCardLocation()).content
        ));
        ImageView cardShirt = new ImageView(cardShirtImage);
        cardShirt.setFitWidth(100);
        cardShirt.setFitHeight(150);
        Button mythosButton = new Button("", cardShirt);
        cardFrontPlace.setImage(cardShirtImage);
        cardFrontPlace.setFitWidth(500);
        cardFrontPlace.setFitHeight(730);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Image cardFront = new Image(new ByteArrayInputStream(table.getCard(CardType.EXPEDITION).content));
                cardFrontPlace.setImage(cardFront);
                cardFrontPlace.setFitWidth(500);
                cardFrontPlace.setFitHeight(730);
                cardShirt.setImage(
                        new Image(new ByteArrayInputStream(
                                gameBuilder.expeditionCardShirts.get(expeditionDeck.showNextCardLocation()).content
                )));
            }
        };
        mythosButton.setOnAction(event);
        return mythosButton;
    }

    Button buildContactButtons(CardType type, ImageView cardFrontPlace) {
        Image cardShirtImage = new Image(new ByteArrayInputStream(gameBuilder.cardShirts.get(type).content));
        ImageView cardShirt = new ImageView(cardShirtImage);
        cardShirt.setFitWidth(100);
        cardShirt.setFitHeight(150);
        Button mythosButton = new Button("", cardShirt);
        cardFrontPlace.setImage(cardShirtImage);
        cardFrontPlace.setFitWidth(500);
        cardFrontPlace.setFitHeight(730);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                cardFrontPlace.setImage(new Image(new ByteArrayInputStream(table.getCard(type).content)));
                cardFrontPlace.setFitWidth(500);
                cardFrontPlace.setFitHeight(730);
            }
        };
        mythosButton.setOnAction(event);
        return mythosButton;
    }

    private void buildMythDeck(Stage primaryStage) {
        HBox hbox = new HBox();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        hbox.getChildren().addAll(vbox);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ImageView cardFront = new ImageView();

        grid.add(buildMythButton(cardFront), 0, 0);
        grid.add(cardFront, 0, 1);

        vbox.getChildren().addAll(buildMythButton(cardFront), cardFront);

        primaryStage.setTitle("Древний Ужас");
        primaryStage.setWidth(500);
        primaryStage.setHeight(900);
        primaryStage.setScene(new Scene(hbox));
        primaryStage.show();
    }


    Button buildMythButton(ImageView cardFront) {
        Image cardShirtImage = new Image(new ByteArrayInputStream(gameBuilder.cardShirts.get(CardType.MYTH).content));
        ImageView cardShirt = new ImageView(cardShirtImage);
        cardShirt.setFitWidth(100);
        cardShirt.setFitHeight(150);
        Button mythosButton = new Button("", cardShirt);

        cardFront.setImage(cardShirtImage);
        cardFront.setFitWidth(300);
        cardFront.setFitHeight(500);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Image mythImage = new Image(new ByteArrayInputStream(table.getMythos().content));
                cardFront.setImage(mythImage);
                cardFront.setFitWidth(300);
                cardFront.setFitHeight(500);
            }
        };

        mythosButton.setOnAction(event);

        return mythosButton;
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        new EldrichHorror();
    }



}
