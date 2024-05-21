package restaurant.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private Connection conn;
    private String jdbcUrl = "jdbc:mysql://localhost:3306/db_restaurant_management";
    private String username = "root";
    private String password = "";

    public database() {

        try {
            this.conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
