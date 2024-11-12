package CodingImplementation.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import CodingImplementation.src.database.DatabaseConnection;

public class Administrator extends Record {
    private int ID;  // Unique identifier for the administrator

    public Administrator(int adminId) {
          // Call to parent class constructor if necessary
        this.ID = adminId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean deleteAccount(User user){
        if(user instanceof Client){
            Client client = (Client) user;
            client.delete();
            return true;
        }
        else if(user instanceof Instructor){
            Instructor instructor = (Instructor) user;
            instructor.delete();
            return true;
        }
        return false;
    }
    //verify adminID
    public boolean verifyAdminId(Connection connection, int adminId) {
        String query = "SELECT COUNT(*) FROM Administrator WHERE adminId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // returns true if adminId exists
            }
        } catch (SQLException e) {
            System.err.println("Error verifying admin ID: " + e.getMessage());
        }
        return false; // return false if adminId doesn't exist
    }

     // Admin panel method to display the menu
    public String adminPanel() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        
        while(!choice.equals("6")){
        // Display the menu options
        String menu = 
        "Admin Panel\n" +
        "1. Create a Lesson\n" +
        "2. Cancel a Lesson\n" +
        "3. View Space Table\n" +
        "4. View Schedule Table\n" +
        "5. View Location Table\n" +
        "6. Log out\n" +
        "Please select an option (1-6): ";
        
        System.out.print(menu);
        choice = scanner.nextLine();  // Get user choice
        
        switch (choice) {
            case "1":
                createLesson();
                break;
            case "2":
                // Call cancelLesson() method in Lesson class
                //cancelLesson();
                break;
            case "3":
                displaySpaceTable();
                break;
            case "4":
                displayScheduleTable();
                break;
            case "5":
                displayLocationTable();
                break;
            case "6":
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
        scanner.close();
        return "Admin LoggedOut"; 
    }
     // Method to call createLesson() from Lesson class
     private void createLesson() {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the admin to enter lesson details
        System.out.print("Enter lesson type: ");
        String type = scanner.nextLine();
        
        System.out.print("Enter schedule ID: ");
        int scheduleId = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter location ID: ");
        int locationId = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter privacy (Private/Public): ");
        String privacy = scanner.nextLine().toLowerCase();
        
        // Check that privacy is either "private" or "group"
        while (!privacy.equals("private") && !privacy.equals("public")) {
            System.out.print("Invalid input. Please enter 'Private' or 'Public' for privacy: ");
            privacy = scanner.nextLine().toLowerCase();
        }
        
        System.out.print("Enter space ID: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the capacity.");
        int capacity = Integer.parseInt(scanner.nextLine());
        
        // Create a new Lesson object with the entered details
        Lesson lesson = new Lesson(type, scheduleId, locationId, privacy, spaceId, capacity);
        
        // Add lesson to dB
        lesson.createLesson(); 
        
        System.out.println("Lesson created successfully!");
    }
    // Method to display the Space table
    private void displaySpaceTable() {
        String query = "SELECT * FROM Space";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Space Table:");
            while (resultSet.next()) {
                int spaceId = resultSet.getInt("spaceId");
                String city = resultSet.getString("city");
                int scheduleId = resultSet.getInt("scheduleId");
                System.out.printf("Space ID: %d, City: %s, Schedule ID: %d%n", spaceId, city, scheduleId);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying Space table: " + e.getMessage());
        }
    }
    // Method to display the Schedule table
    private void displayScheduleTable() {
        String query = "SELECT * FROM Schedule";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Schedule Table:");
            while (resultSet.next()) {
                int scheduleId = resultSet.getInt("scheduleId");
                String date = resultSet.getString("date");
                int locationId = resultSet.getInt("locationId");
                System.out.printf("Schedule ID: %d, Date: %s, Location ID: %d%n", scheduleId, date, locationId);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying Schedule table: " + e.getMessage());
        }
    }
    // Method to display the Location table
    private void displayLocationTable() {
        String query = "SELECT * FROM Location";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Location Table:");
            while (resultSet.next()) {
                int locationId = resultSet.getInt("locationId");
                String locationName = resultSet.getString("locationName");
                System.out.printf("Location ID: %d, Location Name: %s%n", locationId, locationName);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying Location table: " + e.getMessage());
        }
    }
}
