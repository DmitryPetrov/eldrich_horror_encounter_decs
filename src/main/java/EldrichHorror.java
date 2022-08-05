package main.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.java.dealer.FileSystemSourceLoader;
import main.java.horror.GameBase;
import main.java.horror.Table;
import main.java.horror.ancient.AncientName;
import main.java.screen.AncientScreen;
import main.java.screen.Encounters;
import main.java.screen.Mythos;

import java.io.IOException;
import java.util.List;

public class EldrichHorror extends Application {

    Table table;
    Encounters encounters;
    Mythos mythos;
    AncientScreen ancientScreen;

    public EldrichHorror() {
        FileSystemSourceLoader sourceLoader = new FileSystemSourceLoader(List.of(GameBase.ORIGIN, GameBase.FORSAKEN_LORE));
        table = new Table(AncientName.AZATHOTH, sourceLoader, false);
        encounters = new Encounters(table);
        mythos = new Mythos(table);
        ancientScreen = new AncientScreen(table);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox lines = new HBox();
        lines.setPadding(new Insets(10, 10, 10, 10));
        lines.setSpacing(10);
        lines.getChildren().addAll(
                encounters.buildContactsDeck(),
                mythos.buildMythDeck(),
                ancientScreen.buildScene()
        );

        primaryStage.setTitle("Древний Ужас");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setScene(new Scene(lines));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        new EldrichHorror();
    }



}
