package CodingImplementation.src;


public class Location {
    private int ID;
    private static int IDincrement = 1;
    private int scheduleId;
    private String locationName;

    public Location(String locationName, int scheduleId) {
        this.locationName = locationName;
        this.scheduleId = scheduleId;
        this.ID = IDincrement;
        IDincrement++;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
