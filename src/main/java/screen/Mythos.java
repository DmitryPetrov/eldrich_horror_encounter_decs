package main.java.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.horror.Card;
import main.java.horror.Table;
import main.java.horror.myth.MythosCard;
import main.java.horror.myth.MythosDeck;

public class Mythos {

    private Table table;
    private MythosDeck mythosDeck;
    private Pane processField;
    private Button newMythosButton;
    private CardPlace newMythosCard;
    private Pane newMythosScreen;

    Pane screen;

    public Mythos(Table table) {
        this.table = table;
        this.mythosDeck = table.getMythosDeck();
    }

    public void build() {
        newMythosCard = new CardPlace(mythosDeck.getShirt());
        buildMythButton(newMythosCard);
        buildProcessField();
        buildNewMythosScreen();

        HBox box = new HBox();
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(50);


        box.getChildren().addAll(newMythosScreen, processField);
        screen = box;
    }

    public void buildNewMythosScreen() {
        VBox box = new VBox();
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(50);
        box.setAlignment(Pos.TOP_CENTER);

        buildProcessField();
        box.getChildren().addAll(newMythosButton, newMythosCard);
        newMythosScreen = box;
    }

    private void buildMythButton(CardPlace cardPlace) {
        newMythosButton = new CardButton(mythosDeck.getShirt());
        newMythosButton.setOnAction(e -> {
            MythosCard mythos = table.getMythos();
            cardPlace.updateCard(mythos);
            if (!mythos.event) {
                addProcessCard(mythos);
            }
        });
    }

    private void buildProcessField() {
        HBox box = new HBox();
        processField = box;
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(50);
        mythosDeck.showInGameCards().forEach( this::addProcessCard);
    }

    private void addProcessCard(Card card) {
        VBox process = new VBox();
        process.setPadding(new Insets(10, 10, 10, 10));
        process.setSpacing(50);

        CardPlace processCard = new CardPlace(card);
        Button discardButton = new Button("Discard");
        process.getChildren().addAll(processCard, discardButton);

        discardButton.setOnAction(actionEvent -> {
            int index = mythosDeck.discardCard(card.id);
            processField.getChildren().remove(index);
        });


        processField.getChildren().add(process);
    }

}
