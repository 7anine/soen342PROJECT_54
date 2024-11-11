package CodingImplementation.src;


public class Location {
    private int locationId;
    private int scheduleId;
    private String locationName;
    
    
    public Location(String locationName, int scheduleId, int locationId) {
        this.locationName = locationName;
        this.scheduleId = scheduleId;
        this.locationId = locationId;
    }


}
