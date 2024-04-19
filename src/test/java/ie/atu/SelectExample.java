package ie.atu;


import java.sql.*;

public class SelectExample {
    public static void main(String[] args) {
        // MySQL database connection details
        String url = "jdbc:mysql://localhost:3306/SubwayDatabase";
        String username = "root";
        String password = "password";

        // SQL statement
        String selectSQL = "SELECT c.Name, c.Email, c.Phone " +
                "FROM Customers c " +
                "JOIN Orders o ON c.CustomerID = o.CustomerID";

        try (Connection connection = DriverManager.getConnection(url, username, password);
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