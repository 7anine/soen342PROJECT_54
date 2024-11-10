
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Offering extends Record {
    private int ID;
    private int IDincrement =0;

    private int lessonId;
    private String instructorId;
    private boolean isAvailable;
    

    public Offering( String location, int lessonId, String instructorId) {
        super();  // Call to parent class constructor if necessary
        this.lessonId = lessonId;
        this.instructorId = instructorId;
        this.isAvailable = true;
        this.ID = IDincrement;
        IDincrement++;
    }

    public int getOfferingId() {
        return ID;
    }
    public int getLessonId() {
        return lessonId;
    }

    public String getInstructorId() {
        return instructorId;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setOfferingId(int offeringId) {
        this.ID = offeringId;
    }

    

    public void setLessonId(int lessonId) {
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
 