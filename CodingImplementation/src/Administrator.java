public class Administrator extends Record {
    private String adminId;  // Unique identifier for the administrator

    // Constructor
    public Administrator(String adminId) {
        super();  // Call to parent class constructor if necessary
        this.adminId = adminId;
    }

    // Accessor (Getter)
    public String getAdminId() {
        return adminId;
    }

    // Mutator (Setter)
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
