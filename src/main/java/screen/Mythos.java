package main.java.screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.horror.CardType;

import java.io.ByteArrayInputStream;

public class Mythos {


/*
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
*/

}
