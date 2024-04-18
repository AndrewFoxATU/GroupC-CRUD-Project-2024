package ie.atu.app;

import ie.atu.app.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetails implements CRUD {
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

