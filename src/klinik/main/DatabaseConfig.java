package klinik.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
     // Sesuaikan kredensial PostgreSQL Anda di sini
    private static final String URL = "jdbc:postgresql://localhost:5432/db_klinik";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
