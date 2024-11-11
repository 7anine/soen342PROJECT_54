
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
        
        while(!choice.equals("3")){
        // Display the menu options
        String menu = 
            "Admin Panel\n" +
            "1. Create a Lesson\n" +
            "2. Cancel a Lesson\n" +
            "3. Log out\n" +
            "Please select an option (1/2/3): ";
        
        System.out.print(menu);
        choice = scanner.nextLine();  // Get user choice
        
            switch (choice) {
                case "1":
                    createLesson();
                    break;
                case "2":
                    // Call cancelLesson() method in Lesson class
                    
                    break;
                case "3":
                    // Log out
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
    }
        
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
        
        System.out.print("Enter privacy (private/group): ");
        String privacy = scanner.nextLine().toLowerCase();
        
        // Check that privacy is either "private" or "group"
        while (!privacy.equals("private") && !privacy.equals("group")) {
            System.out.print("Invalid input. Please enter 'private' or 'group' for privacy: ");
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
}
