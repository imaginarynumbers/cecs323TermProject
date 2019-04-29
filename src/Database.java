
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

	public <T extends IDatabaseObject> void printObjects(List<T> objects) {
		for (T object : objects) {
			object.print();
		}
	}

	void printProjects() throws SQLException {
		this.<Project>printObjects(this.getProjects());
	}

	void printEmployees() throws SQLException {
		this.<Employee>printObjects(this.getEmployees());
	}

	void printSprints(int projectId) throws SQLException {
		System.out.println("SprintID \t ProjectID \t Date \t sprintName");
		this.<Sprint>printObjects(this.getSprints(projectId));
	}

	void printUserStories(int projectId) throws SQLException {
		System.out.println("UserStoryID \t ProjectID \t As \t I want to \t Priority \t creation date");
		this.<UserStory>printObjects(this.getUserStories(projectId));
	}

	List<Employee> getEmployees() throws SQLException {
		String query = "SELECT * FROM Employee";
		ResultSet result = this.state.executeQuery(query);
		List<Employee> res = new ArrayList<>();

		while (result.next()) {
			res.add(new Employee(result));
		}
		result.close();
		return res;
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

	List<Sprint> getSprints(int projectId) throws SQLException {
		String query = "SELECT * FROM Sprint WHERE projectID = (?)";
		PreparedStatement ps = this.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<Sprint> result = new ArrayList<>();

		while (res.next()) {
			result.add(new Sprint(res));
		}
		res.close();
		return result;
	}

	List<UserStory> getUserStories(int projectId) throws SQLException {
		String query = "SELECT * FROM UserStory WHERE projectID = (?)";
		PreparedStatement ps = this.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<UserStory> result = new ArrayList<>();

		while (res.next()) {
			result.add(new UserStory(res));
		}
		res.close();
		return result;
	}
}
