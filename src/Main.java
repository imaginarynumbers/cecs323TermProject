
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String db_url = "jdbc:mysql://cecs-db01.coe.csulb.edu:3306?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
		Database db = new Database(db_url);
		
		// set this env vars for user and password
		String user = System.getenv("TERM_DB_USER");
		String password = System.getenv("TERM_DB_PASSWORD");
		
		if (user == null || password == null) {
			System.out.println("Error: Please set env variables: ");
			System.out.println("TERM_DB_USER");
			System.out.println("TERM_DB_PASSWORD");
			return;
		}
		
		try {
			db.connect(user, password);
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
