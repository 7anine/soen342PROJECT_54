import database.DatabaseConnection;

public class Space {

    private int ID;
    private Location location;
    public Space(Location location) {
        this.location = location;
        int lastId = DatabaseConnection.getLastIdFromTable("space", "spaceId");
        System.out.println("Last Space ID: " + lastId);
        this.ID = lastId + 1;
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
