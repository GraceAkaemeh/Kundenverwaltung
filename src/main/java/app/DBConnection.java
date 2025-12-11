package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/kundenverwaltung?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "grace2024!"; // your MariaDB root password


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // ensures driver loaded
        } catch (ClassNotFoundException e) {
            throw new SQLException("MariaDB JDBC driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
