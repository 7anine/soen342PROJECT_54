import database.DatabaseConnection;

public class Booking extends Record {
    private int offeringId;
    private int clientId;
    private int ID;


    public Booking(int ID, int offeringId, int clientId) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.clientId = clientId;
        int lastId = DatabaseConnection.getLastIdFromTable("booking", "bookingId");
        System.out.println("Last Booking ID: " + lastId);
        this.ID = lastId + 1;
    }

    public int getOfferingId() {
        return offeringId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getID() {
        return ID;
    }

    public void setOfferingId(int offeringId) {
        this.offeringId = offeringId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    //Method to view all bookings by admin
    public static void viewBookingByAdmin(Connection connection) {
        staticLock.readLock().lock();
        try {
            String query = """
                SELECT Booking.bookingId, Lesson.type, Schedule.date, Instructor.name AS instructorName, 
                       Client.clientId, Client.name AS clientName
                FROM Booking
                INNER JOIN Offering ON Booking.offeringId = Offering.offeringId
                INNER JOIN Lesson ON Offering.lessonId = Lesson.lessonId
                INNER JOIN Schedule ON Lesson.scheduleId = Schedule.scheduleId
                INNER JOIN Instructor ON Lesson.instructorId = Instructor.instructorId
                INNER JOIN Client ON Booking.clientId = Client.clientId;
                """;

            try (var statement = connection.createStatement();
                 var resultSet = statement.executeQuery(query)) {

                System.out.println("Bookings for all clients:");

                while (resultSet.next()) {
                    int bookingId = resultSet.getInt("bookingId");
                    String lessonType = resultSet.getString("type");
                    String date = resultSet.getDate("date").toString();
                    String instructorName = resultSet.getString("instructorName");
                    int clientId = resultSet.getInt("clientId");
                    String clientName = resultSet.getString("clientName");

                    // Format and display booking details
                    System.out.printf("Booking ID %d: Client %s (ID: %d) has booked a %s lesson on %s, taught by %s.%n",
                            bookingId, clientName, clientId, lessonType, date, instructorName);
                }

            } catch (SQLException e) {
                System.err.println("Error fetching bookings: " + e.getMessage());
            }
        } finally {
            staticLock.readLock().unlock();
            System.out.println(Thread.currentThread().getName() + " finished reading from database from method viewBookingByAdmin.");
        }
    }
    //Cancel a booking via id
    public static boolean cancelBooking(Connection connection, int bookingId) {
    staticLock.writeLock().lock(); // Lock for write operation
    String query = "DELETE FROM Booking WHERE bookingId = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, bookingId);
        
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0; // Return true if booking was deleted
    } catch (SQLException e) {
        System.err.println("Error deleting booking: " + e.getMessage());
        return false;
    } finally {
        staticLock.writeLock().unlock(); // Release the lock
        System.out.println(Thread.currentThread().getName() + " finished write operation in cancelBooking.");
    }
}


}
