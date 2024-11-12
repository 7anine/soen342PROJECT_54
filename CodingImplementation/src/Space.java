

public class Space {

    private int ID;
    private static int IDincrement = 1;
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


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
