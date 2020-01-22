package lv.itlat.tony;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) throws Exception {
        var root = new MainForm();
        stage.setTitle("Notepad");
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}