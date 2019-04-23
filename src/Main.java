
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String db_url = "jdbc:mysql://cecs-db01.coe.csulb.edu:3306?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
		Database db = new Database(db_url);
		
		String user = "cecs323sec7s12";
		String password = "Veiwai";
		
		try {
			db.connect(user, password);
			db.executeQuery("USE cecs323sec7s12;");
			
			Controller con = new Controller(db);
			con.start();
			/*
			ResultSet res = db.executeQuery("SELECT * FROM Project;");
			while (res.next()) {
				Project pro = new Project(res);
				pro.print();
			}
			*/
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
