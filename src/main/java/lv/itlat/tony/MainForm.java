package lv.itlat.tony;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class MainForm extends BorderPane {

    public TableView<Record> recordTable;

    public MainForm() throws IOException {
        var loader = new FXMLLoader(
                getClass().getResource("main.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void initialize() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test1;AUTO_SERVER=TRUE");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from records");
        while (rs.next()) {
            var id = (UUID) rs.getObject("ID");
            var name = rs.getString("NAME");
            var email = rs.getString("EMAIL");
            var phone = rs.getString("PHONE");

            var rec = new Record();
            rec.setId(id);
            rec.setName(name);
            rec.setEmail(email);
            rec.setPhone(phone);

            recordTable.getItems().add(rec);

        }

        conn.close();
    }

    public void addRecord() {
        var dataEntry = new DataEntryForm(this);
        var data = dataEntry.showAndGet(null);
        if (data != null) {
            recordTable.getItems().add(data);
        }
    }

    public void editRecord() {
        var selected = recordTable.getSelectionModel().getSelectedItem();

        var dataEntry = new DataEntryForm(this);
        dataEntry.showAndGet(selected);
    }
}