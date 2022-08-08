package main.java.screen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.horror.Card;

import java.io.ByteArrayInputStream;

public class CardButtonImage extends ImageView {

    public CardButtonImage(Card shirt) {
        super(new Image(new ByteArrayInputStream(shirt.content)));
        this.setFitWidth(114);
        this.setFitHeight(178);
    }
}
