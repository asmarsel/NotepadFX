package lv.itlat.tony;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {

    public static void main(String[] args) throws SQLException {
        RecordDAO.initDB();
    }
}