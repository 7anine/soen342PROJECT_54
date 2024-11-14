package CodingImplementation.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import CodingImplementation.src.database.DatabaseConnection;

public class Lesson {
    private String type;
    private int scheduleId;
    private int spaceId;
    private String privacy;
    private int locationId;
    private boolean assignedInstructor;
    private int capacity;
    private int numberRegistered;
    private int ID;


    public Lesson(int ID, String type, int scheduleId,int locationId, String privacy, int spaceId, int capacity) {
        this.type = type;
        this.scheduleId = scheduleId;
        this.spaceId = spaceId;
        this.privacy = privacy;
        this.locationId = locationId;
        this.assignedInstructor = false;
        this.capacity = capacity;
        this.numberRegistered = 0;
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSpacId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public boolean isAssignedInstructor() {
        return assignedInstructor;
    }
    public void setLocation(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }
    public void setAssignedInstructor(boolean assigned) {
        this.assignedInstructor = assigned;
    }
    public void clientAdded(){
        this.numberRegistered++;
    }
    public boolean isAvailable(){
        if(this.capacity<this.numberRegistered){
            return true;
        }
        return false;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }
    // Method to create a lesson
    public void createLesson() {
        // Add the lesson to the database
        addLessonToDB(this);  
        System.out.println("Lesson Created: " + type + " (Schedule ID: " + scheduleId + ", Location ID: " + locationId + 
                        ", Privacy: " + privacy + ", Space ID: " + spaceId + ")");
                        
    }

    public void addLessonToDB(Lesson lesson){
        String insertQuery = "INSERT INTO Lesson (lessonId, type, scheduleId, spaceId, locationId, privacy, capacity) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
             
            // Set parameters for the query
            preparedStatement.setInt(1, lesson.ID);
            preparedStatement.setString(2, lesson.type);
            preparedStatement.setInt(3, lesson.scheduleId);
            preparedStatement.setInt(4, lesson.spaceId);
            preparedStatement.setInt(5, lesson.locationId);
            preparedStatement.setString(6, lesson.privacy);
            preparedStatement.setInt(7, lesson.capacity);
            
            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lesson added to the database successfully!");
            } else {
                System.out.println("Failed to add lesson to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding lesson to database: " + e.getMessage());
        }
    }
}
