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
                    String propertyId = "";
                    String query = "";

                    ResultSet rs = s.executeQuery("select count(*) from property");
                    rs.next();
                    int numProperties = rs.getInt(1);

                    query = String.format("select distinct get_min_prop_id() from property");
                    rs = s.executeQuery(query);
                    rs.next();
                    String min_propertyId = rs.getString(1);
            
                    do{
                        System.out.printf("\nPlease enter the property ID (Hint: there are %d properties starting with index %s): ", numProperties, min_propertyId);
                        propertyId = scnr.next();
                        try{
                            query = String.format("select * from property where prop_id='%s'", propertyId);
                            rs = s.executeQuery(query);
                            rs.next();
                            rs.getString("prop_id");
                        } catch(SQLException e){
                            System.out.println("Property ID not found.");
                            continue;
                        }
                        break;
                    } while(true);

                    editProperty(con, s, scnr, propertyId);
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

    public static void editProperty(Connection con, Statement s, Scanner scnr, String propertyId){
        int choice = -1;
        do{
            System.out.println("\nWhat would you like to edit?");
            System.out.println("1. Property Name");
            System.out.println("2. Property Address");
            System.out.println("3. Property City");
            System.out.println("4. Property State");
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
                    editPropertyName(con, s, scnr, propertyId);
                    break;
                case 2:
                    editPropertyAddress(con, s, scnr, propertyId);
                    break;
                case 3:
                    editPropertyCity(con, s, scnr, propertyId);
                    break;
                case 4:
                    editPropertyState(con, s, scnr, propertyId);
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

    public static void editPropertyCity(Connection con, Statement s, Scanner scnr, String propertyId){
        String query = String.format("select * from property where prop_id='%s'", propertyId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate City");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current City: " + rs.getString("city"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
        
        String city = "";
        System.out.print("\nPlease enter the city (or q to exit): ");
        scnr.nextLine();
        city = scnr.nextLine();

        if(city.equals("q")){
            return;
        }

        query = "update property set city=? where prop_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, city);
            ps.setString(2, propertyId);
            ps.executeUpdate();
            System.out.println("City updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating city. "+ e.getMessage());
        }
    }

    public static void editPropertyState(Connection con, Statement s, Scanner scnr, String propertyId){
        String query = String.format("select * from property where prop_id='%s'", propertyId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate State");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current State: " + rs.getString("state"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
        
        String[] states = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC",  
        "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA",  
        "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",  
        "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC",  
        "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};

        int choiceState = -1;
        do {
            System.out.println("\nPlease, choose your state.");
            int i = 0;
            for(String state : states){
                System.out.printf("%d. %s\n", i+1, state);
                i++;
            }
            System.out.printf("%d. Exit\n", states.length+1);

            System.out.print("Enter your choice: ");
            
            // validating entered choice
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.\n");
                scnr.next();
                continue;
            } else {
                choiceState = scnr.nextInt();
            }

            if(choiceState > states.length+1 || choiceState < 1){
                System.out.println("Invalid choice. Please try again.\n");
                continue;
            }

            if(choiceState == states.length+1){
                System.out.println("Exiting to menu.");
                break;
            }

            String state = states[choiceState-1];
            query = String.format("update property set state='%s' where prop_id='%s'", state, propertyId);
            try {
                s.executeUpdate(query);
                System.out.println("State updated successfully.");
                break;
            } catch (SQLException e) {
                System.out.println("Error while updating state. "+ e.getMessage());
            }
        } while(choiceState != states.length+1);
    }

    public static void editPropertyName(Connection con, Statement s, Scanner scnr, String propertyId){
        String query = String.format("select * from property where prop_id='%s'", propertyId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Property Name");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
        
        String name = "";
        System.out.print("\nPlease enter the property name (or q to exit): ");
        scnr.nextLine();
        name = scnr.nextLine();

        if(name.equals("q")){
            return;
        }

        query = "update property set name=? where prop_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, propertyId);
            ps.executeUpdate();
            System.out.println("Name updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating name. "+ e.getMessage());
        }
    }

    public static void editPropertyAddress(Connection con, Statement s, Scanner scnr, String propertyId){
        String query = String.format("select * from property where prop_id='%s'", propertyId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Address");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current address: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
        
        String address = "";
        System.out.print("\nPlease enter the address (or q to exit): ");
        scnr.nextLine();
        address = scnr.nextLine();

        if(address.equals("q")){
            return;
        }

        query = "update property set address=? where prop_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, address);
            ps.setString(2, propertyId);
            ps.executeUpdate();
            System.out.println("Address updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating address. "+ e.getMessage());
        }
    }

    public static void viewProperty(Connection con, Statement s, Scanner scnr){
        String query = "select * from property";
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nProperties Table");
            System.out.println("==================");
            System.out.println("Property ID\tName\t\tAddress\t\t\t\tState\t\tCity\t\t\tTotal Apartments\tAvailable Apartments\tAvailable Amenities");
            System.out.println("===========================================================================================================================================================================");
            while(rs.next()){
                System.out.printf("%-10s\t%-10s\t%-30s\t%-10s\t%-20s\t%-20s\t%-20s\t%s\n", rs.getString("prop_id"), rs.getString("name"), rs.getString("address"), rs.getString("state"), rs.getString("city"), rs.getString("apt_total"), rs.getString("apt_available"), rs.getString("amnt_available"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
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

                System.out.println("\nWhat is the minimum fee for the amenities?");
                int minFee = -1;
                do{
                    System.out.print("Enter the minimum fee: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        minFee = scnr.nextInt();
                        break;
                    }
                } while(minFee <= 0);

                System.out.println("\nWhat is the maximum fee for the amenities?");
                int maxFee = -1;
                do{
                    System.out.print("Enter the maximum fee: $");
                    if (!scnr.hasNextInt()){
                        System.out.println("Please input an integer.\n");
                        scnr.next();
                        continue;
                    } else {
                        maxFee = scnr.nextInt();
                        break;
                    }
                } while(maxFee <= 0);

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
                System.out.println("\nApartments have been randomly populated.\n");

                query = String.format("select distinct get_max_amnt_id() from amenity");
                rs = s.executeQuery(query);
                rs.next();
                int amnt_id = rs.getInt(1) + 1;

                cs = con.prepareCall("{call populate_amenities(?, ?, ?, ?, ?)}");
                cs.setInt(1, amnt_id);
                cs.setInt(2, numFloors);
                cs.setInt(3, minFee);
                cs.setInt(4, maxFee);
                cs.setInt(5, prop_id);
                cs.execute();
                System.out.println("Amenities have been randomly populated.\n");

                cs.close();
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