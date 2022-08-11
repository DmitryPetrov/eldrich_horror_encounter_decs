package main.java.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.horror.ancient.AncientName;
import main.java.horror.GameBase;

public class AncientSelector {

    public final Pane screen;

    private boolean easyMod = false;
    private final MainScreen mainScreen;
    private final Stage primaryStage;

    public AncientSelector(Stage primaryStage, MainScreen mainScreen) {
        this.primaryStage = primaryStage;
        this.mainScreen = mainScreen;
        screen = new HBox();
    }

    public Pane build() {
        VBox buttons = new VBox();
        buttons.setPadding(new Insets(50, 50, 50, 50));
        buttons.setSpacing(100);

        for (AncientName ancientName: AncientName.values()) {
            Button button = new Button(ancientName.name());
            button.setBackground(null);
            button.setFont(Font.font("Courier new bold", 40));
            button.setBorder(new Border(new BorderStroke(
                    Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(2)
            )));
            button.setOnAction(actionEvent -> {
                mainScreen.init(ancientName, easyMod);
                primaryStage.setScene(new Scene(mainScreen.screen));
            });
            buttons.getChildren().add(button);
        }

        ToggleButton toggleButton = new ToggleButton("Easy mod");
        toggleButton.setFont(Font.font("Courier new bold", 40));
        toggleButton.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DASHED, new CornerRadii(12), new BorderWidths(2)
        )));
        toggleButton.setOnAction(event -> {
                setEasyMod(toggleButton.isSelected());
        });

        screen.getChildren().addAll(buttons, toggleButton);
        return screen;
    }

    private void setEasyMod(boolean easyMod) {
        this.easyMod = easyMod;
    }
}
