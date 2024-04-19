package ie.atu;

import ie.atu.pool.DatabaseUtils;

import java.sql.*;

public class UpdateExample {
    public static void main(String[] args) {
        String updateSQL = "UPDATE Customers SET Phone = 'newphone' WHERE Name = 'John Doe'";

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsUpdated = statement.executeUpdate(updateSQL);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
