package lv.itlat.tony;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DataEntryForm extends BorderPane {
    private final Stage stage = new Stage();
    public TextField nameField;
    public TextField emailField;
    public TextField phoneField;
    private boolean isOk = false;

    public DataEntryForm(Pane parent) {

        try {

            var loader = new FXMLLoader(
                    getClass().getResource("data-entry.fxml"));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();

            stage.initOwner(parent.getScene().getWindow());
            stage.initModality(Modality.NONE);

            stage.setTitle("Add record");
            var scene = new Scene(this);
            stage.setScene(scene);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    public Record showAndGet() {
        nameField.setText("John");

        stage.showAndWait();
        if (isOk) {
            var rec = new Record();
            rec.setName(nameField.getText());
            rec.setEmail(emailField.getText());
            rec.setPhone(phoneField.getText());
            return rec;
        } else {
            return null;
        }
    }

    public void okPressed() {
        isOk = true;
        stage.close();
    }

    public void cancelPressed() {
        stage.close();
    }
}