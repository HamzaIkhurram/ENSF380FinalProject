package com.example.demo.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatabaseHelperTest {

    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        // Establish connection and create tables before each test
        connection = DatabaseHelper.connect();
        DatabaseHelper.createNewTables();
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testConnect() {
        // Test if the connection is not null
        assertNotNull(connection);
        System.out.println("Database connection test passed.");
    }

    @Test
    public void testCreateNewTables() throws SQLException {
        // Verify if the 'advertisements' table was created
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='advertisements';");
            assertTrue(rs.next());
            System.out.println("Advertisements table creation test passed.");
        }

        // Verify if the 'media' table was created
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='media';");
            assertTrue(rs.next());
            System.out.println("Media table creation test passed.");
        }
    }
}
