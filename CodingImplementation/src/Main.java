
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import database.DatabaseConnection;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to The Lesson Link!");

        // Attempt to connect to the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to the database successfully!");

            do {
                System.out.println("\nPlease choose an option:");
                System.out.println("1. Sign In");
                System.out.println("2. Register as a Client");
                System.out.println("3. Register as an Instructor");
                System.out.println("4. Sign In as an Admin");
                System.out.println("5. Quit");

                System.out.print("Enter your choice (1-5): ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Signing in...");
                        // Call your sign-in method here
                        break;
                    case 2:
                        System.out.println("Registering as a client...");
                        System.out.println("Enter your name:");
                        String clientName = scanner.nextLine();
                        System.out.println("Enter your age:");
                        int clientAge = scanner.nextInt();
                        System.out.println("Enter your email:");
                        String clientEmail = scanner.nextLine();
                        System.out.println("Enter your password:");
                        String clientPassword = scanner.nextLine();

                        Client newClient = new Client(clientName, clientAge, clientPassword);
                        newClient.RegisterClientAccountToDB(newClient,clientPassword);
                        break;
                    case 3:
                        System.out.println("Registering as an instructor...");
                        System.out.println("Enter your name:");
                        String instructorName = scanner.nextLine();
                        System.out.println("Enter your specialization:");
                        String instructorSpecialization = scanner.nextLine();
                        System.out.println("Enter your phone number:");
                        String instructorPhone = scanner.nextLine();
                        System.out.println("Enter the cities that you are available in (separated by commas):");
                        String instructorCities = scanner.nextLine();
                        System.out.println("Enter your password:");
                        String instructorPassword = scanner.nextLine();

                        Instructor newInstructor = new Instructor(instructorName, instructorSpecialization, instructorPhone, instructorCities, null);
                        newInstructor.register(newInstructor,instructorPassword);
                        break;
                    case 4:
                    System.out.println("Signing in as an admin...");
                        System.out.print("Enter Admin ID: ");
                        int enteredId = scanner.nextInt();

                        Administrator admin = new Administrator(enteredId);
                        if (admin.verifyAdminId(connection, enteredId)) {
                            admin.adminPanel();
                        } else {
                            System.out.println("Incorrect Admin ID. Access Denied.");
                        }
                        break;
                    case 5:
                        System.out.println("Thank you for using The Lesson Link. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;

                }
            } while (choice != 5);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
