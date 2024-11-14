package CodingImplementation.src;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import CodingImplementation.src.database.DatabaseConnection;

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
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Enter a number between 1 and 5: ");
                    scanner.next(); // Clear the invalid input
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character left by nextInt()

                switch (choice) {
                    case 1:
                        System.out.println("Sign In As: ");
                        System.out.println("1. Client");
                        System.out.println("2. Instructor");

                        System.out.print("Enter your choice (1 or 2): ");
                        int signInAs;
                        while (!scanner.hasNextInt()) {
                            System.out.print("Invalid input. Enter 1 or 2: ");
                            scanner.next(); // Clear invalid input
                        }
                        signInAs = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (signInAs) {
                            case 1:
                                System.out.print("Enter Client ID: ");
                                int clientID = scanner.nextInt();
                                System.out.print("Enter Client Password: ");
                                int clientPassword = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                Client newClient = Client.ClientSignIn(clientID, clientPassword);
                                if (newClient != null) {
                                    newClient.clientPortal();
                                }
                                break;
                            case 2:
                                System.out.print("Enter Instructor ID: ");
                                int instructorID = scanner.nextInt();
                                System.out.print("Enter Instructor Password: ");
                                int instructorPassword = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                Instructor newInstructor = Instructor.instructorSignIn(instructorID, instructorPassword);
<<<<<<< HEAD
                                newInstructor.instructorPortal();
=======
                                if (newInstructor != null) {
                                    newInstructor.instructorPortal();
                                }
>>>>>>> main
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                        break;
                    case 2:
                        System.out.println("Registering as a client...");
                        System.out.print("Enter your name: ");
                        String clientName = scanner.nextLine();
                        System.out.print("Enter your age: ");
                        int clientAge = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter your email: ");
                        String clientEmail = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String clientPasswordStr = scanner.nextLine();

                        Client newClient = new Client(clientName, clientAge, clientEmail);
                        System.out.println("Your client ID is: " + newClient.getID());
                        newClient.createClientAccount(newClient, clientPasswordStr);
                        newClient.clientPortal();
                        break;
                    case 3:
                        System.out.println("Registering as an instructor...");
                        System.out.print("Enter your name: ");
                        String instructorName = scanner.nextLine();
                        System.out.print("Enter your specialization: ");
                        String instructorSpecialization = scanner.nextLine();
                        System.out.print("Enter your phone number: ");
                        String instructorPhone = scanner.nextLine();
                        System.out.print("Enter the cities that you are available in (separated by commas): ");
                        String instructorCities = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String instructorPasswordStr = scanner.nextLine();

                        Instructor newInstructor = new Instructor(instructorName, instructorSpecialization, instructorCities, instructorPhone);
                        newInstructor.createInstructorAccount(newInstructor, instructorPasswordStr);
                        newInstructor.instructorPortal();
                        break;
                    case 4:
                        System.out.println("Signing in as an admin...");
                        System.out.print("Enter Admin ID: ");
                        int enteredId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Administrator admin = new Administrator(enteredId);
                        if (admin.verifyAdminId(connection, enteredId)) {
                            admin.adminPanel();
                            scanner.nextLine(); // Consume newline

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
            System.gc(); // Suggest garbage collection, though it's managed by JVM
        }
    }
}
