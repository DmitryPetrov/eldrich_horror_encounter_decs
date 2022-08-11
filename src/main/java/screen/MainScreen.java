package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.horror.Table;
import main.java.horror.ancient.AncientName;

import java.io.ByteArrayInputStream;

public class MainScreen {

    private final Table table;
    private final Stage primaryStage;

    Encounters encounters;
    Mythos mythos;
    AncientScreen ancientScreen;
    Scene ancientSelector;
    public Pane screen;

    public MainScreen(Stage primaryStage, Table table) {
        this.primaryStage = primaryStage;
        this.table = table;
        this.screen = new Pane();
    }

    public void init(AncientName ancientName, boolean easyMod) {
        table.buildGame(ancientName, easyMod);
        encounters = new Encounters(table);
        mythos = new Mythos(table);
        ancientScreen = new AncientScreen(table);
        buildMainScreen();
    }

    public Pane buildMainScreen() {
        StackPane stackPane = new StackPane();

        VBox screen = new VBox();
        AnchorPane field = new AnchorPane();

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(30, 50, 30, 50));
        buttons.setSpacing(100);

        Button encounterButton = new Button("Encounters");
        encounterButton.setBackground(null);
        encounterButton.setFont(Font.font("Courier new bold", 40));
        encounterButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(2)
        )));
        encounterButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(encounters.screen);
        });

        Button mythosButton = new Button("Mythos");
        mythosButton.setBackground(null);
        mythosButton.setFont(Font.font("Courier new bold", 40));
        mythosButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(2)
        )));
        mythosButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(mythos.screen);
        });

        Button ancientButton = new Button("The Ancient One");
        ancientButton.setBackground(null);
        ancientButton.setFont(Font.font("Courier new bold", 40));
        ancientButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(2)
        )));
        ancientButton.setOnAction(actionEvent -> {
            field.getChildren().clear();
            field.getChildren().addAll(ancientScreen.screen);
        });


        Button ancientSelectorButton = new Button("Select new ancient");
        ancientSelectorButton.setAlignment(Pos.BASELINE_RIGHT);
        ancientSelectorButton.setBackground(null);
        ancientSelectorButton.setFont(Font.font("Courier new bold", 20));
        ancientSelectorButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(6), new BorderWidths(1)
        )));
        ancientSelectorButton.setOnAction(actionEvent -> primaryStage.setScene(ancientSelector));

        buttons.getChildren().addAll(encounterButton, mythosButton, ancientButton, ancientSelectorButton);
        screen.getChildren().addAll(buttons, field);

        ImageView backGround = new ImageView(
                 new Image(new ByteArrayInputStream(table.getTableSurface()))
        );
        stackPane.getChildren().addAll(backGround, screen);
        this.screen = stackPane;
        return stackPane;
    }

    public void addAncientSelector(Scene selector) {
        this.ancientSelector = selector;
    }
}
