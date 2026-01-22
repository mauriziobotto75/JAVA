package it.botto.rubrica.db;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/rubrica";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}