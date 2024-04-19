package ie.atu.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubwayCRUD {

    public static void main(String[] args) {
        SubwayCRUD subwayCRUD = new SubwayCRUD();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Subway CRUD Application");
            System.out.println("1. Create Order"); // create order by asking for new customer id, name, then date, then price, then what sandwich they ordered
            System.out.println("2. View Order"); // view order by asking for customer id
            System.out.println("3. Update Order"); // ask what customer id and what they want updated
            System.out.println("4. Delete Order"); // delete whole order
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                case 2:

                case 3:

                case 4:

                case 5:
                    System.out.println("Exiting application.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}
