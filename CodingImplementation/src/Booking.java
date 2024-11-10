package CodingImplementation.src;


public class Booking extends Record {
    private String offeringId;
    private String clientName;
    private int ID;
    private static int IDincrement = 0;


    public Booking(String offeringId, String clientName) {
        super();  // Call to parent class constructor if necessary
        this.offeringId = offeringId;
        this.clientName = clientName;
        this.ID = IDincrement;
        IDincrement++;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public String getClientName() {
        return clientName;
    }

    public int getID() {
        return ID;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
