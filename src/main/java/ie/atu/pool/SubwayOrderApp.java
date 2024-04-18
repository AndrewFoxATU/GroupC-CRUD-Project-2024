package ie.atu.pool;


import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Scanner;

// interface for CRUD operations
interface CRUD {
    void create(Connection conn) throws SQLException;
    void read(Connection conn) throws SQLException;
    void update(Connection conn) throws SQLException;
    void delete(Connection conn) throws SQLException;
}

// class for handling order details
class OrderDetails implements CRUD {
    private int orderID;
    private List<Integer> sandwichIDs;
    private List<Integer> quantities;

    // Constructor
    public OrderDetails() {
        this.sandwichIDs = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    // create method to add order details to the database
    @Override
    public void create(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO OrderSandwiches (OrderID, SandwichID, Quantity) VALUES (?, ?, ?)");
        for (int i = 0; i < sandwichIDs.size(); i++) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, sandwichIDs.get(i));
            stmt.setInt(3, quantities.get(i));
            stmt.executeUpdate();
        }
    }

    // read method to display order details from the database
    @Override
    public void read(Connection conn) throws SQLException {
        System.out.println("Order Details:");
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderSandwiches WHERE OrderID = ?")) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Sandwich ID: " + rs.getInt("SandwichID") + ", Quantity: " + rs.getInt("Quantity"));
            }
        }
    }

    // update method to update order details in the database
    @Override
    public void update(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE OrderSandwiches SET Quantity = ? WHERE OrderID = ? AND SandwichID = ?");
        for (int i = 0; i < sandwichIDs.size(); i++) {
            stmt.setInt(1, quantities.get(i));
            stmt.setInt(2, orderID);
            stmt.setInt(3, sandwichIDs.get(i));
            stmt.executeUpdate();
        }
    }

    // delete method to remove order details from the database
    @Override
    public void delete(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM OrderSandwiches WHERE OrderID = ?");
        stmt.setInt(1, orderID);
        stmt.executeUpdate();
    }

    // method to add a sandwich and its quantity to the order
    public void addSandwich(int sandwichID, int quantity) {
        sandwichIDs.add(sandwichID);
        quantities.add(quantity);
    }
}

// class for handling orders
class Order implements CRUD {
    private int orderID;
    private int customerID;
    private String orderDate;
    private double totalPrice;
    private OrderDetails orderDetails;

    // Constructor
    public Order() {
        this.orderDetails = new OrderDetails();
    }

    // create method to add an order to the database
    @Override
    public void create(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Orders (CustomerID, OrderDate, TotalPrice) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, customerID);
        stmt.setDate(2, Date.valueOf(orderDate));
        stmt.setDouble(3, totalPrice);
        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            orderID = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating order failed, no ID obtained.");
        }

        orderDetails.create(conn);
    }

    // read method to display orders from the database
    @Override
    public void read(Connection conn) throws SQLException {
        System.out.println("Orders:");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {
            while (rs.next()) {
                System.out.println("Order ID: " + rs.getInt("OrderID") + ", Customer ID: " + rs.getInt("CustomerID") + ", Date: " + rs.getDate("OrderDate") + ", Total Price: " + rs.getDouble("TotalPrice"));
            }
        }
    }

    // Implementing update method to update an order in the database
    @Override
    public void update(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Orders SET CustomerID = ?, OrderDate = ?, TotalPrice = ? WHERE OrderID = ?");
        stmt.setInt(1, customerID);
        stmt.setDate(2, Date.valueOf(orderDate));
        stmt.setDouble(3, totalPrice);
        stmt.setInt(4, orderID);
        stmt.executeUpdate();

        orderDetails.update(conn);
    }

    // Implementing delete method to remove an order from the database
    @Override
    public void delete(Connection conn) throws SQLException {
        orderDetails.delete(conn);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Orders WHERE OrderID = ?");
        stmt.setInt(1, orderID);
        stmt.executeUpdate();
    }


    // Getter and setter for orderID
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    // Getter and setter for customerID
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    // Getter and setter for orderDate
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    // Getter and setter for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter for orderDetails
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
}


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