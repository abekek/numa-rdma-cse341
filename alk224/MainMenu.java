import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class MainMenu{
    public static void main(String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException{
        Scanner scnr = new Scanner(System.in);
        System.out.println("------------------------------------------------------");
        System.out.println("Welcome to Northside Uncommons Management of Apartments (NUMA)!\n");
        System.out.println("Before using our system, you have to login first.");

        System.out.println("Please enter your username and password.");

        // requesting user's credentials
        System.out.print("enter your user id: ");
        String userID = scnr.next();
        System.out.printf("enter your password for %s: ", userID);
        String password = scnr.next();

        boolean isSuccessfullLogin = false;

        do{
            try (
                // Connect to Oracle
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", userID, password);
                Statement s = con.createStatement();
            ){
                isSuccessfullLogin = true;
                startMainMenu(con, s, scnr);
                con.close();
                s.close();
            } catch(SQLException e){
                System.out.printf("Please, try again. Error: %s.\n", e.getMessage());
                System.out.print("enter your user id: ");
                userID = scnr.next();
                System.out.printf("enter your password for %s: ", userID);
                password = scnr.next();
            }
        } while(!isSuccessfullLogin);
        scnr.close();
    }

    public static void startMainMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;

        do{
            System.out.println("\nPlease choose whether you want to login as a tenant (or prospective) or a manager.");
            System.out.println("1. Property Manager");
            System.out.println("2. Tenant");
            System.out.println("3. NUMA Manager");
            System.out.println("4. Business Manager");
            System.out.println("5. Exit");
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
                    // startPropMngMenu(con, s, scnr);
                    PropMngMenu.startMenu();
                    break;
                case 2:
                    startTenantMenu(con, s, scnr);
                    break;
                case 3:
                    startNUMAMngrMenu(con, s, scnr);
                    break;
                case 4:
                    startBusMngrMenu(con, s, scnr);
                    break;
                case 5:
                    System.out.println("\nThank you for using NUMA!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 5);   
    }

    public static void startPropMngMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
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

    public static void startTenantMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
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
        
    }

    public static void registerTenant(Connection con, Statement s, Scanner scnr) throws SQLException{

    }

    public static void startOldTenantMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Check Payment Status");
            System.out.println("2. Make Rental Payment");
            System.out.println("3. Add a Person/Pet");
            System.out.println("4. Set Move-out Date");
            System.out.println("5. Update Personal Data");
            System.out.println("6. Exit");
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
                case 2:
                case 3:
                case 4:
                case 5:
                    updatePersonalData(con, s, scnr);
                    break;
                case 6:
                    System.out.println("Exiting to tenant menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 6);
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

    public static void startNUMAMngrMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add a New Property");
            System.out.println("2. Exit");
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
                    break;
                case 2:
                    System.out.println("Exiting to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 2);
    }

    public static void startBusMngrMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        System.out.println("Business Manager menu is not implemented yet.");
    }
}