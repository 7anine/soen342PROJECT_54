
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import CodingImplementation.src.database.DatabaseConnection;

public class Administrator extends Record {
    private int ID;  // Unique identifier for the administrator
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock staticLock = new ReentrantReadWriteLock();

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
    public void adminPanel() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do{
        // Display the menu options
        String menu = 
        "Admin Panel\n" +
        "1. Create a Lesson\n" +
        "2. Manage Lessons\n" +
        "3. View Space Table\n" +
        "4. View Schedule Table\n" +
        "5. View Location Table\n"+
        "6. Manage Accounts\n" +
        "7. Manage Bookings\n"+
        "8. Log out\n" +
        "Please select an option (1-8): ";
        
        System.out.print(menu);
        choice = scanner.nextInt();  // Get user choice
       
        
        switch (choice) {
            case 1:
                createLesson();
                break;
            case 2:
                // Call cancelLesson() method in Lesson class
                //cancelLesson();
                break;
            case 3:
                displaySpaceTable();
                break;
            case 4:
                displayScheduleTable();
                break;
            case 5:
                displayLocationTable();
                break;
            case 6:
                deleteAccountMenu();
                break;
                case 7:
                System.out.println("Manage Bookings:");
                try (Connection connection = DatabaseConnection.getConnection()) {
                    // Display all bookings by calling the static method
                    Booking.viewBookingByAdmin(connection);
                } catch (SQLException e) {
                    System.err.println("Error connecting to the database: " + e.getMessage());
                    break;
                }
            
                // Prompt the admin to either cancel a booking or exit
                boolean continueManaging = true;
                while (continueManaging) {
                    System.out.print("\nEnter the Booking ID to cancel, or 0 to return to the main menu: ");
                    int bookingId = scanner.nextInt();
                    
                    if (bookingId == 0) {
                        continueManaging = false; // Exit booking management
                    } else {
                        try (Connection connection = DatabaseConnection.getConnection()) {
                            boolean cancelled = Booking.cancelBooking(connection, bookingId);
                            
                            if (cancelled) {
                                System.out.println("Booking ID " + bookingId + " cancelled successfully.");
                            } else {
                                System.out.println("Failed to cancel booking. Ensure the Booking ID is correct.");
                            }
                        } catch (SQLException e) {
                            System.err.println("Error canceling booking: " + e.getMessage());
                        }
                    }
                }
                break;
            
            case 8:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
       
    }while(choice!=8);
        
    }

    private void deleteAccountMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of account to delete:");
        System.out.println("1. Client");
        System.out.println("2. Instructor");
        System.out.print("Enter your choice (1 or 2): ");
        int accountType = scanner.nextInt();

        switch (accountType) {
            case 1:
                displayClientTable();
                System.out.print("Enter Client ID to delete: ");
                int clientId = scanner.nextInt();
                deleteAccountFromDB("Client", clientId);
                break;
            case 2:
                displayInstructorTable();
                System.out.print("Enter Instructor ID to delete: ");
                int instructorId = scanner.nextInt();
                deleteAccountFromDB("Instructor", instructorId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private void displayClientTable() {
        lock.readLock().lock();
        String query = "SELECT * FROM Client";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Client Table:");
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                System.out.printf("Client ID: %d, Name: %s%n", clientId, name);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying Client table: " + e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
    }

    private void displayInstructorTable() {
        lock.readLock().lock();
        String query = "SELECT * FROM Instructor";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Instructor Table:");
            while (resultSet.next()) {
                int instructorId = resultSet.getInt("instructorId");
                String name = resultSet.getString("name");
                System.out.printf("Instructor ID: %d, Name: %s%n", instructorId, name);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying Instructor table: " + e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
    }

    private void deleteAccountFromDB(String tableName, int id) {
        lock.writeLock().lock();
        String nameColumn = tableName.equals("Client") ? "name" : "name"; // assuming both tables use "name" column for names
        String idColumn = tableName.equals("Client") ? "clientId" : "instructorId";
        String queryGetName = "SELECT " + nameColumn + " FROM " + tableName + " WHERE " + idColumn + " = ?";
        String queryDelete = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement getNameStatement = connection.prepareStatement(queryGetName);
             PreparedStatement deleteStatement = connection.prepareStatement(queryDelete)) {
    
            // First, get the name of the account to delete
            getNameStatement.setInt(1, id);
            ResultSet resultSet = getNameStatement.executeQuery();
            String name = null;
            
            if (resultSet.next()) {
                name = resultSet.getString(nameColumn);
            }
    
            // Proceed with deletion if name was found
            if (name != null) {
                deleteStatement.setInt(1, id);
                int rowsAffected = deleteStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(tableName + " account with ID " + id + " (" + name + ") deleted successfully.");
                } else {
                    System.out.println("No " + tableName + " account found with ID " + id + ".");
                }
            } else {
                System.out.println("No " + tableName + " account found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting " + tableName + " account: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
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
        
        int ID= DatabaseConnection.getLastIdFromTable("lesson","lessonId") + 1;
        // Create a new Lesson object with the entered details
        Lesson lesson = new Lesson(ID, type, scheduleId, locationId, privacy, spaceId, capacity);
        
        // Add lesson to dB
        lesson.createLesson(); 
        scanner.nextLine(); // Consume newline

        System.out.println("Lesson created successfully!");
        scanner.close();
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
