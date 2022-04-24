import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class TenantMenu{
    public static void startMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;

        do{
            System.out.println("\nAre you a new tenant?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("3. Exit");
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
                    registerTenant(con, s, scnr);
                    break;
                case 2:
                    loginTenant(con, s, scnr);
                    break;
                case 3:
                    System.out.println("Exiting to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 3);
    }

    public static void loginTenant(Connection con, Statement s, Scanner scnr) throws SQLException{
        // TODO: implement login
    }

    public static void registerTenant(Connection con, Statement s, Scanner scnr) throws SQLException{
        // TODO: implement registration
    }

    public static void startOldTenantMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. View your personal information");
            System.out.println("2. View your apartment information");
            System.out.println("3. Check Payment Status");
            System.out.println("4. Make Rental Payment");
            System.out.println("5. Add a Person/Pet");
            System.out.println("6. Set Move-out Date");
            System.out.println("7. Update Personal Data");
            System.out.println("8. Exit");
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
                    viewPersonalData(con, s, scnr, "66-7451013");
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    updatePersonalData(con, s, scnr);
                    break;
                case 8:
                    System.out.println("Exiting to tenant menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 8);
    }

    public static void viewPersonalData(Connection con, Statement s, Scanner scnr, String tenantId) throws SQLException{
        String query = String.format("select * from tenant natural join person natural join customer where id='%s'", tenantId);
        
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nPersonal Information");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("SSN: " + rs.getString("ssn"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("Middle Name: " + rs.getString("middle_name"));
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Phone Number: " + rs.getString("phone"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Country: " + rs.getString("country"));
                System.out.println("State: " + rs.getString("state"));
                System.out.println("City: " + rs.getString("city"));
                System.out.println("Account: " + rs.getString("account"));
                System.out.println("Occupation: " + rs.getString("occupation"));
                System.out.println("Company: " + rs.getString("company"));
                System.out.println("Salary: " + rs.getString("salary"));
                System.out.println("Number of dependents: " + rs.getString("num_dependent"));
                System.out.println("Lease ID: " + rs.getString("lease_id"));
            }

        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

    }

    public static void updatePersonalData(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        do{
            System.out.println("\nPlease, choose what you want to update.");

            List<String> tenantAttrs = new ArrayList<>();
            
            try {
                ResultSet rs = s.executeQuery("select * from tenant");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numCols = rsMetaData.getColumnCount();
                for (int colNum = 1; colNum < numCols; colNum++) {
                    tenantAttrs.add(rsMetaData.getColumnLabel(colNum));
                }
            } catch (SQLException e) {
                System.out.println("Error while getting column names. "+ e.getMessage());
            }

            int i = 0;
            for(String attr : tenantAttrs){
                if(attr.equals("ID") || attr.equals("LEASE_ID")){
                    continue;
                }
                System.out.printf("%d. %s\n", i+1, attr);
                i++;
            }
            System.out.printf("%d. Exit\n", 11);

            System.out.print("Enter your choice: ");
            
            // validating entered choice
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.\n");
                scnr.next();
                continue;
            } else {
                choice = scnr.nextInt();
            }

            String attrChoice = "";

            switch(choice){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    attrChoice = tenantAttrs.get(choice);
                    break;
                case 11:
                    System.out.println("Exiting to tenant menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }

            // String query = String.format("update tenant set %s=%s where ID=%s", attrChoice);

        } while(choice != 11);
    }
}