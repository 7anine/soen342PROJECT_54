package CodingImplementation.src;

public class Booking extends Record {
    private int offeringId;
    private int clientId;
    private int ID;
    private static int IDincrement = 1;


    public Booking(int offeringId, int clientId) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.clientId = clientId;
        this.ID = IDincrement;
        IDincrement++;
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
