import CodingImplementation.src.database.DatabaseConnection;

public class Location {
    private int ID;
    private int scheduleId;
    private String locationName;
    private String city;

    public Location(String locationName, String city, int scheduleId) {
        this.locationName = locationName;
        this.scheduleId = scheduleId;
        this.city = city;

        int lastId = DatabaseConnection.getLastIdFromTable("location", "locationId");
        //System.out.println("Last Location ID: " + lastId);
        this.ID = lastId + 1;
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
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
