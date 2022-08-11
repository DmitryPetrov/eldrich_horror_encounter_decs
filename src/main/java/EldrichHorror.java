package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.dealer.FileSystemSourceLoader;
import main.java.horror.GameBase;
import main.java.horror.Table;
import main.java.horror.ancient.AncientName;
import main.java.screen.AncientSelector;
import main.java.screen.MainScreen;

import java.io.IOException;
import java.util.List;

public class EldrichHorror extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Table table = new Table(new FileSystemSourceLoader(List.of(GameBase.ORIGIN, GameBase.FORSAKEN_LORE)));
        MainScreen mainScreen = new MainScreen(primaryStage, table);

        AncientSelector ancientSelector = new AncientSelector(primaryStage, mainScreen);
        ancientSelector.build();

        Scene selectorScene = new Scene(ancientSelector.screen);
        mainScreen.addAncientSelector(selectorScene);

        primaryStage.setTitle("Древний Ужас");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setScene(selectorScene);
        primaryStage.show();
    }

}
