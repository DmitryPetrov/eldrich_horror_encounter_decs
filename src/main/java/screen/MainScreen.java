package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.java.horror.Table;

import java.io.ByteArrayInputStream;

public class MainScreen {

    Encounters encounters;
    Mythos mythos;
    AncientScreen ancientScreen;
    Table table;

    public MainScreen(Table table) {
        this.table = table;
        encounters = new Encounters(table);
        mythos = new Mythos(table);
        ancientScreen = new AncientScreen(table);
    }

    public Pane build() {
        encounters.buildContactsDeck();
        mythos.build();
        ancientScreen.buildScene();
        StackPane stackPane = new StackPane();

        VBox screen = new VBox();
        AnchorPane field = new AnchorPane();

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(50, 50, 50, 50));
        buttons.setSpacing(10);

        Button encounterButton = new Button("Encounters");
        encounterButton.setBackground(null);
        encounterButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(encounters.screen);
        });
        Button mythosButton = new Button("Mythos");
        mythosButton.setBackground(null);
        mythosButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(mythos.screen);
        });
        Button ancientButton = new Button("Ancient");
        ancientButton.setBackground(null);
        ancientButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(ancientScreen.screen);
        });


        buttons.getChildren().addAll(encounterButton, mythosButton, ancientButton);
        screen.getChildren().addAll(buttons, field);

        ImageView backGround = new ImageView(
                 new Image(new ByteArrayInputStream(table.getTableSurface()))
        );
        stackPane.getChildren().addAll(backGround, screen);
        return stackPane;
    }
}
