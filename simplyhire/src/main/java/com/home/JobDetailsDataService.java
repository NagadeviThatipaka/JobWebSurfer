package com.home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JobDetailsDataService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";  // Replace with your DB URL
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASS = "Devi@123";  // Replace with your MySQL password

    public void AddJobDetails(List<JobDetail> listOfJobs) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish the database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // SQL INSERT query to insert JSON data
            String sql = "INSERT INTO job_detail (title_location, basic_details, qualifications, full_job_description) VALUES (?, ?, ?, ?)";

            for(JobDetail jobDetail : listOfJobs) {

                try {
                    // Prepare the statement
                    pstmt = conn.prepareStatement(sql);

                    String titleJsonData = "{ \"title_location\": \"" + jobDetail.getTitleLocation().replaceAll("[\r\n]+", " ") + "\"}";
                    String basicDetailsJsonData = "{ \"basic_details\": \"" + jobDetail.getBasicDetails().replaceAll("[\r\n]+", " ") + "\"}";
                    String qualificationsJsonData = "{ \"qualifications\": \"" + jobDetail.getQualifications().replaceAll("[\r\n]+", " ") + "\"}";
                    String fullJobDescJsonData = "{ \"full_job_description\": \"" + jobDetail.getFullJobDescription().replaceAll("[\r\n]+", " ") + "\"}";

                    System.out.println("title_location: " + titleJsonData);
                    System.out.println("basic_details: " + basicDetailsJsonData);
                    System.out.println("qualifications: " + qualificationsJsonData);
                    System.out.println("full_job_description: " + fullJobDescJsonData);


                    // Set the JSON data as a string
                    pstmt.setString(1, titleJsonData);
                    pstmt.setString(2, basicDetailsJsonData);
                    pstmt.setString(3, qualificationsJsonData);
                    pstmt.setString(4, fullJobDescJsonData);


                    // Execute the insert statement
                    int rowsInserted = pstmt.executeUpdate();

                    // Check if insert was successful
                    if (rowsInserted > 0) {
                        System.out.println("A new record was inserted successfully!");
                    }
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
