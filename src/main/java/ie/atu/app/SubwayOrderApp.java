// SubwayCRUDApp.java
package ie.atu.app;

import ie.atu.pool.DatabaseUtils;

import java.sql.SQLException;
import java.util.Scanner;

public class SubwayOrderApp {

    public static void main(String[] args) {
        SubwayCRUD subwayCRUD = new SubwayCRUD();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Subway order App");
            System.out.println("1. Create Order"); // create order by asking for new customer id, name, then date, then price, then what sandwich they ordered
            System.out.println("2. View Order");  // view order by asking for customer id
            System.out.println("3. Update Order Price"); // ask what customer id and what they want updated
            System.out.println("4. Delete Order");  // delete whole order
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // CREATE
                    try {
                        subwayCRUD.create(DatabaseUtils.getConnection());
                    } catch (SQLException e) {
                        System.err.println("Error creating order: " + e.getMessage());
                    }
                    break;
                case 2: // READ
                    try {
                        subwayCRUD.read(DatabaseUtils.getConnection());
                    } catch (SQLException e) {
                        System.err.println("Error viewing order: " + e.getMessage());
                    }
                    break;
                case 3: // UPDATE
                    try {
                        subwayCRUD.update(DatabaseUtils.getConnection());
                    } catch (SQLException e) {
                        System.err.println("Error updating order: " + e.getMessage());
                    }
                    break;
                case 4: // DELETE
                    try {
                        subwayCRUD.delete(DatabaseUtils.getConnection());
                    } catch (SQLException e) {
                        System.err.println("Error deleting order: " + e.getMessage());
                    }
                    break;
                case 5: // EXIT
                    System.out.println("Exiting application...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}
