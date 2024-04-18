package ie.atu.app;

import ie.atu.pool.DatabaseUtils;

import java.sql.*;
import java.util.Scanner;

public class SubwayOrderApp {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // User input for order details
                System.out.println("Enter Customer ID:");
                int customerID = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                System.out.println("Enter Order Date (YYYY-MM-DD):");
                String orderDate = scanner.nextLine();

                System.out.println("Enter Total Price:");
                double totalPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character

                // Creating order object
                Order order = new Order();
                order.setCustomerID(customerID);
                order.setOrderDate(orderDate);
                order.setTotalPrice(totalPrice);

                // User input for sandwich details
                System.out.println("Enter Sandwich ID (Enter -1 to finish):");
                int sandwichID;
                while ((sandwichID = scanner.nextInt()) != -1) {
                    scanner.nextLine(); // Consume newline character
                    order.getOrderDetails().addSandwich(sandwichID, 1); // Assume quantity is 1
                    System.out.println("Enter Sandwich ID (Enter -1 to finish):");
                }

                // Creating the order
                order.create(conn);
                System.out.println("Order successfully created!");

                System.out.println("Do you want to place another order? (yes/no)");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("yes"))
                    break;
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
