import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class PropMngMenu {
    public static void startMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Record Visit Data");
            System.out.println("2. Record Lease Data");
            System.out.println("3. Record Move-out");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // validating entered choice
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.\n");
                scnr.next();
                continue;
            } else {
                choice = scnr.nextInt();
            }

            switch(choice){
                case 1:
                    recordVisitData(con, s, scnr);
                    break;
                case 2:
                case 3:
                    break;
                case 4:
                    System.out.println("Exiting to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 4);
    }

    public static void recordVisitData(Connection con, Statement s, Scanner scnr){
        String query = String.format("select distinct get_max_visit_id() from visit_data");

        String newVisitId = "";
        String customerId = "";

        do {
            System.out.print("\nPlease enter the customer ID: ");
            customerId = scnr.next();

            try{
                ResultSet rs = s.executeQuery(query);
                rs.next();
                newVisitId = rs.getString(1);
                newVisitId = Integer.parseInt(newVisitId) + 1 + "";
                System.out.println("New Visit ID: " + newVisitId);
                System.out.println("Customer ID: " + customerId);

                System.out.print("Please enter the details of the visit: ");
                scnr.nextLine();
                String details = scnr.nextLine();

                query = String.format("insert into visit_data values ('%s', '%s', '%s')", newVisitId, details, customerId);
                s.executeUpdate(query);
                break;
            } catch(SQLException e){
                System.out.println("Error: " + e.getMessage());
            }
        } while(true);
    }
}