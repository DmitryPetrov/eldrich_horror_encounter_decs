package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import main.java.horror.Table;
import main.java.horror.ancient.AncientOne;
import main.java.horror.myth.MythosDeck;

import java.io.ByteArrayInputStream;

public class AncientScreen {

    Table table;
    AncientOne ancientOne;

    public AncientScreen(Table table) {
        this.table = table;
        this.ancientOne = table.getAncient();
    }

    public Pane buildScene() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(buildAncientCard(), buildMysteryRow());
        return vbox;
    }

    public Pane buildMysteryRow() {
        VBox mysteryRow = new VBox();

        Image cardShirt = new Image(new ByteArrayInputStream(ancientOne.getMysteryDeck().getShirt().content));

        ImageView cardFrontPlace = new ImageView(cardShirt);
        cardFrontPlace.setFitWidth(534);
        cardFrontPlace.setFitHeight(342);

        ImageView cardShirtPlace = new ImageView(cardShirt);
        cardShirtPlace.setFitWidth(126);
        cardShirtPlace.setFitHeight(82);
        Button mysteryButton = new Button("", cardShirtPlace);

        mysteryButton.setOnAction(
                e -> {
                    cardFrontPlace.setImage(new Image(new ByteArrayInputStream(ancientOne.getMysteryDeck().get().content)));
                    cardFrontPlace.setFitWidth(534);
                    cardFrontPlace.setFitHeight(342);
                }
        );

        mysteryRow.getChildren().addAll(mysteryButton, cardFrontPlace);

        return mysteryRow;
    }

    public Button buildAncientCard() {
        Image ancientFace = new Image(new ByteArrayInputStream(ancientOne.getFace()));
        ImageView ancientView = new ImageView(ancientFace);
        ancientView.setFitWidth(500);
        ancientView.setFitHeight(650);
        Button button = new Button("", ancientView);
        button.setOnAction(e -> {
            ancientView.setImage(new Image(new ByteArrayInputStream(ancientOne.getBack())));
            ancientView.setFitWidth(500);
            ancientView.setFitHeight(650);
        });
        return button;
    }

}
