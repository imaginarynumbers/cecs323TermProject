import java.sql.SQLException;
import java.util.Scanner;

abstract class AState {
	Controller controller;
	MyScanner scan;
	Database db;
	
	void setup(Controller con, MyScanner scan, Database db) {
		this.controller = con;
		this.scan = scan;
		this.db = db;
	}
	abstract AState update() throws SQLException;
}

