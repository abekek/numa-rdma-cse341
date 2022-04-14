import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class MainMenu{
    public static void main(String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException{
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to Northside Uncommons Management of Apartments (NUMA)!");
        System.out.println("Before using our system, you have to login first.");
        System.out.println("Please choose whether you want to login as a tenant (or prospective) or a manager.");

        int choice;

        do{
            System.out.println("1. Tenant");
            System.out.println("2. Manager");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            // validating entered year
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.");
                scnr.next();
                continue;
            } else {
                choice = scnr.nextInt();
            }

            switch(choice){
                case 1:
                case 2:
                    break;
                case 3:
                    System.out.println("Thank you for using NUMA!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            break;
        } while(true);

        System.out.println("Now, please enter your username and password.");

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
                switch(choice){
                    case 1:
                        // TenantMenu.tenantMenu(con, s, userID);
                        System.out.println("Tenant menu is not implemented yet.");
                        isSuccessfullLogin = true;
                        break;
                    case 2:
                        // ManagerMenu.managerMenu(con, s, userID);
                        System.out.println("Manager menu is not implemented yet.");
                        isSuccessfullLogin = true;
                        break;
                }
            } catch(SQLException e){
                System.out.printf("Please, try again. Error: %s.\n", e.getMessage());
                System.out.print("enter your user id: ");
                userID = scnr.next();
                System.out.printf("enter your password for %s: ", userID);
                password = scnr.next();
            }
        } while(!isSuccessfullLogin);
    }
}