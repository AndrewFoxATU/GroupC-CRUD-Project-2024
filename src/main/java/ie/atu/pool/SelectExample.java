package ie.atu.pool;

import java.sql.*;

public class SelectExample {



    public static void main(String[] args) {
        // SQL statement
        String selectSQL = "SELECT c.Name, c.Email, c.Phone " +
                "FROM Customers c " +
                "JOIN Orders o ON c.CustomerID = o.CustomerID";


        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                System.out.println("Name: " + name + ", Email: " + email + ", Phone: " + phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
