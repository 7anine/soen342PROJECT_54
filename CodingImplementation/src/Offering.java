package CodingImplementation.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import CodingImplementation.database.DatabaseConnection;

public class Offering extends Record {
    private String offeringId;
    private String lessonId;
    private String instructorId;
    private boolean isAvailable;
    

    public Offering(String offeringId, String location, String lessonId, String instructorId) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.lessonId = lessonId;
        this.instructorId = instructorId;
        this.isAvailable = true;
    }

    public String getOfferingId() {
        return offeringId;
    }
    public String getLessonId() {
        return lessonId;
    }

    public String getInstructorId() {
        return instructorId;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
    public void setAvailability(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public void addOfferingToDB(int lessonID, int instructorID) {
        String insertQuery = "INSERT INTO Offering (lessonId, instructorId) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, lessonID);
            statement.setInt(2, instructorID);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Offering added to the database successfully.");
            } else {
                System.out.println("Failed to add the offering.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding offering to the database: " + e.getMessage());
        }
    }
    
}
 