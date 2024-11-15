import database.DatabaseConnection;

public class Booking extends Record {
    private int offeringId;
    private int clientId;
    private int ID;


    public Booking(int offeringId, int clientId) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.clientId = clientId;
        int lastId = DatabaseConnection.getLastIdFromTable("booking", "bookingId");
        System.out.println("Last Booking ID: " + lastId);
        this.ID = lastId + 1;
    }

    public int getOfferingId() {
        return offeringId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getID() {
        return ID;
    }

    public void setOfferingId(int offeringId) {
        this.offeringId = offeringId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
