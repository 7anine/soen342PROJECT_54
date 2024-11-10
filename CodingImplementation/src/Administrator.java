
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrator extends Record {
    private int adminId;  // Unique identifier for the administrator

    public Administrator(int adminId) {
          // Call to parent class constructor if necessary
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
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
    //verify adminID
    public boolean verifyAdminId(Connection connection, int adminId) {
        String query = "SELECT COUNT(*) FROM Administrator WHERE adminId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // returns true if adminId exists
            }
        } catch (SQLException e) {
            System.err.println("Error verifying admin ID: " + e.getMessage());
        }
        return false; // return false if adminId doesn't exist
    }

    public String adminPanel(){
        return " ";
    }
}
