import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to The Lesson Link!");
        
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
                    //send to sign in method
                    //then see options
                    break;
                case 2:
                    System.out.println("Registering as a client...");
                    break;
                case 3:
                    System.out.println("Registering as an instructor...");
                    // send to instructor registration emthpd
                    break;
                case 4:
                    System.out.println("Signing in as an admin...");
                    // send to admin sign in method
                    break;
                case 5:
                    System.out.println("Thank you for using The Lesson Link. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        } while (choice != 5);

        scanner.close();

    }
}