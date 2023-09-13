package com.example.assignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.JDBC;

    /**
     * The databaseConnection class provides a way to establish a connection to the studentDatabase database.
     */
public class databaseConnection {
    /**
     * The URL of the SQLite database to connect to.
     */
    private static final String DB_URL = "jdbc:sqlite:src/main/java/com/example/assignment/studentDatabase.db";

    /**
     * Returns a connection to the studentDatabase database.
     * @return A Connection object representing the database connection.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the SQLite JDBC driver is not found.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(DB_URL);
    }
}