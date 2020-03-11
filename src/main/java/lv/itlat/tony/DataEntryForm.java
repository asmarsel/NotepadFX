package lv.itlat.tony;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class DataEntryForm extends BorderPane {
    private final Stage stage = new Stage();
    public TextField nameField;
    public TextField emailField;
    public TextField phoneField;
    public Label idLabel;
    public Button okButton;
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

    public void initialize() {
        okButton.disableProperty().bind(Bindings.createBooleanBinding(() -> nameField.getText().strip().isBlank(), nameField.textProperty()));
    }

    public Record showAndGet(Record existingRecord) {

        if (existingRecord != null) {
            idLabel.setText(existingRecord.getId().toString());
            nameField.setText(existingRecord.getName());
            emailField.setText(existingRecord.getEmail());
            phoneField.setText(existingRecord.getPhone());
            stage.setTitle("Edit Record");
        } else {
            idLabel.setText(UUID.randomUUID().toString());
        }

        stage.showAndWait();
        if (isOk) {
            var rec = existingRecord == null ? new Record() : existingRecord;
            rec.setName(nameField.getText());
            rec.setEmail(emailField.getText());
            rec.setPhone(phoneField.getText());
            rec.setId(UUID.fromString(idLabel.getText()));
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