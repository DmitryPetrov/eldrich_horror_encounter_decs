package main.java.screen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.horror.Card;

import java.io.ByteArrayInputStream;

public class CardPlace extends ImageView {

    public CardPlace(Card card) {
        super();
        this.setImage(new Image(new ByteArrayInputStream(card.content)));
        this.setFitWidth(342);
        this.setFitHeight(534);
    }

    public CardPlace(Card card, boolean big) {
        super();
        this.setImage(new Image(new ByteArrayInputStream(card.content)));
        if (big) {
            this.setFitWidth(456);
            this.setFitHeight(712);
        } else {
            this.setFitWidth(342);
            this.setFitHeight(534);
        }
    }

    public void updateCard(Card card) {
        double w = this.getFitWidth();
        double h = this.getFitHeight();
        this.setImage(new Image(new ByteArrayInputStream(card.content)));
        this.setFitWidth(w);
        this.setFitHeight(h);
    }
}
