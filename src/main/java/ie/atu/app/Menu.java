package ie.atu.app;

import ie.atu.pool.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Menu {
    public static void main(String[] args) {
        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM Sandwiches";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Subs Menu:");
            System.out.println("ID\tPrice\tName");
            while (resultSet.next()) {
                int sandwichID = resultSet.getInt("SandwichID");
                double price = resultSet.getDouble("Price");
                String name = resultSet.getString("Name");
                System.out.println(sandwichID + "\t$" + price + "\t" + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
