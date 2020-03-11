package lv.itlat.tony;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordDAO {

    public static String CONN_URL = "jdbc:h2:~/testitlat;AUTO_SERVER=TRUE";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN_URL);
    }

    public static void initDB() throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.createStatement()) { // try with resources

            stmt.execute("CREATE TABLE records (\n" +
                    "   id UUID NOT NULL PRIMARY KEY,\n" +
                    "   name VARCHAR(200) NOT NULL,\n" +
                    "   email VARCHAR(400),\n" +
                    "   phone VARCHAR(50)\n" +
                    ")");

        }
    }

    public static List<Record> getAllRecords() throws SQLException {
        var result = new ArrayList<Record>();
        try (var conn = getConnection();
             var stmt = conn.createStatement()) { // try with resources

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

                result.add(rec);
            }
        }
        return result;
    }

    public static List<Record> findRecords(String fname, String femail, String fphone) throws SQLException {
        var result = new ArrayList<Record>();
        try (var conn = getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM records WHERE UPPER(name) LIKE ? AND UPPER(email) LIKE ? AND UPPER(phone) LIKE ?")) {
            stmt.setString(1, "%" + fname.toUpperCase() + "%");
            stmt.setString(2, "%" + femail.toUpperCase() + "%");
            stmt.setString(3, "%" + fphone.toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
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

                result.add(rec);
            }
        }
        return result;
    }

    public static void insertRecord(Record record) throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.prepareStatement("INSERT INTO records " +
                     "(id, name, email, phone) VALUES (?, ?, ?, ?)")) {

            stmt.setObject(1, record.getId());
            stmt.setString(2, record.getName());
            stmt.setString(3, record.getEmail());
            stmt.setString(4, record.getPhone());

            stmt.executeUpdate();
        }
    }

    public static void updateRecord(Record record) throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.prepareStatement("UPDATE records " +
                     "SET name = ?, email = ?, phone = ? WHERE id = ?")) {

            stmt.setString(1, record.getName());
            stmt.setString(2, record.getEmail());
            stmt.setString(3, record.getPhone());
            stmt.setObject(4, record.getId());

            stmt.executeUpdate();
        }
    }

    public static void deleteRecord(Record record) throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.prepareStatement("DELETE FROM records WHERE id = ?")) {

            stmt.setObject(1, record.getId());

            stmt.executeUpdate();
        }
    }
}