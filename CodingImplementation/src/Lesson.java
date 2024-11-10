
public class Lesson {
    private String type;
    private Schedule schedule;
    private Space space;
    private String privacy;
    private String location;
    private boolean assignedInstructor;
    private int capacity;
    private int numberRegistered;

    private int ID;
    private static int IDincrement =0;


    public Lesson(String type, Schedule schedule,String location, String privacy, Space space) {
        this.type = type;
        this.schedule = schedule;
        this.space = space;
        this.privacy = privacy;
        this.location = location;
        this.assignedInstructor = false;
        this.capacity = 20;//hard coded for now
        //need to separate private and public lessons
        this.numberRegistered = 0;
        this.ID = IDincrement;
        IDincrement++;
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
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public boolean isAssignedInstructor() {
        return assignedInstructor;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
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
