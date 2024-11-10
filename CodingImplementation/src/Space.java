package CodingImplementation.src;


public class Space {

    private int ID;
    private int IDincrement=0;
    private Location location;
    public Space(Location location) {
        this.location = location;
        this.ID = IDincrement;
        IDincrement++;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
