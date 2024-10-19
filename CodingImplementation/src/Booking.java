public class Booking extends Record {
    private String bookingId;
    private String clientName;
    private String lessonId;

    // Constructor
    public Booking(String bookingId, String clientName, String lessonId) {
        super();  // Call to parent class constructor if necessary
        this.bookingId = bookingId;
        this.clientName = clientName;
        this.lessonId = lessonId;
    }

    // Accessors (Getters)
    public String getBookingId() {
        return bookingId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getLessonId() {
        return lessonId;
    }

    // Mutators (Setters)
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
}
