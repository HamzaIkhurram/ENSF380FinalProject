package com.example.demo.config;

import java.sql.*;


/**
 * This class is responsible for creating the database and tables.
 */
public class DatabaseHelper {

    /**
     * The URL of the database. It is a SQLite database.
     */
    private static final String DATABASE_URL = "jdbc:sqlite:cityhall_ads.db";

    /**
     * Connect to the database.
     * @return the Connection object
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Create new tables in the database.
     * The tables are advertisements and media.
     * advertisements table has columns id, title, start_date, end_date, and created_at.
     * media table has columns id, advertisement_id, file_path, and file_type.
     */
    public static void createNewTables() {
        String advertisementsTable = "CREATE TABLE IF NOT EXISTS advertisements (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " start_date TEXT NOT NULL,\n"
                + " end_date TEXT NOT NULL,\n"
                + " created_at TEXT NOT NULL\n"
                + ");";

        String mediaTable = "CREATE TABLE IF NOT EXISTS media (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " advertisement_id INTEGER NOT NULL,\n"
                + " file_path TEXT NOT NULL,\n"
                + " file_type TEXT NOT NULL,\n"
                + " FOREIGN KEY (advertisement_id) REFERENCES advertisements (id)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(advertisementsTable);
            stmt.execute(mediaTable);
            System.out.println("Tables have been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}