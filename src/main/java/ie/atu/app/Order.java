package ie.atu.app;

import java.sql.*;


// class for handling orders
public class Order implements CRUD {
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

