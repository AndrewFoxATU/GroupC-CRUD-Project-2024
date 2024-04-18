/* package ie.atu;

import java.sql.*;
import java.util.Scanner;

public class SubwayOrderApp {

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/SubwayDatabase", "root", "password")) {
            Scanner scanner = new Scanner(System.in);

            // Prompt user for their information
            System.out.println("Please enter your name:");
            String name = scanner.nextLine();

            System.out.println("Please enter your email:");
            String email = scanner.nextLine();

            System.out.println("Please enter your phone number:");
            String phone = scanner.nextLine();

            // Insert user information into Customers table
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Customers (Name, Email, Phone) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            // Get the generated CustomerID

            // Display available sandwiches
            System.out.println("Available sandwiches:");
            displaySandwiches(conn);

            // Prompt user to choose a sandwich
            System.out.println("Please enter the ID of the sandwich you want:");
            int sandwichID = scanner.nextInt();

            // Insert order into Orders table
            stmt = conn.prepareStatement("INSERT INTO Orders (CustomerID, OrderDate, TotalPrice) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            //stmt.setInt(1, customerID);
            stmt.setDate(2, new Date(System.currentTimeMillis()));
            stmt.setDouble(3, 0); // Placeholder for TotalPrice
            stmt.executeUpdate();

            // Get the generated OrderID


            // Insert order details into OrderSandwiches table

            // Display available ingredients
            System.out.println("Available ingredients:");
            displayIngredients(conn);

            // Prompt user to choose ingredients
            /*
            System.out.println("Please enter the IDs of the ingredients you want (separated by commas):");
            scanner.nextLine(); // Consume newline character
            String ingredientIDsInput = scanner.nextLine();
            String[] ingredientIDs = ingredientIDsInput.split(",");
            */
            // Insert selected ingredients into SandwichIngredients table
/*
            System.out.println("Order successfully placed.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //display available sandwiches
    private static void displaySandwiches(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Sandwiches")) {
            while (rs.next()) {
                System.out.println(rs.getInt("SandwichID") + ": " + rs.getString("Name"));
            }
        }
    }

    //display available ingredients
    private static void displayIngredients(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Ingredients")) {
            while (rs.next()) {
                System.out.println(rs.getInt("IngredientID") + ": " + rs.getString("Name"));
            }
        }
    }
}

*/
/*
// Main class
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
            System.out.println("Enter Sandwich ID and Quantity (separated by space, enter -1 to finish):");
            while (true) {
                int sandwichID = scanner.nextInt();
                if (sandwichID == -1)
                    break;
                int quantity = scanner.nextInt();
                order.getOrderDetails().addSandwich(sandwichID, quantity);
            }

            // Creating the order
            order.create(conn);

            // Displaying order history for the customer
            order.displayOrderHistory(conn, customerID);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
*/


