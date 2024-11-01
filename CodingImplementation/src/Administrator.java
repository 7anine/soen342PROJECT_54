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

    public boolean deleteAccount(User user){
        if(user instanceof Client){
            Client client = (Client) user;
            client.delete();
            return true;
        }
        else if(user instanceof Instructor){
            Instructor instructor = (Instructor) user;
            instructor.delete();
            return true;
        }
        return false;
    }
}
