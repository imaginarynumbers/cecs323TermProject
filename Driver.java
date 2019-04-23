package cecs323.csulb.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Driver {
  private static String dbURL1 = "jdbc:mysql://cecs-db01.coe.csulb.edu:3306?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
  private static Connection conn1 = null;
  private static Statement stmnt = null;
  private static String tableName = "TermProjectDB";
  
  public static void main(String[] args) {
    createConnection();
    menu();
  }
  public static void menu() {
    Scanner sc = new Scanner(System.in);
    while(true) {
      System.out.println("Please make a selection:\n" +
          "1. Create Project\n"        +
          "2. Modify Project\n"        +
          "3. View Project\n"          +
          "4. Display Developers\n"    +
          "5. Create Sprint\n"         +
          "6. Modify Sprint\n"         +
          "7. View Sprint\n"           +
          "8. View Sprint Backlog"     );
      int choice = sc.nextInt();
      switch(choice) {
        case 1:
          //Create Project
          break;
        case 2:
          //Modify Project
          break;
        case 3:
          //View Project
          break;
        case 4:
          //Display Developers
          break;
        case 5:
          //Create Sprint
          break;
        case 6:
          //Modify Sprint
          break;
        case 7:
          //View Sprint
          break;
        case 8:
          //View Sprint Backlog
          break;
        default:
          System.out.println("You made an invalid selection, try again.");
          choice = sc.nextInt();
          break;
       }
    }
  }
  private static void createConnection() {
    try {
      conn1 = DriverManager.getConnection(dbURL1, userName, password);
      if(conn1 != null) {
        System.out.println("Connected to SoftwareCompany Database\n");
      }
    }
    catch(SQLException ex) {
      //handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
