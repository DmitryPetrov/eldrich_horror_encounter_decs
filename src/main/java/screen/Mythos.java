package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.horror.Table;
import main.java.horror.myth.MythosDeck;

public class Mythos {

    Table table;
    MythosDeck mythosDeck;
    Pane screen;

    public Mythos(Table table) {
        this.table = table;
        this.mythosDeck = table.getMythosDeck();
    }

    public Pane buildMythDeck() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.TOP_CENTER);
        CardPlace cardFront = new CardPlace(mythosDeck.getShirt());
        vbox.getChildren().addAll(buildMythButton(cardFront), cardFront);
        screen = vbox;
        return vbox;
    }

    private Button buildMythButton(CardPlace cardPlace) {
        Button mythosButton = new CardButton(mythosDeck.getShirt());
        mythosButton.setOnAction(e -> cardPlace.updateCard(table.getMythos()));
        return mythosButton;
    }

}
