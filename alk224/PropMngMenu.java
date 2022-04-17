import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class PropMngMenu {
    private Connection con;
    private Statement s;

    public PropMngMenu(Connection con, Statement s) {
        // TODO implement here
        this.con = con;
        this.s = s;
    }

    public void startMenu() throws SQLException, IOException, java.lang.ClassNotFoundException{
        System.out.println("test");
    }
}