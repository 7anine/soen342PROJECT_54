import CodingImplementation.src.database.DatabaseConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Instructor extends Record implements User{
    private String name;
    private String specialization;
    private String cities;
    private String phoneNumber;
    // private boolean availability; not sure how to use that
    private int ID;  // Unique identifier for the administrator

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock staticLock = new ReentrantReadWriteLock();

    public Instructor(String name, String specialization, String cities, String phoneNumber) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
        int lastInstructorId = DatabaseConnection.getLastIdFromTable("instructor", "instructorId");
        System.out.println("Last Client ID: " + lastInstructorId);

        this.ID = lastInstructorId + 1;
    }

    // to be called if creating an instructor obj for an instructor already in database (signing in)
    public Instructor(String name, String specialization, String cities, String phoneNumber, int ID) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
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

   

   public void delete(){}


    public void createInstructorAccount(Instructor instructor, String password) {
        // Assign instance variables
        this.ID = instructor.getID();
        this.name = instructor.getName();
        this.specialization = instructor.getSpecialization();
        this.phoneNumber = instructor.getPhone();
        this.cities = instructor.getCities();


        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is writing to the database from method createInstructorAccount" );

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
        } finally {
            lock.writeLock().unlock();  // Release the read lock
            System.out.println(Thread.currentThread().getName() + " finished writing to the database from method createInstructorAccount");
        }
    }


    public static Instructor instructorSignIn(int instructorID, int instructorPassword) {
        if (validPassword(instructorID, instructorPassword)) {

            staticLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " is reading from database from method instructorSignIn");


                    String selectQuery = "SELECT instructorId, name, specialization, phoneNumber, cities FROM Instructor WHERE instructorId = ?";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(selectQuery)) {

                    statement.setInt(1, instructorID);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            // Retrieve instructor data from the ResultSet
                            int id = resultSet.getInt("instructorId");
                            String name = resultSet.getString("name");
                            String specialization = resultSet.getString("specialization");
                            String phoneNumber = resultSet.getString("phoneNumber");
                            String cities = resultSet.getString("cities");

                            // Create and return the Instructor object
                            return new Instructor(name, specialization, phoneNumber, cities, id);
                        }
                    }

                } catch (SQLException e) {
                    System.err.println("Error retrieving instructor data: " + e.getMessage());
                }
            } finally {
                staticLock.readLock().unlock(); // Release the instance-level write lock
                System.out.println(Thread.currentThread().getName() + " finished reading to the database from method instructorSignIn");
            }
        } else {
            System.out.println("Sign-in failed. Cannot create instructor object.");
        }

        return null; // Return null if sign-in fails or instructor data is not retrieved
    }

    public static boolean validPassword(int instructorID, int instructorPassword) {

        staticLock.readLock().lock();  // Acquire the read lock
        try {
            System.out.println(Thread.currentThread().getName() + " is reading from database from method validPassword");


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
        } finally {
            staticLock.readLock().unlock();  // Release the read lock
            System.out.println(Thread.currentThread().getName() + " finished reading from database from method validPassword.");
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
                    selectLessonForBooking(); // Allow instructor to select a lesson for booking
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

        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " is reading from the database from method viewAllLessons");


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
        } finally {
            lock.readLock().unlock(); // Release the instance-level write lock
            System.out.println(Thread.currentThread().getName() + " finished reading to the database from method viewAllLessons");
        }
    }

    private void selectLessonForBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Lesson ID to create an offering for, or 0 to go back: ");
        int lessonId = scanner.nextInt();

        if (lessonId == 0) {
            return; // Return to the main portal menu
        }

        createOfferingForLesson(lessonId, this.ID);
    }

    private void createOfferingForLesson(int lessonId, int instructorID) {
        String insertQuery = "INSERT INTO Offering (offeringID, lessonId, instructorID) VALUES (?, ?, ?)";
        String getLessonCityQuery = "SELECT Location.locationCity FROM Lesson " +
                "JOIN Location ON Lesson.locationID = Location.locationID " +
                "WHERE Lesson.lessonId = ?";
        String getInstructorCitiesQuery = "SELECT cities FROM Instructor WHERE instructorID = ?";

        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is writing to the database from method createOfferingForLesson");

            // Step 1: Retrieve the lesson's location city
            String lessonCity = null;
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement lessonCityStmt = connection.prepareStatement(getLessonCityQuery)) {

                lessonCityStmt.setInt(1, lessonId);
                try (ResultSet lessonCityResult = lessonCityStmt.executeQuery()) {
                    if (lessonCityResult.next()) {
                        lessonCity = lessonCityResult.getString("locationCity");
                    } else {
                        System.out.println("Lesson or Location not found for lesson ID: " + lessonId);
                        return;
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving lesson city: " + e.getMessage());
                return;
            }

            // Step 2: Retrieve the instructor's cities
            String instructorCities = null;
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement instructorCitiesStmt = connection.prepareStatement(getInstructorCitiesQuery)) {

                instructorCitiesStmt.setInt(1, instructorID);
                try (ResultSet instructorCitiesResult = instructorCitiesStmt.executeQuery()) {
                    if (instructorCitiesResult.next()) {
                        instructorCities = instructorCitiesResult.getString("cities");
                    } else {
                        System.out.println("Instructor not found for instructor ID: " + instructorID);
                        return;
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving instructor cities: " + e.getMessage());
                return;
            }

            // Step 3: Verify if the instructor's cities contain the lesson's location city
            if (instructorCities != null && lessonCity != null && instructorCities.contains(lessonCity)) {
                // Step 4: Create the offering if the instructor is allowed to teach in the lesson's location city
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(insertQuery)) {


                    int lastId = DatabaseConnection.getLastIdFromTable("offering", "offeringId");
                    System.out.println("Last Offering ID: " + lastId);
                    statement.setInt(1, lastId + 1);


                    statement.setInt(2, lessonId);
                    statement.setInt(3, instructorID);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Offering created successfully for instructor ID " + instructorID + " and Lesson ID " + lessonId);


                        // TODO: Make lesson unavailable since instructor took it
                        String updateLessonAvailabilityQuery = "UPDATE Lesson SET isAvailable = false WHERE lessonId = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateLessonAvailabilityQuery)) {
                            updateStmt.setInt(1, lessonId);
                            int updateRowsAffected = updateStmt.executeUpdate();
                            if (updateRowsAffected > 0) {
                                System.out.println("Lesson ID " + lessonId + " marked as unavailable.");
                            } else {
                                System.out.println("Failed to update lesson availability for lesson ID " + lessonId);
                            }
                        } catch (SQLException e) {
                            System.err.println("Error marking lesson as unavailable: " + e.getMessage());
                        }

                    } else {
                        System.out.println("Failed to create the offering.");
                    }

                } catch (SQLException e) {
                    System.err.println("Error creating offering: " + e.getMessage());
                }
            } else {
                System.out.println("Instructor with ID " + instructorID + " is not authorized to create an offering for lesson in city " + lessonCity);
            }
        } finally {
            lock.writeLock().unlock(); // Release the instance-level write lock
            System.out.println(Thread.currentThread().getName() + " finished writing to the database from method createOfferingForLesson");
        }
    }
}
