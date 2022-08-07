package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.horror.Table;

import java.io.ByteArrayInputStream;
import java.util.List;

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
        StackPane stackPane = new StackPane();

        VBox screen = new VBox();
        AnchorPane field = new AnchorPane();

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(50, 50, 50, 50));
        buttons.setSpacing(100);

        Button encounterButton = new Button("Encounters");
        encounterButton.setBackground(null);
        encounterButton.setFont(Font.font("Courier new bold", 40));
        encounterButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#708090"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(2)
        )));
        encounterButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(encounters.screen);
        });

        Button mythosButton = new Button("Mythos");
        mythosButton.setBackground(null);
        mythosButton.setFont(Font.font("Courier new bold", 40));
        mythosButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#708090"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(2)
        )));
        mythosButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(mythos.screen);
        });

        Button ancientButton = new Button("The Ancient One");
        ancientButton.setBackground(null);
        ancientButton.setFont(Font.font("Courier new bold", 40));
        ancientButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#708090"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(2)
        )));
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
