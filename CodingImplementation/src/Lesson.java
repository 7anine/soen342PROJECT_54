public class Lesson {
    private String type;
    private Schedule schedule;
    private Space space;
    private String Privacy;
    private boolean assignedInstructor;

    public Lesson(String type, Schedule schedule, Space space, String privacy, boolean assignedInstructor) {
        this.type = type;
        this.schedule = schedule;
        this.space = space;
        Privacy = privacy;
        this.assignedInstructor = assignedInstructor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }

    public boolean isAssignedInstructor() {
        return assignedInstructor;
    }

    public void setAssignedInstructor(boolean assigned) {
        this.assignedInstructor = assigned;
    }
}
