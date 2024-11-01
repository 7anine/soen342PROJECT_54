public class Offering extends Record {
    private String offeringId;
    private String location;
    private String lessonId;
    private String instructorId;

    public Offering(String offeringId, String location, String lessonId, String instructorId) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.location = location;
        this.lessonId = lessonId;
        this.instructorId = instructorId;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public String getLocation() {
        return location;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
 