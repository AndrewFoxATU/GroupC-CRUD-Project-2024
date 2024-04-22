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

        // Prompt for customer details
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Customer Phone: ");
        String phone = scanner.nextLine();

        // Prompt for order details
        System.out.print("Enter Sandwich ID: ");
        int sandwichID = scanner.nextInt();
        System.out.print("Enter Order Date (YYYY-MM-DD): ");
        String orderDate = scanner.next();

        // Calculate TotalPrice based on Sandwich Price
        double totalPrice = 0.0;
        String totalPriceQuery = "SELECT Price FROM Sandwiches WHERE SandwichID = ?";
        try (PreparedStatement priceStmt = conn.prepareStatement(totalPriceQuery)) {
            priceStmt.setInt(1, sandwichID);
            ResultSet priceResultSet = priceStmt.executeQuery();
            if (priceResultSet.next()) {
                totalPrice = priceResultSet.getDouble("Price");
            } else {
                System.out.println("Invalid Sandwich ID.");
                return;
            }
        }

        // Insert customer details
        String insertCustomerSQL = "INSERT INTO Customers (Name, Email, Phone) VALUES (?, ?, ?)";
        try (PreparedStatement customerStmt = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS)) {
            customerStmt.setString(1, name);
            customerStmt.setString(2, email);
            customerStmt.setString(3, phone);
            customerStmt.executeUpdate();

            ResultSet generatedKeys = customerStmt.getGeneratedKeys();
            int customerID = -1;
            if (generatedKeys.next()) {
                customerID = generatedKeys.getInt(1);
            }

            // Insert order details
            String insertOrderSQL = "INSERT INTO Orders (CustomerID, OrderDate, TotalPrice) VALUES (?, ?, ?)";
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, customerID);
                orderStmt.setString(2, orderDate);
                orderStmt.setDouble(3, totalPrice);
                orderStmt.executeUpdate();

                ResultSet orderGeneratedKeys = orderStmt.getGeneratedKeys();
                int orderID = -1;
                if (orderGeneratedKeys.next()) {
                    orderID = orderGeneratedKeys.getInt(1);
                }

                // Insert order sandwich details
                String insertOrderSandwichSQL = "INSERT INTO OrderSandwiches (OrderID, SandwichID, Quantity) VALUES (?, ?, 1)";
                try (PreparedStatement orderSandwichStmt = conn.prepareStatement(insertOrderSandwichSQL)) {
                    orderSandwichStmt.setInt(1, orderID);
                    orderSandwichStmt.setInt(2, sandwichID);
                    orderSandwichStmt.executeUpdate();
                }

                System.out.println("Order created successfully. Order ID: " + orderID);
            }
        }
    }


    public void read(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Order ID: ");
        int customerID = scanner.nextInt();

        String query = "SELECT * FROM Orders o JOIN Customers c ON o.CustomerID = c.CustomerID WHERE c.CustomerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, customerID);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No order found for the given Customer ID.");
            } else {
                // Move the cursor to before the first row to allow backward iteration
                resultSet.beforeFirst();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("OrderID");
                    String orderDate = resultSet.getString("OrderDate");
                    double totalPrice = resultSet.getDouble("TotalPrice");
                    String customerName = resultSet.getString("Name");
                    String email = resultSet.getString("Email");
                    String phone = resultSet.getString("Phone");
                    System.out.println("OrderID: " + orderID + ", Order Date: " + orderDate + ", Total Price: " + totalPrice + ", Customer Name: " + customerName + ", Email: " + email + ", Phone: " + phone);
                }
            }
        }
    }


    @Override
    public void update(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Order ID: ");
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

        System.out.print("Enter Order ID: ");
        int orderID = scanner.nextInt();

        String deleteOrderSandwichesQuery = "DELETE FROM OrderSandwiches WHERE OrderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteOrderSandwichesQuery)) {
            stmt.setInt(1, orderID);
            stmt.executeUpdate();
        }

        String deleteOrderQuery = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteOrderQuery)) {
            stmt.setInt(1, orderID);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("No order found for the given Order ID.");
            }
        }
    }
}