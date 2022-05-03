import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataGen{
    public static void main(String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException{
        Scanner scnr = new Scanner(System.in);
        
        // requesting user's credentials
        System.out.print("enter Oracle user id: ");
        String userID = scnr.next();
        System.out.printf("enter Oracle password for %s: ", userID);
        String password = scnr.next();

        try (
            // Connect to Oracle
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", userID, password);
            Statement s = con.createStatement();
        ) { 
            // ------------------------------------------------------------
            // GENERATING DATA FOR TABLES: PERSON, CUSTOMER, TENANT, EMPLOYEE
            // ------------------------------------------------------------
            // file = new File("PEOPLE.csv");
            File file = new File("people_data.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] dataPerson;
            int rowNum = 0;
            List<String> tenantIds = new ArrayList<String>();

            for(int e = 0; e < 5; e++){
                double salaryEmp = Math.random() * (100000 - 10000) + 10000;
                String queryInsertEmployee = String.format("INSERT INTO EMPLOYEE (salary) VALUES ('%s')", salaryEmp);
                s.executeQuery(queryInsertEmployee);
            }

            while((line = br.readLine()) != null){
                ResultSet result;
                dataPerson = line.split(",");
                String[] attributes = new String[16];
                int i = 0;
                for(String attr: dataPerson){
                    if(attr.equals(""))
                        attr = "null";
                    attr = attr.replace("\'", " ");
                    attributes[i++] = attr;
                }

                String queryInsertPerson;
                String queryInsertCustomer;

                queryInsertPerson = String.format("INSERT INTO PERSON VALUES ('%s', '%s', '%s', '%s')", Integer.toString(rowNum+1), attributes[11], attributes[12], attributes[13]);
                queryInsertCustomer = String.format("INSERT INTO CUSTOMER VALUES ('%s', '%s', '%s')", Integer.toString(rowNum+1), attributes[14], attributes[15]);

                s.executeQuery(queryInsertPerson);
                s.executeQuery(queryInsertCustomer);

                if(rowNum >= 100){
                    String queryInsertTenant = String.format("INSERT INTO TENANT VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", Integer.toString(rowNum+1), attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6], attributes[7], attributes[8], attributes[9], attributes[10]);
                    s.executeQuery(queryInsertTenant);
                    tenantIds.add(Integer.toString(rowNum+1));
                }

                if((rowNum+1)%100 == 0)
                    System.out.printf("%d rows of people are inserted\n", rowNum+1);
                
                rowNum++;
            }

            Collections.shuffle(tenantIds);

            // ------------------------------------------------------------
            // GENERATING DATA FOR TABLES: LEASE, PAYMENT
            // ------------------------------------------------------------
            // File file = new File("LEASE.csv");
            file = new File("lease1.csv");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            line = "";

            rowNum = 0;
            String[] dataLease;
            while((line = br.readLine()) != null){
                ResultSet result;
                dataLease = line.split(",");
                String[] attributes = new String[4];
                int i = 0;
                for(String attr: dataLease){
                    if(attr.equals(""))
                        attr = "null";
                    attr = attr.replace("\'", " ");
                    attributes[i++] = attr;
                }

                String[] paymentTypes = {"CASH", "CHECK", "CREDIT"};
                double paymentAmount = Math.random()*(50000)+100;
                double parkingPayment = Math.random()*(1000)+100;
                String queryInsertLease = String.format("INSERT INTO LEASE VALUES ('%s', '%s', '%s', '%s', '', '', '%s', '%s')", attributes[0], tenantIds.get(rowNum), attributes[1], attributes[2], Double.toString(paymentAmount), Double.toString(parkingPayment));
                s.executeUpdate(queryInsertLease);

                String queryInsertPayment = String.format("INSERT INTO PAYMENT VALUES('%s', '%s', '%s', '%s')", attributes[3], Double.toString(paymentAmount + parkingPayment), paymentTypes[(int)(Math.random()*3)], attributes[0]);
                s.executeUpdate(queryInsertPayment);

                if(rowNum%2==0){
                    String queryInsertMbmLease = String.format("INSERT INTO MBM_LEASE VALUES('%s', '%s')", attributes[0], (int)(Math.random()*25+5));
                    s.executeUpdate(queryInsertMbmLease);
                }
                else{
                    String queryInsertFixedLease = String.format("INSERT INTO FIXED_LEASE VALUES('%s', '%s')", attributes[0], (int)(Math.random()*2+1));
                    s.executeUpdate(queryInsertFixedLease);
                }

                if((rowNum+1)%100 == 0)
                    System.out.printf("%d rows of payments are inserted\n", rowNum+1);

                rowNum++;
            }

            // ------------------------------------------------------------
            // GENERATING DATA FOR TABLES: VISIT_DATA
            // ------------------------------------------------------------
            file = new File("visit_data.csv");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            line = "";
            String[] dataVisit;
            rowNum = 0;

            while((line = br.readLine()) != null){
                ResultSet result;
                dataVisit = line.split(",");
                String[] attributes = new String[3];
                int i = 0;
                for(String attr: dataVisit){
                    if(attr.equals(""))
                        attr = "null";
                    attr = attr.replace("\'", " ");
                    attributes[i++] = attr;
                }

                result = s.executeQuery("SELECT ID FROM (SELECT ID FROM PERSON ORDER BY dbms_random.value) WHERE ROWNUM = 1");
                result.next();

                String queryInsertVisit = String.format("INSERT INTO VISIT_DATA VALUES ('%s', '%s', '%s')", attributes[0], attributes[1], result.getString(1));
                s.executeQuery(queryInsertVisit);

                if((rowNum+1)%100 == 0)
                    System.out.printf("%d rows of visit data are inserted\n", rowNum+1);
                
                rowNum++;
            }

            // ------------------------------------------------------------
            // GENERATING DATA FOR TABLES: PROPERTY, APARTMENT, AMENITY
            // ------------------------------------------------------------
            file = new File("PROPERTY.csv");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            line = "";

            ResultSet result;
            result = s.executeQuery("SELECT LEASE_ID FROM (SELECT LEASE_ID FROM LEASE ORDER BY dbms_random.value)");
            String[] leases = new String[900];
            int l = 0;
            while(result.next()){
                leases[l++] = result.getString(1);
            }
            l = 0;

            String[] dataProperty;
            rowNum = 0;
            while((line = br.readLine()) != null){
                // ResultSet result;
                dataProperty = line.split(",");
                String[] attributes = new String[6];
                int i = 0;
                for(String attr: dataProperty){
                    if(attr.equals(""))
                        attr = "null";
                    attr = attr.replace("\'", " ");
                    attributes[i++] = attr;
                }

                int amenitiesNum = (int) (Math.random()*15+1);

                String queryInsertProperty = String.format("INSERT INTO PROPERTY VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[5], amenitiesNum);
                s.executeQuery(queryInsertProperty);

                int numApts = Integer.parseInt(attributes[5]);
                int numAptsAvailable = numApts;
                String[] apt_types = {"half-bathroom", "windowless", "regular", "triangular"};
                
                // result = s.executeQuery("SELECT LEASE_ID FROM (SELECT LEASE_ID FROM LEASE ORDER BY dbms_random.value)");
                // String[] leases = new String[700];
                // int l = 0;
                // while(result.next()){
                //     leases[l++] = result.getString(1);
                // }
                if(l < 900){
                    for(int j = 1; j <= numApts; j++){
                        // System.out.printf("%s\n", result.getInt(1));
                        // if(j > numApts * 0.5)
                        //     break;
                        String monthly_rent = String.format("%.2f", Math.random()*(50000)+400);
                        String security_deposit = String.format("%.2f", Math.random()*(1000)+30);
                        String area = String.format("%.2f", Math.random()*(1000)+10); 
                        String bdrm_num = (int)(Math.random()*(10))+"";
                        String bthrm_num = (int)(Math.random()*(5)+1)+"";
                        String type = "";
                        if(Double.parseDouble(area) >= 500)
                            type += "penthouse";
                        if(Integer.parseInt(bdrm_num) == 0)
                            type += "/" + "studio";
                        type += "/" + apt_types[(int)(Math.random()*(4))];

                        // result = s.executeQuery("SELECT LEASE_ID FROM (SELECT LEASE_ID FROM LEASE ORDER BY dbms_random.value) WHERE ROWNUM = 1");
                        // result.next();

                        // System.out.printf("%s\n", result.getInt(1));
                        if(j > numApts * 0.5){
                            String queryInsertApartment = String.format("INSERT INTO APARTMENT VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '')", Integer.toString(j), monthly_rent, security_deposit, area, bdrm_num, bthrm_num, type, attributes[0]);
                            s.executeQuery(queryInsertApartment);
                        }
                        else{
                            String queryInsertApartment = String.format("INSERT INTO APARTMENT VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", Integer.toString(j), monthly_rent, security_deposit, area, bdrm_num, bthrm_num, type, attributes[0], leases[l++]);
                            s.executeQuery(queryInsertApartment);
                            // System.out.printf("%s\n", result.getInt(1));
                            numAptsAvailable--;
                        }
                    }
                }
                else{
                    for(int j = 1; j <= numApts; j++){
                        // System.out.printf("%s\n", result.getInt(1));
                        // if(j > numApts * 0.5)
                        //     break;
                        String monthly_rent = String.format("%.2f", Math.random()*(50000)+400);
                        String security_deposit = String.format("%.2f", Math.random()*(1000)+30);
                        String area = String.format("%.2f", Math.random()*(1000)+10); 
                        String bdrm_num = (int)(Math.random()*(10))+"";
                        String bthrm_num = (int)(Math.random()*(5)+1)+"";
                        String type = "";
                        if(Double.parseDouble(area) >= 500)
                            type += "penthouse";
                        if(Integer.parseInt(bdrm_num) == 0)
                            type += "/" + "studio";
                        type += "/" + apt_types[(int)(Math.random()*(4))];

                        String queryInsertApartment = String.format("INSERT INTO APARTMENT VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '')", Integer.toString(j), monthly_rent, security_deposit, area, bdrm_num, bthrm_num, type, attributes[0]);
                        s.executeQuery(queryInsertApartment);
                    }
                }

                String queryUpdateProperty = String.format("UPDATE PROPERTY SET APT_AVAILABLE = '%s' WHERE PROP_ID = '%s'", Integer.toString(numAptsAvailable), attributes[0]);
                s.executeQuery(queryUpdateProperty);

                File fileAmenity = new File("amenity.csv");
                FileReader frAmenity = new FileReader(fileAmenity);
                BufferedReader brAmenity = new BufferedReader(frAmenity);
                String lineAmenity = "";

                int amntCounter = amenitiesNum;

                String[] dataAmenity;
                while((lineAmenity = brAmenity.readLine()) != null){
                    if(amntCounter == 0)
                        break;
                    dataAmenity = lineAmenity.split(",");
                    String[] attributesAmnt = new String[5];
                    int k = 0;
                    for(String attrAmnt: dataAmenity){
                        if(attrAmnt.equals(""))
                            attrAmnt = "null";
                        attrAmnt = attrAmnt.replace("\'", " ");
                        attributesAmnt[k++] = attrAmnt;
                    }

                    String queryInsertAmenity = String.format("INSERT INTO AMENITY (name, description, floor, available, fee, prop_id) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", attributesAmnt[0], attributesAmnt[1], attributesAmnt[2], attributesAmnt[3], attributesAmnt[4], attributes[0]);
                    s.executeQuery(queryInsertAmenity);

                    amntCounter--;
                }

                if((rowNum+1)%10 == 0)
                    System.out.printf("%d rows of property are inserted\n", rowNum+1);

                rowNum++;
            }

            System.out.println("Inserted records into the table...");
            br.close();
            con.close();
            s.close();
        } catch(SQLException e){
            System.out.printf("Oracle connection failed: %s\n", e.getMessage());
        }

        // closing the scanner
        scnr.close();
    }
}