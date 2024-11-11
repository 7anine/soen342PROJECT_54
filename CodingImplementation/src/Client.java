package CodingImplementation.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import CodingImplementation.src.database.DatabaseConnection;

public class Client extends Record {
    private String name;
    private int age;
    private String email;
    private Client guardian;
    private int guardianID;
    private static int IDincrement = 0;

    private int ID;

    public Client(String name, int age, String email) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
        if(age<18){
            this.guardian = requestGuardian();
            this.guardianID = this.guardian.getID();
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void delete(){
        //delete the client from the database
    }

    public static void CreateClientAccount(String name, int age, String email, String password){
        Client newClient = new Client(name, age, email);
        newClient.RegisterClientAccountToDB(newClient, password);
    }

    public void RegisterClientAccountToDB(Client client, String password){
        //add logic to register new client to DB
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

}
