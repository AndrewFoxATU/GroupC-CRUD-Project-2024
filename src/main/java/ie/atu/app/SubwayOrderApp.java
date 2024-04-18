package ie.atu.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// main class is for the cashier to enter the customer id, date, price, sandwich id and quantity
public class SubwayOrderApp {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/SubwayDatabase", "root", "password")) {
            Scanner scanner = new Scanner(System.in);

            // User input for order details
            System.out.println("Enter Customer ID:");
            int customerID = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.println("Enter Order Date (YYYY-MM-DD):");
            String orderDate = scanner.nextLine();

            System.out.println("Enter Total Price:");
            double totalPrice = scanner.nextDouble();

            // Creating order object
            Order order = new Order();
            order.setCustomerID(customerID);
            order.setOrderDate(orderDate);
            order.setTotalPrice(totalPrice);

            // User input for sandwich details
            System.out.println("Enter Sandwich ID and Quantity (Enter -1 to finish):");
            while (true) {
                int sandwichID = scanner.nextInt();
                if (sandwichID == -1)
                    break;
                int quantity = scanner.nextInt();
                order.getOrderDetails().addSandwich(sandwichID, quantity);
            }
            // Creating the order
            order.create(conn);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}