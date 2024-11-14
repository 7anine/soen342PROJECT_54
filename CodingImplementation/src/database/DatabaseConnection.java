package CodingImplementation.src.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/soen342";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password"; //change your password here
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int getLastIdFromTable(String tableName, String nameOfColumn) {
        int lastId = 0; // Default to 0 if the table is empty

        String query = "SELECT "+nameOfColumn+" FROM " + tableName + " ORDER BY "+nameOfColumn+" DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getInt(1); // Get the first column of the last row
                System.out.println("got: " + lastId);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving last ID from table " + tableName + ": " + e.getMessage());
        }

        return lastId;
    }

}
