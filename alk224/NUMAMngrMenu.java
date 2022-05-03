import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class NUMAMngrMenu {
    public static void startMenu(Connection con, Statement s, Scanner scnr) throws SQLException{
        int choice = -1;
        
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add a New Property");
            System.out.println("2. Remove a Property");
            System.out.println("3. Edit a Property");
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
                    addProperty(con, s, scnr);
                    break;
                case 2:
                    removeProperty(con, s, scnr);
                    break;
                case 3:
                    editProperty(con, s, scnr);
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

    public static void addProperty(Connection con, Statement s, Scanner scnr){

    }

    public static void removeProperty(Connection con, Statement s, Scanner scnr){
        System.out.println("\nWhich property would you like to remove?");
        String propertyId = "";
        String query = "";

        do{
            System.out.print("\nPlease enter the property ID: ");
            propertyId = scnr.next();
            try{
                query = String.format("select * from property where prop_id='%s'", propertyId);
                ResultSet rs = s.executeQuery(query);
                rs.next();
                rs.getString("prop_id");
            } catch(SQLException e){
                System.out.println("Property ID not found.");
                continue;
            }
            break;
        } while(true);

        System.out.printf("\nAre you sure you want to remove property %s? (y/n): ", propertyId);
        String confirm = scnr.next();

        if(confirm.equals("y")){
            try{
                query = String.format("delete from property where prop_id='%s'", propertyId);
                s.executeUpdate(query);
                System.out.printf("\nProperty %s has been removed.\n", propertyId);
            } catch(SQLException e){
                System.out.println("\nError removing property.");
            }
        } else {
            System.out.println("\nProperty not removed.");
        }
    }

    public static void editProperty(Connection con, Statement s, Scanner scnr){

    }
}