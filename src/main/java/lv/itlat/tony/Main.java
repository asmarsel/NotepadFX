package lv.itlat.tony;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    public void start(Stage stage) throws Exception {

        Main.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Notepad");
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}