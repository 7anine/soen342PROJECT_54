public class Administrator extends Record {
    private String adminId;  // Unique identifier for the administrator

    public Administrator(String adminId) {
        super();  // Call to parent class constructor if necessary
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
