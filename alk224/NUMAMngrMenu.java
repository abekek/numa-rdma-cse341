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
            System.out.println("4. View a List of Properties");
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
                    addProperty(con, s, scnr);
                    break;
                case 2:
                    removeProperty(con, s, scnr);
                    break;
                case 3:
                    editProperty(con, s, scnr);
                    break;
                case 4:
                    viewProperty(con, s, scnr);
                    break;
                case 5:
                    System.out.println("Exiting to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 5);
    }

    public static void editProperty(Connection con, Statement s, Scanner scnr){

    }

    public static void viewProperty(Connection con, Statement s, Scanner scnr){

    }

    public static void addProperty(Connection con, Statement s, Scanner scnr){
        System.out.println("\nAdding a New Property");
        System.out.println("====================");
        System.out.printf("\nAre you sure you want to add new property? (y/n): ");
        String confirm = scnr.next();
        String query = "";

        if(confirm.equals("y")){
            try{
                System.out.println("\nHow many apartments would the property have?");
                int numApartments = -1;
                do{
                    System.out.print("Enter the number of apartments: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        numApartments = scnr.nextInt();
                        break;
                    }
                } while(numApartments <= 0);
                
                System.out.println("\nHow many amenities would the property have?");
                int numAmenities = -1;
                do{
                    System.out.print("Enter the number of amenities: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        numAmenities = scnr.nextInt();
                        break;
                    }
                } while(numAmenities <= 0);

                System.out.println("\nHow many floors would the property have?");
                int numFloors = -1;
                do{
                    System.out.print("Enter the number of floors: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        numFloors = scnr.nextInt();
                        break;
                    }
                } while(numFloors <= 0);

                System.out.println("\nWhat is the minimum rent of the apartment?");
                int minRent = -1;
                do{
                    System.out.print("Enter the minimum rent: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minRent = scnr.nextInt();
                        break;
                    }
                } while(minRent <= 0);

                System.out.println("\nWhat is the maximum rent of the apartment?");
                int maxRent = -1;
                do{
                    System.out.print("Enter the maximum rent: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxRent = scnr.nextInt();
                        break;
                    }
                } while(maxRent <= 0);

                System.out.println("\nWhat is the minimum secutiry deposit of the apartment?");
                int minDeposit = -1;
                do{
                    System.out.print("Enter the minimum security deposit: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minDeposit = scnr.nextInt();
                        break;
                    }
                } while(minDeposit <= 0);

                System.out.println("\nWhat is the maximum security deposit of the apartment?");
                int maxDeposit = -1;
                do{
                    System.out.print("Enter the maximum security deposit: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxDeposit = scnr.nextInt();
                        break;
                    }
                } while(maxDeposit <= 0);

                System.out.println("\nWhat is the minimum number of bedrooms of the apartment?");
                int minBedrooms = -1;
                do{
                    System.out.print("Enter the minimum number of bedrooms: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minBedrooms = scnr.nextInt();
                        break;
                    }
                } while(minBedrooms <= 0);

                System.out.println("\nWhat is the maximum number of bedrooms of the apartment?");
                int maxBedrooms = -1;
                do{
                    System.out.print("Enter the maximum number of bedrooms: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxBedrooms = scnr.nextInt();
                        break;
                    }
                } while(maxBedrooms <= 0);

                System.out.println("\nWhat is the minimum number of bathrooms of the apartment?");
                int minBathrooms = -1;
                do{
                    System.out.print("Enter the minimum number of bathrooms: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minBathrooms = scnr.nextInt();
                        break;
                    }
                } while(minBathrooms <= 0);

                System.out.println("\nWhat is the maximum number of bathrooms of the apartment?");
                int maxBathrooms = -1;
                do{
                    System.out.print("Enter the maximum number of bathrooms: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxBathrooms = scnr.nextInt();
                        break;
                    }
                } while(maxBathrooms <= 0);

                System.out.println("\nWhat is the minimum square foot area of the apartment?");
                int minArea = -1;
                do{
                    System.out.print("Enter the minimum square foot area: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minArea = scnr.nextInt();
                        break;
                    }
                } while(minArea <= 0);

                System.out.println("\nWhat is the maximum square foot area of the apartment?");
                int maxArea = -1;
                do{
                    System.out.print("Enter the maximum square foot area: ");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxArea = scnr.nextInt();
                        break;
                    }
                } while(maxArea <= 0);

                query = "select distinct get_max_prop_id() from property";
                ResultSet rs = s.executeQuery(query);
                rs.next();
                int prop_id = rs.getInt(1) + 1;
                query = String.format("insert into property (prop_id, name, address, state, city, apt_total, apt_available, amnt_available) values (%d, '', '', '', '', %d, %d, %d)", prop_id, numApartments, numApartments, numAmenities);
                s.executeUpdate(query);
                System.out.printf("\nNew property has been added.\n");
                query = "select distinct get_max_prop_id() from property";
                rs = s.executeQuery(query);
                rs.next();
                prop_id = rs.getInt(1);
                System.out.println("Property ID: " + prop_id);
                CallableStatement cs = con.prepareCall("{call populate_apartments(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                cs.setInt(1, prop_id);
                cs.setInt(2, minRent);
                cs.setInt(3, maxRent);
                cs.setInt(4, minDeposit);
                cs.setInt(5, maxDeposit);
                cs.setInt(6, minArea);
                cs.setInt(7, maxArea);
                cs.setInt(8, minBedrooms);
                cs.setInt(9, maxBedrooms);
                cs.setInt(10, minBathrooms);
                cs.setInt(11, maxBathrooms);
                cs.execute();
                // query = String.format("execute populate_apartments('%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d');", prop_id, minRent, maxRent, minDeposit, maxDeposit, minArea, maxArea, minBedrooms, maxBedrooms, minBathrooms, maxBathrooms);
                // s.executeQuery(query);
                System.out.println("\nApartments have been randomly populated.\n");
            } catch(SQLException e){
                System.out.println("\nError adding property/populating apartments." + e.getMessage());
            }
        } else {
            System.out.println("\nProperty not removed.");
        }

        System.out.println("Please, be sure to update other necessary information about the property.");
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
                System.out.println("Property ID not found." + e.getMessage());
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
                System.out.println("\nError removing property." + e.getMessage());
            }
        } else {
            System.out.println("\nProperty not removed.");
        }
    }
}