package lv.itlat.tony;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class MainForm extends BorderPane {

    public TableView<Record> recordTable;
    public TextField nameSearchText;
    public TextField emailSearchText;
    public TextField phoneSearchText;
    public Button editButton;
    public Button deleteButton;

    public MainForm() throws IOException {
        var loader = new FXMLLoader(
                getClass().getResource("main.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void initialize() throws SQLException {

        var records = RecordDAO.getAllRecords();
        recordTable.getItems().setAll(records);

        editButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                    return recordTable.getSelectionModel().getSelectedCells().size() == 0;
                },
                recordTable.getSelectionModel().getSelectedItems()));
        deleteButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                    return recordTable.getSelectionModel().getSelectedCells().size() == 0;
                },
                recordTable.getSelectionModel().getSelectedItems()));

    }

    public void addRecord() throws SQLException {
        var dataEntry = new DataEntryForm(this);
        var data = dataEntry.showAndGet(null);
        if (data != null) {
            recordTable.getItems().add(data);
            RecordDAO.insertRecord(data);
        }
    }

    public void editRecord() throws SQLException {
        var selected = recordTable.getSelectionModel().getSelectedItem();
        var dataEntry = new DataEntryForm(this);
        if (dataEntry.showAndGet(selected) != null) {
            RecordDAO.updateRecord(selected);
        }
    }

    public void doSearch() throws SQLException {
        var name = nameSearchText.getText();
        var email = emailSearchText.getText();
        var phone = phoneSearchText.getText();
        var records = RecordDAO.findRecords(name, email, phone);
        recordTable.getItems().setAll(records);
    }

    public void deleteRecord() throws SQLException {
        var selected = recordTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Deleting " + selected.getName() + "?");
        alert.setContentText("Are You Sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            recordTable.getItems().remove(selected);
        RecordDAO.deleteRecord(selected);
        }
    }
}