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
                    viewPersonalData(con, s, scnr, "1");
                    break;
                case 2:
                    viewApartmentData(con, s, scnr, "1");
                    break;
                case 3:
                case 4:
                case 5:
                    addPersonPet(con, s, scnr, "1");
                    break;
                case 6:
                    setMoveoutDate(con, s, scnr, "1");
                    break;
                case 7:
                    updatePersonalData(con, s, scnr, "1");
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

    public static void addPersonPet(Connection con, Statement s, Scanner scnr, String tenantId){

    }

    public static void setMoveoutDate(Connection con, Statement s, Scanner scnr, String tenantId){

    }

    public static void viewApartmentData(Connection con, Statement s, Scanner scnr, String tenantId) throws SQLException{
        String query = String.format("select * from (select * from (tenant natural join lease natural join apartment)) A inner join property B on A.prop_id=B.prop_id where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nApartment & Property Information");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Property Name: " + rs.getString("name"));
                System.out.println("Property Address: " + rs.getString(28));
                System.out.println("Property State: " + rs.getString(29));
                System.out.println("Property City: " + rs.getString(30));
                System.out.println("Apartment Number: " + rs.getString("apt_num"));
                System.out.println("Monthly Rent: " + rs.getString("monthly_rent"));
                System.out.println("Security Deposit: " + rs.getString("security_dep"));
                System.out.println("Area: " + rs.getString("area"));
                System.out.println("Bedrooms Number: " + rs.getString("bdrm_num"));
                System.out.println("Bathrooms Number: " + rs.getString("bthrm_num"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
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
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }
    }

    public static void updatePersonalData(Connection con, Statement s, Scanner scnr, String tenantId) throws SQLException{
        int choice = -1;
        do{
            System.out.println("\nPlease, choose what you want to update.");

            List<String> tenantAttrs = new ArrayList<>();
            
            try {
                ResultSet rs = s.executeQuery(String.format("select * from person natural join customer natural join tenant where id='%s'" , tenantId));
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numCols = rsMetaData.getColumnCount();
                for (int colNum = 1; colNum <= numCols; colNum++) {
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
            System.out.printf("%d. Exit\n", 16);

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
                    updateName(con, s, scnr, tenantId, tenantAttrs.get(choice));
                    break;
                case 4:
                case 5:
                case 6:
                    updateSSN(con, s, scnr, tenantId);
                    break;
                case 8:
                    updateCountry(con, s, scnr, tenantId);
                    break;
                case 9:
                    updateState(con, s, scnr, tenantId);
                    break;
                case 10:
                    updateCity(con, s, scnr, tenantId);
                    break;
                case 11:
                    updateAccount(con, s, scnr, tenantId);
                    break;
                case 12:
                    updateOccupation(con, s, scnr, tenantId);
                    break;
                case 13:
                    updateCompany(con, s, scnr, tenantId);
                    break;
                case 14:
                    updateSalary(con, s, scnr, tenantId);
                    break;
                case 15:
                    updateNumDependent(con, s, scnr, tenantId);
                    break;
                case 7:
                    updateAddress(con, s, scnr, tenantId);
                    break;
                case 16:
                    System.out.println("Exiting to tenant menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
            }
        } while(choice != 16);
    }

    public static void updateName(Connection con, Statement s, Scanner scnr, String tenantId, String attr){
        String query = String.format("select * from person where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.printf("\nUpdate %s\n", attr);
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current " + attr + ": " + rs.getString(attr));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String name = "";
        System.out.print("\nPlease enter the name (or q to exit): ");
        scnr.nextLine();
        name = scnr.nextLine();

        if(name.equals("q")){
            return;
        }

        query = String.format("update person set %s=? where id=?", attr);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("Name updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating the name. "+ e.getMessage());
        }
    }

    public static void updateNumDependent(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select num_dependent from tenant where id='%s'", tenantId);
        String numDependent = "";
        try {
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                numDependent = rs.getString("num_dependent");
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        System.out.printf("Current number of dependents: %s\n", numDependent);
        int newNumDep = 0;

        do{
            System.out.print("\nEnter new number of dependents: ");
            if (!scnr.hasNextInt()){
                System.out.println("Please input a number.\n");
                scnr.next();
                continue;
            } else {
                newNumDep = scnr.nextInt();
                break;
            }
        } while(true);

        query = String.format("update tenant set num_dependent='%s' where id='%s'", Integer.toString(newNumDep), tenantId);
        try {
            s.executeUpdate(query);
            System.out.println("Number of dependents updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating number of dependents. "+ e.getMessage());
        }
    }

    public static void updateSalary(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select salary from tenant where id='%s'", tenantId);
        String salary = "";
        try {
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                salary = rs.getString("salary");
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        System.out.printf("Current salary: %s\n", salary);
        double newSalary = 0.0;

        do{
            System.out.print("\nEnter new salary: ");
            if (!scnr.hasNextDouble()){
                System.out.println("Please input a number.\n");
                scnr.next();
                continue;
            } else {
                newSalary = scnr.nextDouble();
                break;
            }
        } while(true);

        query = String.format("update tenant set salary='%s' where id='%s'", String.format("%.2f", newSalary), tenantId);
        try {
            s.executeUpdate(query);
            System.out.println("Salary updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating salary. "+ e.getMessage());
        }
    }

    public static void updateAccount(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Account");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current Account: " + rs.getString("account"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String newAccount = "";
        String ssnAccount = "^[0-9]*$";

        do{
            System.out.print("\nEnter new account number (or q to exit): ");
            newAccount = scnr.next();
            if(newAccount.equals("q")){
                System.out.println("Exiting.");
                return;
            }
            if(!newAccount.matches(ssnAccount)){
                System.out.println("Invalid account number. Please try again.");
                continue;
            }
        } while(!newAccount.matches(ssnAccount));

        String updateQuery = String.format("update tenant set account='%s' where id='%s'", newAccount, tenantId);
        try {
            s.executeUpdate(updateQuery);
            System.out.println("Account number updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating account number. "+ e.getMessage());
        }
    }

    public static void updateCity(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
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

        query = "update tenant set city=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, city);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("City updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating city. "+ e.getMessage());
        }
    }

    public static void updateState(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
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
                System.out.println("Exiting to tenant menu.");
                break;
            }

            String state = states[choiceState-1];
            query = String.format("update tenant set state='%s' where id='%s'", state, tenantId);
            try {
                s.executeUpdate(query);
                System.out.println("State updated successfully.");
                break;
            } catch (SQLException e) {
                System.out.println("Error while updating state. "+ e.getMessage());
            }
        } while(choiceState != states.length+1);
    }

    public static void updateCompany(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Company");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current Company: " + rs.getString("company"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String company = "";
        System.out.print("\nPlease enter the company (or q to exit): ");
        scnr.nextLine();
        company = scnr.nextLine();

        if(company.equals("q")){
            return;
        }

        query = "update tenant set company=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, company);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("Company updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating company. "+ e.getMessage());
        }
    }

    public static void updateOccupation(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Occupation");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current Occupation: " + rs.getString("occupation"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String occupation = "";
        System.out.print("\nPlease enter the occupation (or q to exit): ");
        scnr.nextLine();
        occupation = scnr.nextLine();

        if(occupation.equals("q")){
            return;
        }

        query = "update tenant set occupation=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, occupation);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("Occupation updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating occupation. "+ e.getMessage());
        }
    }

    public static void updateCountry(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Country");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current Country: " + rs.getString("country"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String country = "";
        System.out.print("\nPlease enter the country name (or q to exit): ");
        scnr.nextLine();
        country = scnr.nextLine();

        if(country.equals("q")){
            return;
        }

        query = "update tenant set country=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, country);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("Country updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating country. "+ e.getMessage());
        }
    }

    public static void updateAddress(Connection con, Statement s, Scanner scnr, String tenantId){
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate Address");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current Address: " + rs.getString("address"));
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

        query = "update tenant set address=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, address);
            ps.setString(2, tenantId);
            ps.executeUpdate();
            System.out.println("Address updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating address. "+ e.getMessage());
        }
    }

    public static void updateSSN(Connection con, Statement s, Scanner scnr, String tenantId) throws SQLException{
        String query = String.format("select * from tenant where id='%s'", tenantId);
        try {
            ResultSet rs = s.executeQuery(query);
            System.out.println("\nUpdate SSN");
            System.out.println("====================");
            while(rs.next()){
                System.out.println("Current SSN: " + rs.getString("ssn"));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting column names. "+ e.getMessage());
        }

        String newSSN = "";
        String ssnRegex = "^(?!666|000|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0{4})\\d{4}$";

        do{
            System.out.print("\nEnter new SSN (or q to exit): ");
            newSSN = scnr.next();
            if(newSSN.equals("q")){
                System.out.println("Exiting.");
                return;
            }
            if(!newSSN.matches(ssnRegex)){
                System.out.println("Invalid SSN. Please try again.");
                continue;
            }
        } while(!newSSN.matches(ssnRegex));

        String updateQuery = String.format("update tenant set ssn='%s' where id='%s'", newSSN, tenantId);
        try {
            s.executeUpdate(updateQuery);
            System.out.println("SSN updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating SSN. "+ e.getMessage());
        }
    }
}