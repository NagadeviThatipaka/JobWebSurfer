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
            String sql = "INSERT INTO job_detail (id, date_of_posting, company, title, location, title_location, basic_details, qualifications, full_job_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "date_of_posting = VALUES(date_of_posting), " +
                    "company = VALUES(company), " +
                    "title = VALUES(title), " +
                    "location = VALUES(location), " +
                    "title_location = VALUES(title_location), " +
                    "basic_details = VALUES(basic_details), " +
                    "qualifications = VALUES(qualifications), " +
                    "full_job_description = VALUES(full_job_description)";


            for(JobDetail jobDetail : listOfJobs) {

                try {
                    // Prepare the statement
                    pstmt = conn.prepareStatement(sql);

                    String titleJsonData = "{ \"title_location\": \"" + jobDetail.getTitleLocation().replaceAll("[\r\n]+", " ") + "\"}";
                    String basicDetailsJsonData = "{ \"basic_details\": \"" + jobDetail.getBasicDetails().replaceAll("[\r\n]+", " ") + "\"}";
                    String qualificationsJsonData = "{ \"qualifications\": \"" + jobDetail.getQualifications().replaceAll("[\r\n]+", " ") + "\"}";
                    String fullJobDescJsonData = "{ \"full_job_description\": \"" + jobDetail.getFullJobDescription().replaceAll("[\r\n]+", " ") + "\"}";

                    /*System.out.println("title_location: " + titleJsonData);
                    System.out.println("basic_details: " + basicDetailsJsonData);
                    System.out.println("qualifications: " + qualificationsJsonData);
                    System.out.println("full_job_description: " + fullJobDescJsonData);*/


                    pstmt.setString(1, jobDetail.getKey());
                    pstmt.setString(2, jobDetail.getTime());
                    pstmt.setString(3, jobDetail.getCompany());
                    pstmt.setString(4, jobDetail.getJobTitle());
                    pstmt.setString(5, jobDetail.getJobLocation());
                    // Set the JSON data as a string
                    pstmt.setString(6, titleJsonData);
                    pstmt.setString(7, basicDetailsJsonData);
                    pstmt.setString(8, qualificationsJsonData);
                    pstmt.setString(9, fullJobDescJsonData);


                    // Execute the insert statement
                    int rowsInserted = pstmt.executeUpdate();

                    // Check if insert was successful
                    if (rowsInserted > 0) {
                        System.out.println("A new record was inserted successfully!");
                    }

                    System.out.println("Upserted one record: " + jobDetail.getKey());

                }catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error inserting: " + ex.getMessage());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in DataLayer: " + e.getMessage());
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
