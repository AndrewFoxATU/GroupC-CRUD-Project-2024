package ie.atu.app;

import ie.atu.pool.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SubwayCRUD implements CRUD {

    @Override
    public void create(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Total Price: ");
        double totalPrice = scanner.nextDouble();

        System.out.print("Enter Sandwich ID: ");
        int sandwichID = scanner.nextInt();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String insertOrderSQL = "INSERT INTO Orders (TotalPrice) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, totalPrice);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int orderID = -1;
            if (generatedKeys.next()) {
                orderID = generatedKeys.getInt(1);
            }

            String insertOrderSandwichSQL = "INSERT INTO OrderSandwiches (OrderID, SandwichID, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement orderSandwichStmt = conn.prepareStatement(insertOrderSandwichSQL)) {
                orderSandwichStmt.setInt(1, orderID);
                orderSandwichStmt.setInt(2, sandwichID);
                orderSandwichStmt.setInt(3, quantity);
                orderSandwichStmt.executeUpdate();
            }

            System.out.println("Order created successfully.");
        }
    }

    @Override
    public void read(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        String query = "SELECT * FROM Orders WHERE CustomerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                String orderDate = resultSet.getString("OrderDate"); // Assuming OrderDate is stored as a string
                double totalPrice = resultSet.getDouble("TotalPrice");
                System.out.println("OrderID: " + orderID + ", Order Date: " + orderDate + ", Total Price: " + totalPrice);
            }
        }
    }


    @Override
    public void update(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        System.out.print("Enter new Total Price: ");
        double newTotalPrice = scanner.nextDouble();

        String updateQuery = "UPDATE Orders SET TotalPrice = ? WHERE CustomerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setDouble(1, newTotalPrice);
            stmt.setInt(2, customerID);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("No order found for the given Customer ID.");
            }
        }
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        String deleteQuery = "DELETE FROM Orders WHERE CustomerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, customerID);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("No order found for the given Customer ID.");
            }
        }
    }
}
