import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class MainMenu{
    public static void main(String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException{
        Scanner scnr = new Scanner(System.in);
        System.out.println("------------------------------------------------------");
        System.out.println("Welcome to Northside Uncommons Management of Apartments (NUMA)!\n");
        System.out.println("Before using our system, you have to login first.\n");
        System.out.println("Please choose whether you want to login as a tenant (or prospective) or a manager.");

        int choice;

        do{
            System.out.println("1. Property Manager");
            System.out.println("2. Tenant");
            System.out.println("3. NUMA Manager");
            System.out.println("4. Business Manager");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            // validating entered year
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
                    break;
                case 5:
                    System.out.println("Thank you for using NUMA!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
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
                isSuccessfullLogin = true;
                switch(choice){
                    case 1:
                        System.out.println("Property Manager menu is not implemented yet.");
                        break;
                    case 2:
                        System.out.println("Tenant menu is not implemented yet.");
                        break;
                    case 3:
                        System.out.println("NUMA Manager menu is not implemented yet.");
                        break;
                    case 4:
                        System.out.println("Business Manager menu is not implemented yet.");
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