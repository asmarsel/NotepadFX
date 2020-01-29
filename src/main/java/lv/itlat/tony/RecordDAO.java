package lv.itlat.tony;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RecordDAO {

    public static String CONN_URL = "jdbc:h2:~/test1;AUTO_SERVER=TRUE";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN_URL);
    }

    public static void initDB() throws SQLException {
        try (var conn = getConnection();
             var stmt = conn.createStatement()) {

        }
    }
}
