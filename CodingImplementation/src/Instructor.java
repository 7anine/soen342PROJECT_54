
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Instructor extends Record implements User{
    private String name;
    private String specialization;
    private String cities;
    private String phoneNumber;
    // private boolean availability; not sure how to use that
    private Lesson[] assignedLessons;
    

    public Instructor(String name, String specialization, String cities, String phoneNumber,  Lesson[] assignedLessons) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
        this.assignedLessons = assignedLessons;
    }

    public Instructor(String name, String specialization, String cities, String phoneNumber, boolean availability) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

   

    public Lesson[] getAssignedLessons() {
        return assignedLessons;
    }

    public void setAssignedLessons(Lesson[] assignedLessons) {
        this.assignedLessons = assignedLessons;
    }
    public void delete(){}

    
    public void CreateinstructorAccount(Instructor instructor) {
        this.name = instructor.getName();
        this.specialization = instructor.getSpecialization();
        this.phoneNumber = instructor.getPhone();
        this.cities = instructor.getCities();

        // TODO: Add to db here

        System.out.println("Instructor registered with the following details:");
        System.out.println("Name: " + this.name);
        System.out.println("Specialization: " + this.specialization);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.print("Available Cities: " + cities);

        System.out.println();
    }

    public void selectOffering(Offering offering ){
        //modify the offering in the database to make it availabel to public
    }

    public static boolean instructorSignIn(int instructorID, int instructorPassword) {
        String selectQuery = "SELECT password FROM Instructor WHERE instructorId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            statement.setInt(1, instructorID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    // Check if the password matches
                    if (storedPassword.equals(Integer.toString(instructorPassword))) {
                        System.out.println("Sign-in successful.");
                        return true;
                    } else {
                        System.out.println("Incorrect password.");
                    }
                } else {
                    System.out.println("Instructor ID not found.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error during instructor sign-in: " + e.getMessage());
        }

        return false;
    }
}
