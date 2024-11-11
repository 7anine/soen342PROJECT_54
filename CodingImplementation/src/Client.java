
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import CodingImplementation.src.database.DatabaseConnection;

public class Client extends Record {
    private String name;
    private int age;
    private boolean isOver18;
    private String email;
    private Client guardian;
    private int guardianID = 0;
    private static int IDincrement = 1;

    private int ID;

    public Client(String name, int age, String email) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
        if(age<18){
            this.isOver18 = false;
            this.guardian = requestGuardian();
            this.guardianID = this.guardian.getID();
        }else{
            this.isOver18 = true;
        }
        this.ID = IDincrement;
        IDincrement++;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Client(String name, int age, String email, int ID, int guardianID) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
        this.ID =ID;
        this.guardianID = guardianID;
        //TODO: create guardian by fetching attributes by ID from database if needed
    }

    public boolean isOver18() {
        return isOver18;
    }

    public void setOver18(boolean over18) {
        isOver18 = over18;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Client getGuardian() {
        return guardian;
    }

    public void setGuardian(Client guardian) {
        this.guardian = guardian;
    }

    public int getGuardianID() {
        return guardianID;
    }

    public void setGuardianID(int guardianID) {
        this.guardianID = guardianID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void delete(){
        //delete the client from the database
    }

    public void createClientAccount(Client client, String password) {
        // Assign instance variables
        this.ID = client.getID();
        this.name = client.getName();
        this.age = client.getAge();
        this.email = client.getEmail();
        this.isOver18 = client.isOver18();
        this.guardianID = client.getGuardianID();

        // Insert into database
        String insertQuery = "INSERT INTO Client (clientId, name, age, email, isOver18, guardianClientId, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, this.ID);
            statement.setString(2, this.name);
            statement.setInt(3, this.age);
            statement.setString(4, this.email);
            statement.setBoolean(5, this.isOver18);
            if (this.getGuardianID()!= 0) {
                statement.setInt(6, this.guardianID);
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }
            statement.setString(7, password);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client registered with the following details:");
                System.out.println("Name: " + this.name);
                System.out.println("Age: " + this.age);
                System.out.println("Email: " + this.email);
                System.out.println("Is Over 18: " + this.isOver18);
                System.out.println("Guardian Client ID: " + (this.guardianID != 0 ? this.guardianID : 0));
            } else {
                System.out.println("Failed to register the client.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding client to the database: " + e.getMessage());
        }
    }



    public Client requestGuardian(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("You are under 18. Please ask a parent to enter his/her information: ");
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your age:");
        int age = scanner.nextInt();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        //not adding the guardian as a user, but as someone who logs into his childs account with the same password,
        //and is authorized to make bookings in his name (add to db still for persistance)

        return new Client(name, age, email);
    }


    public static boolean clientSignIn(int clientID, int clientPassword) {
        String selectQuery = "SELECT password FROM Client WHERE clientId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            statement.setInt(1, clientID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    // Check if the password matches
                    if (storedPassword.equals(Integer.toString(clientPassword))) {
                        System.out.println("Sign-in successful.");
                        return true;
                    } else {
                        System.out.println("Incorrect password.");
                    }
                } else {
                    System.out.println("Client ID not found.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error during client sign-in: " + e.getMessage());
        }

        return false;
    }
    public static Client createClientObject(int clientId, int clientPassword) {
        if (clientSignIn(clientId, clientPassword)) {
            String selectQuery = "SELECT clientId, name, age, email, isOver18, guardianClientId FROM Client WHERE clientId = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(selectQuery)) {

                statement.setInt(1, clientId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve client data from the ResultSet
                        int id = resultSet.getInt("clientId");
                        String name = resultSet.getString("name");
                        int age = resultSet.getInt("age");
                        String email = resultSet.getString("email");
                        boolean isOver18 = resultSet.getBoolean("isOver18");
                        int guardianId = resultSet.getObject("guardianClientId") != null ? resultSet.getInt("guardianClientId") : null;

                        // Create and return the Client object
                        return new Client(name, age, email, id, guardianId);
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving client data: " + e.getMessage());
            }
        } else {
            System.out.println("Sign-in failed. Cannot create client object.");
        }

        return null; // Return null if sign-in fails or client data is not retrieved
    }


    public void clientPortal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Client Portal ---");
            System.out.println("1. View all offerings");
            System.out.println("2. Sign out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewOfferings();
                    break;
                case 2:
                    System.out.println("Signing out...");
                    return; // Exit the portal
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void viewOfferings() {
        String query = "SELECT * FROM Offering";
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n--- Available Offerings ---");
            boolean offeringsFound = false;

            while (resultSet.next()) {
                int offeringId = resultSet.getInt("offeringId");
                int lessonId = resultSet.getInt("lessonId");
                int clientId = resultSet.getInt("clientId");
                int scheduleId = resultSet.getInt("scheduleId");

                System.out.println("Offering ID: " + offeringId);
                System.out.println("Lesson ID: " + lessonId);
                System.out.println("Client ID: " + clientId);
                System.out.println("Schedule ID: " + scheduleId);
                System.out.println("--------------------------");

                offeringsFound = true;
            }

            if (!offeringsFound) {
                System.out.println("No offerings available at the moment.");
                return;
            }

            System.out.print("Enter the Offering ID to create a new booking, or enter 0 to go back: ");
            int selectedId = scanner.nextInt();

            if (selectedId == 0) {
                return; // Go back to client portal menu
            } else {
                handleOfferingSelection(selectedId);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching offerings: " + e.getMessage());
        }
    }

    private void handleOfferingSelection(int offeringId) {
        System.out.println("You selected offering ID: " + offeringId);
        System.out.println("Creating a new booking for you...");

        Booking booking = new Booking(offeringId, this.ID);

        addBookingToDatabase(booking);
    }

    private void addBookingToDatabase(Booking booking) {
        String insertQuery = "INSERT INTO Booking (bookingId, clientId, lessonId) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            // Set values for the bookingId, clientId, and lessonId columns
            statement.setInt(1, booking.getID());
            statement.setInt(2, booking.getClientId());
            statement.setInt(3, booking.getOfferingId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking created successfully!");
            } else {
                System.out.println("Failed to create the booking.");
            }

        } catch (SQLException e) {
            System.err.println("Error creating booking: " + e.getMessage());
        }
    }
}
