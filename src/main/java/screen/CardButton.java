package main.java.screen;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.horror.Card;

import java.io.ByteArrayInputStream;

public class CardButton extends Button {

    public CardButton(Card shirt) {
        super("", new CardButtonImage(shirt));
    }



}
