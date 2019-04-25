
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLTimeoutException;

public class Database {
	String url;
	Connection con = null;
	Statement state = null;

	public Database(String url) {
		this.url = url;
	}

	void connect() throws SQLException {
		this.connect(null, null);
	}

	void connect(String user, String password) throws SQLException {
		System.out.println("Connecting...");
		if (user != null && password != null) {
			System.out.println(user + " : " + password);
			this.con = DriverManager.getConnection(this.url, user, password);
		} else {
			this.con = DriverManager.getConnection(this.url);
		}
		if (this.con == null) {
			throw new SQLException("this.con is null");
		} else {
			System.out.println("Connected!");
		}
		this.state = this.con.createStatement();
	}
	
	ResultSet executeQuery(String query) throws SQLException {
		return this.state.executeQuery(query);
	}

	void close() throws SQLException {

		if (this.con != null) {
			this.con.close();
			this.con = null;
		}
		if (this.state != null) {
			this.state.close();
			this.state = null;
		}
		System.out.println("Closed!");
	}
	
	void printProjects() throws SQLException {
		ResultSet res = this.executeQuery("SELECT * FROM Project;");
		while (res.next()) {
			Project pro = new Project(res);
			pro.print();
		}
	}

	void printEmployees() throws SQLException {
		ResultSet res = this.executeQuery("SELECT * FROM Employee");
		System.out.println("First Name\tLast Name\tJob Title");
		while (res.next()) {
			Employee emp = new Employee(res);
			emp.print();
		}
	}
	
	List<Project> getProjects() throws SQLException {
		String query = "SELECT * FROM Project;";
		ResultSet result = this.state.executeQuery(query);
		List<Project> res = new ArrayList<>();
		
		while (result.next()) {
			res.add(new Project(result));
		}
		result.close();
		return res;
	}
}


