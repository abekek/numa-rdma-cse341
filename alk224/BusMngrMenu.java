import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class BusMngrMenu{
    public static void startMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. General statistics");
            System.out.println("2. Apartment statistics");
            System.out.println("3. Tenant statistics");
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
                    viewGeneralStatistics(con, s, scnr);
                    break;
                case 2:
                    viewApartmentStatistics(con, s, scnr);
                    break;
                case 3:
                    viewTenantStatistics(con, s, scnr);
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

    public static void viewGeneralStatistics(Connection con, Statement s, Scanner scnr){      
        System.out.println("\nViewing general statistics.\n");
        System.out.println("-----------------------------------------------------");
        String query = "select count(*) from property";
        
        try{
            ResultSet rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Number of properties: %d\n", rs.getInt(1));
            
            query = "select count(*) from lease";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Number of leases: %d\n", rs.getInt(1));
            
            query = "select count(*) from visit_data";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Number of visits: %d\n", rs.getInt(1));
            
            query = "select count(*) from tenant";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Number of tenants: %d\n", rs.getInt(1));
            
            query = "select sum(rate+parking_rate) from lease";
            rs = s.executeQuery(query);
            rs.next();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            System.out.printf("\nTotal revenue generated: $%s\n", formatter.format(rs.getDouble(1)));

            query = "select sum(amount) from payment";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("\nTotal payments received: $%s\n", formatter.format(rs.getDouble(1)));

            query = "select sum(amount) from payment where type='CREDIT'";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Credit card payments: $%s\n", formatter.format(rs.getDouble(1)));

            query = "select sum(amount) from payment where type='CASH'";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Cash payments: $%s\n", formatter.format(rs.getDouble(1)));

            query = "select sum(amount) from payment where type='CHECK'";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Check payments: $%s\n", formatter.format(rs.getDouble(1)));

        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewApartmentStatistics(Connection con, Statement s, Scanner scnr){
        System.out.println("\nViewing apartment statistics.\n");
        System.out.println("-----------------------------------------------------");

        String query = "select count(*) from apartment where lease_id is null";
        try{
            ResultSet rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Total number of available apartments: %d\n", rs.getInt(1));

            query = "select count(*) from apartment where lease_id is not null";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Total number of occupied apartments: %d\n", rs.getInt(1));
            
            query = "select sum(apt_total) from property";
            rs = s.executeQuery(query);
            rs.next();
            System.out.printf("Total number of apartments: %d\n", rs.getInt(1));
            
            query = "select sum(monthly_rent) from apartment where lease_id is null";
            rs = s.executeQuery(query);
            rs.next();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            System.out.printf("\nPossible revenue from empty apartments: $%s\n", formatter.format(rs.getDouble(1)));
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewTenantStatistics(Connection con, Statement s, Scanner scnr){
        System.out.println("\nViewing tenant statistics.\n");
        String query = "select * from tenant natural join person where salary = (select max(salary) from tenant)";

        try{
            ResultSet rs = s.executeQuery(query);
            rs.next();
            System.out.println("\nTenant with the highest salary");
            System.out.println("---------------------------------");
            System.out.printf("Tenant ID: %d\n", rs.getInt("id"));
            System.out.printf("Name: %s\n", rs.getString("last_name") + " " + rs.getString("middle_name") + " " + rs.getString("first_name"));
            System.out.printf("Occupation: %s\n", rs.getString("occupation"));
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            System.out.printf("Highest salary: $%s\n", formatter.format(rs.getDouble("salary")));

            query = "select * from tenant natural join person where salary = (select min(salary) from tenant)";
            rs = s.executeQuery(query);
            rs.next();
            System.out.println("\nTenant with the lowest salary");
            System.out.println("---------------------------------");
            System.out.printf("Tenant ID: %d\n", rs.getInt("id"));
            System.out.printf("Name: %s\n", rs.getString("last_name") + " " + rs.getString("middle_name") + " " + rs.getString("first_name"));
            System.out.printf("Occupation: %s\n", rs.getString("occupation"));
            System.out.printf("Lowest salary: $%s\n", formatter.format(rs.getDouble("salary")));
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}