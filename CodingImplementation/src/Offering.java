
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import CodingImplementation.src.database.DatabaseConnection;


public class Offering extends Record {
    private int ID;
    private int IDincrement = 1;
    private int lessonId;
    private int instructorId;
    private boolean isAvailable;
    

    public Offering( String location, int lessonId, int instructorId) {
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

    public int getInstructorId() {
        return instructorId;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setOfferingId(int offeringId) {
        this.ID = offeringId;
    }


    public void setAvailability(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    


    public void addOfferingToDB(Offering offering) {
        String insertQuery = "INSERT INTO Offering (ID, lessonId, instructorId, isAvailable) VALUES (?, ?, ?, ?)";

        // Establish a connection using the DatabaseConnection class
        try (Connection connection = DatabaseConnection.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set the values of the query parameters
            preparedStatement.setInt(1, offering.getOfferingId());
            preparedStatement.setInt(2, offering.getLessonId());
            preparedStatement.setInt(3, offering.getInstructorId());
            preparedStatement.setBoolean(4, offering.getIsAvailable());

            // Execute the insert query
            preparedStatement.executeUpdate();
            System.out.println("Offering added to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding offering to database.");
        }
    }

    public void deleteOfferingByID(int offeringID){
        String deleteOfferingSQL = "DELETE FROM Offering WHERE offeringId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteOfferingSQL)) {

            preparedStatement.setInt(1, offeringID);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Offering and associated bookings deleted successfully.");
            } else {
                System.out.println("Offering not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting offering: " + e.getMessage());
        }
    }
    
}
 