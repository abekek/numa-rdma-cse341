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
                    recordLeaseData(con, s, scnr);
                    break;
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

    public static void recordMbmLease(Connection con, Statement s, Scanner scnr, String newLeaseId){
        int noticePeriod = -1;

        do {
            System.out.println("\nWhat is the notice period for this month-by-month lease?");
            System.out.print("Enter the number of days: ");

            // validating entered choice
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.\n");
                scnr.next();
                continue;
            } else {
                noticePeriod = scnr.nextInt();
                break;
            }
        } while(true);

        String query = String.format("INSERT INTO mbm_lease (lease_id, notice_period) VALUES ('%s', %s)", newLeaseId, Integer.toString(noticePeriod));
        
        try {
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void recordFixedLease(Connection con, Statement s, Scanner scnr, String newLeaseId){
        int term = -1;

        do {
            System.out.println("\nWhat is the term for this fixed lease?");
            System.out.print("Enter the number of terms: ");

            // validating entered choice
            if (!scnr.hasNextInt()){
                System.out.println("Please input an integer.\n");
                scnr.next();
                continue;
            } else {
                term = scnr.nextInt();
                break;
            }
        } while(true);

        String query = String.format("INSERT INTO fixed_lease (lease_id, term) VALUES ('%s', %s)", newLeaseId, Integer.toString(term));
        
        try {
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void recordLeaseData(Connection con, Statement s, Scanner scnr){
        String query = String.format("select distinct get_max_lease_id() from lease");

        int choice = -1;
        String newLeaseId = "";
        String customerId = "";
        String tenantId = "";
        String dateSigned = "";
        String pet = "";
        String people = "";
        Double rate = 0.0;

        System.out.print("\nPlease enter the tenant ID: ");
        tenantId = scnr.next();

        try{
            ResultSet rs = s.executeQuery(query);
            rs.next();
            newLeaseId = rs.getString(1);
            newLeaseId = Integer.parseInt(newLeaseId) + 1 + "";

            boolean dateIsIncorrect = true;
            String month = "";
            String day = "";
            String year = "";

            do{
                System.out.println("\nPlease enter the date lease was signed:");
                try{
                    System.out.print("Enter the month: ");
                    month = scnr.next();
                    if(Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12){
                        System.out.println("Invalid month. Please try again.\n");
                        continue;
                    }
                    System.out.print("Enter the day: ");
                    day = scnr.next();
                    // TODO: check for February/leap year
                    if(Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31){
                        System.out.println("Invalid day. Please try again.\n");
                        continue;
                    }
                    System.out.print("Enter the year: ");
                    year = scnr.next();
                    if(Integer.parseInt(year) < 2020 || Integer.parseInt(year) > 2022){
                        System.out.println("Invalid year. Please try again.\n");
                        continue;
                    }
                    dateIsIncorrect = false;
                } catch(Exception e){
                    System.out.println("Please enter a valid date.\n");
                    continue;
                }
                
            } while(dateIsIncorrect);

            dateSigned = month + "/" + day + "/" + year;

            int petChoice = -1;

            do{
                System.out.println("\nDo you want to add any pets?");
                System.out.println("1. Yes");
                System.out.println("2. No");

                System.out.print("Enter your choice: ");

                // validating entered choice
                if (!scnr.hasNextInt()){
                    System.out.println("Please input an integer.\n");
                    scnr.next();
                    continue;
                } else {
                    petChoice = scnr.nextInt();
                }

                switch(petChoice){
                    case 1:
                        pet = recordPet(con, s, scnr);
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                        continue;
                }
            } while(petChoice != 2 && petChoice != 1);

            int peopleChoice = -1;

            do{
                System.out.println("\nDo you want to add any people?");
                System.out.println("1. Yes");
                System.out.println("2. No");

                System.out.print("Enter your choice: ");

                // validating entered choice
                if (!scnr.hasNextInt()){
                    System.out.println("Please input an integer.\n");
                    scnr.next();
                    continue;
                } else {
                    peopleChoice = scnr.nextInt();
                }

                switch(peopleChoice){
                    case 1:
                        people = recordPeople(con, s, scnr);
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                        continue;
                }
            } while(peopleChoice != 2 && peopleChoice != 1);

            do {
                System.out.println("\nPlease enter the rate of the lease:");
                System.out.print("Enter the rate: ");

                // validating entered choice
                if (!scnr.hasNextDouble()){
                    System.out.println("Please input a double.\n");
                    scnr.next();
                    continue;
                } else {
                    rate = scnr.nextDouble();
                    break;
                }
            } while(true);

            do {
                System.out.println("\nPlease choose the type of lease: ");
                System.out.println("1. Month-by-Month Lease");
                System.out.println("2. Fixed Lease");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
    
                // validating entered choice
                if (!scnr.hasNextInt()){
                    System.out.println("Please input an integer.\n");
                    scnr.next();
                    continue;
                } else {
                    choice = scnr.nextInt();
                    break;
                }
            } while(true);
    
            switch(choice){
                case 1:
                    query = String.format("insert into lease values('%s', '%s', '%s', '%s', '%s', '', '%s')", newLeaseId, tenantId, dateSigned, pet, people, Double.toString(rate));
                    s.executeUpdate(query);
                    recordMbmLease(con, s, scnr, newLeaseId);
                    System.out.println("\nLease successfully recorded.\n");
                    break;
                case 2:
                    query = String.format("insert into lease values('%s', '%s', '%s', '%s', '%s', '', '%s')", newLeaseId, tenantId, dateSigned, pet, people, Double.toString(rate));
                    s.executeUpdate(query);
                    recordFixedLease(con, s, scnr, newLeaseId);
                    System.out.println("\nLease successfully recorded.\n");
                    break;
                case 3:
                    System.out.println("Exiting to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String recordPeople(Connection con, Statement s, Scanner scnr){
        String people = "";
        System.out.print("\nPlease enter the people names separating them with a comma: ");
        scnr.nextLine();
        people = scnr.nextLine();
        return people;
    }

    public static String recordPet(Connection con, Statement s, Scanner scnr){
        String pet = "";
        System.out.print("\nPlease enter the pet names separating them with a comma: ");
        scnr.nextLine();
        pet = scnr.nextLine();
        return pet;
    }

    public static void recordVisitData(Connection con, Statement s, Scanner scnr){
        String query = String.format("select distinct get_max_visit_id() from visit_data");

        String newVisitId = "";
        String customerId = "";

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
        } catch(SQLException e){
            System.out.print("Error: " + e.getMessage());
        }
    }
}