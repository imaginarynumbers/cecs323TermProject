
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
}
