package lv.itlat.tony;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {

    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test1;AUTO_SERVER=TRUE");
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE records (\n" +
                "    id UUID NOT NULL PRIMARY KEY,\n" +
                "    name VARCHAR(200) NOT NULL,\n" +
                "    email VARCHAR(400),\n" +
                "    phone VARCHAR(50)\n" +
                ")");

        stmt.execute("    INSERT INTO records (id, name, email, phone)\n" +
                "VALUES (\n" +
                "'ae944e9a-412e-11ea-b77f-2e728ce88125',\n" +
                "'John Doe',\n" +
                "'johndoe@gmail.com',\n" +
                "'1234567890'\n" +
                ")");

        conn.close();
    }
}
