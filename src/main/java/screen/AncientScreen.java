package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import main.java.horror.Table;
import main.java.horror.ancient.AncientOne;

import java.io.ByteArrayInputStream;

public class AncientScreen {

    private AncientOne ancientOne;
    private final int ANCIENT_PLATE_WIDTH = 500;
    private final int ANCIENT_PLATE_HEIGHT = 650;
    private final int MYSTERY_CARD_WIDTH = 534;
    private final int MYSTERY_CARD_HEIGHT = 342;
    private final int MYSTERY_BUTTON_WIDTH = 126;
    private final int MYSTERY_BUTTON_HEIGHT = 82;

    public final Pane screen;

    public AncientScreen(Table table) {
        this.ancientOne = table.getAncient();
        HBox scene = new HBox();
        HBox tablet = new HBox();
        tablet.setPadding(new Insets(50, 50, 50, 50));
        tablet.setSpacing(50);
        tablet.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1)
        )));
        tablet.getChildren().addAll(buildAncientCard(), buildMysteryDeck());
        scene.getChildren().add(tablet);
        scene.setPadding(new Insets(0, 50, 0, 50));
        screen = scene;
    }

    public Pane buildMysteryDeck() {
        VBox mysteryDeck = new VBox();
        mysteryDeck.setPadding(new Insets(0, 15, 0, 15));
        mysteryDeck.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        Image cardShirt = new Image(new ByteArrayInputStream(ancientOne.getMysteryDeck().getShirt().content));
        ImageView cardFrontPlace = new ImageView(cardShirt);
        cardFrontPlace.setFitWidth(MYSTERY_CARD_WIDTH);
        cardFrontPlace.setFitHeight(MYSTERY_CARD_HEIGHT);
        cardFrontPlace.setSmooth(true);
        ImageView cardShirtPlace = new ImageView(cardShirt);
        cardShirtPlace.setFitWidth(MYSTERY_BUTTON_WIDTH);
        cardShirtPlace.setFitHeight(MYSTERY_BUTTON_HEIGHT);
        Button button = new Button("", cardShirtPlace);
        mysteryDeck.setAlignment(Pos.TOP_CENTER);
        button.setPadding(new Insets(50, 50, 50, 50));
        button.setBackground(null);
        button.setOnAction(
                e -> {
                    cardFrontPlace.setImage(new Image(new ByteArrayInputStream(ancientOne.getMysteryDeck().get().content)));
                    cardFrontPlace.setFitWidth(MYSTERY_CARD_WIDTH);
                    cardFrontPlace.setFitHeight(MYSTERY_CARD_HEIGHT);
                }
        );
        mysteryDeck.getChildren().addAll(button, cardFrontPlace);
        return mysteryDeck;
    }

    public Button buildAncientCard() {
        Image front = new Image(new ByteArrayInputStream(ancientOne.getFace()));
        Image back = new Image(new ByteArrayInputStream(ancientOne.getBack()));
        ImageView ancientPlate = new ImageView(front);
        ancientPlate.setFitWidth(ANCIENT_PLATE_WIDTH);
        ancientPlate.setFitHeight(ANCIENT_PLATE_HEIGHT);
        Button button = new Button("", ancientPlate);
        button.setPadding(new Insets(15, 15, 15, 15));
        button.setBorder(new Border(new BorderStroke(
                Paint.valueOf("#303030"), BorderStrokeStyle.DOTTED, new CornerRadii(12), new BorderWidths(1)
        )));
        button.setBackground(null);
        button.setOnAction(e -> {
            if (ancientPlate.getImage().equals(front)) {
                ancientPlate.setImage(back);
            } else {
                ancientPlate.setImage(front);
            }
            ancientPlate.setFitWidth(ANCIENT_PLATE_WIDTH);
            ancientPlate.setFitHeight(ANCIENT_PLATE_HEIGHT);
        });
        return button;
    }

}
