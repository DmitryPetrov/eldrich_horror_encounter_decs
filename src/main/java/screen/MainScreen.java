package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.horror.Table;

public class MainScreen {

    Encounters encounters;
    Mythos mythos;
    AncientScreen ancientScreen;

    public MainScreen(Table table) {
        encounters = new Encounters(table);
        mythos = new Mythos(table);
        ancientScreen = new AncientScreen(table);
    }

    public Pane build() {
        encounters.buildContactsDeck();
        mythos.buildMythDeck();
        ancientScreen.buildScene();


        VBox screen = new VBox();
        AnchorPane field = new AnchorPane();

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(50, 50, 50, 50));
        buttons.setSpacing(10);

        Button encounterButton = new Button("Encounters");
        encounterButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(encounters.screen);
        });
        Button mythosButton = new Button("Mythos");
        mythosButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(mythos.screen);
        });
        Button ancientButton = new Button("Ancient");
        ancientButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(ancientScreen.screen);
        });


        buttons.getChildren().addAll(encounterButton, mythosButton, ancientButton);
        screen.getChildren().addAll(buttons, field);

        return screen;
    }
}
