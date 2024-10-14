package com.home.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsert {
    // MySQL database connection URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";  // Replace with your DB URL
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASS = "Devi@123";  // Replace with your MySQL password

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish the database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // SQL INSERT query to insert JSON data
            String sql = "INSERT INTO simply_table (json_data) VALUES (?)";

            // Prepare the statement
            pstmt = conn.prepareStatement(sql);

            // Example JSON data
            String jsonData = "{ \"name\": \"John Doe\", \"age\": 30, \"address\": { \"city\": \"New York\", \"zipcode\": \"10001\" }}";

            // Set the JSON data as a string
            pstmt.setString(1,jsonData);

            // Execute the insert statement
            int rowsInserted = pstmt.executeUpdate();

            // Check if insert was successful
            if (rowsInserted > 0) {
                System.out.println("A new record was inserted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

