import CodingImplementation.src.database.DatabaseConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Instructor extends Record implements User{
    private String name;
    private String specialization;
    private String cities;
    private String phoneNumber;
    // private boolean availability; not sure how to use that
    private Lesson[] assignedLessons;
    private int ID;  // Unique identifier for the administrator
    private static int IDincrement = 1;
    

    public Instructor(String name, String specialization, String cities, String phoneNumber,  Lesson[] assignedLessons) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
        this.assignedLessons = assignedLessons;
        this.ID = IDincrement;
        IDincrement++;
    }

    public Instructor(String name, String specialization, String cities, String phoneNumber, boolean availability) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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


    public void createInstructorAccount(Instructor instructor, String password) {
        // Assign instance variables
        this.ID = instructor.getID();
        this.name = instructor.getName();
        this.specialization = instructor.getSpecialization();
        this.phoneNumber = instructor.getPhone();
        this.cities = instructor.getCities();

        // Insert into database
        String insertQuery = "INSERT INTO Instructor (instructorId, name, specialization, phoneNumber, cities, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, this.ID);
            statement.setString(2, this.name);
            statement.setString(3, this.specialization);
            statement.setString(4, this.phoneNumber);
            statement.setString(5, this.cities);
            statement.setString(6, password);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Instructor registered with the following details:");
                System.out.println("Name: " + this.name);
                System.out.println("Specialization: " + this.specialization);
                System.out.println("Phone Number: " + this.phoneNumber);
                System.out.print("Available Cities: " + this.cities);
            } else {
                System.out.println("Failed to register the instructor.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding instructor to the database: " + e.getMessage());
        }
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

    public void instructorPortal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Instructor Portal ---");
            System.out.println("1. View all lessons");
            System.out.println("2. Sign out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAllLessons();
                    break;
                case 2:
                    System.out.println("Signing out...");
                    return; // Exit the portal
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void viewAllLessons() {
        String query = "SELECT * FROM Lesson";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n--- All Lessons ---");
            boolean lessonsFound = false;

            while (resultSet.next()) {
                int lessonId = resultSet.getInt("lessonId");
                String type = resultSet.getString("type");
                int scheduleId = resultSet.getInt("scheduleId");
                int spaceId = resultSet.getInt("spaceId");
                String privacy = resultSet.getString("privacy");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                int capacity = resultSet.getInt("capacity");
                int numberRegistered = resultSet.getInt("numberRegistered");

                System.out.println("Lesson ID: " + lessonId);
                System.out.println("Type: " + type);
                System.out.println("Schedule ID: " + scheduleId);
                System.out.println("Space ID: " + spaceId);
                System.out.println("Privacy: " + privacy);
                System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
                System.out.println("Capacity: " + capacity);
                System.out.println("Number Registered: " + numberRegistered);
                System.out.println("----------------------------");

                lessonsFound = true;
            }

            if (!lessonsFound) {
                System.out.println("No lessons available at the moment.");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching lessons: " + e.getMessage());
        }
    }
}
