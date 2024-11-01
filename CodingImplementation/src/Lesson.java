public class Lesson {
    private String type;
    private Schedule schedule;
    private Space space;
    private String Privacy;
    private boolean assignedInstructor;
    private boolean isAvailable;
    private int capacity;
    private int numberRegistered;

    public Lesson(String type, Schedule schedule, Space space, String privacy, boolean assignedInstructor) {
        this.type = type;
        this.schedule = schedule;
        this.space = space;
        Privacy = privacy;
        this.assignedInstructor = assignedInstructor;
        this.isAvailable = true;
        this.capacity = 20;//hard coded for now
        //need to separate private and public lessons
        this.numberRegistered = 0;
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
    public void clientAdded(){
        this.numberRegistered++;
    }
    public boolean isAvailable(){
        if(this.capacity<this.numberRegistered){
            return true;
        }
        return false;
    }
}
