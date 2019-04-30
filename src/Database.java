
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

/* 
 * Database class establishes the connection and handles the queries to the database. 
 * 
 * */
public class Database {
	String url;
	Connection con = null;
	Statement state = null;

	public Database(String url) {
		this.url = url;
	}

	void connect() throws SQLException {
		try {
			this.connect(null, null);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * Establishes connection to database.
	 * @param user: username of connection
	 * @param password: password of connection
	 * */
	void connect(String user, String password) throws SQLException {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Executes database query.
	 * @param query: string query 
	 */
	ResultSet executeQuery(String query) throws SQLException {
		return this.state.executeQuery(query);
	}

	void close() throws SQLException {
		try {
			if (this.con != null) {
				this.con.close();
				this.con = null;
			}
			if (this.state != null) {
				this.state.close();
				this.state = null;
			}
		
		}  catch (SQLException e) {
			e.printStackTrace();
		}
			System.out.println("Closed!");
	}

	/*
	 * Used in printing database objects.
	 * @param objects: object array to be printed
	 */
	public <T extends IDatabaseObject> void printObjects(List<T> objects) {
		for (T object : objects) {
			object.print();
		}
	}

	/*
	 * Prints full list of projects in database.
	 */
	void printProjects() throws SQLException {
		try {
			this.<Project>printObjects(this.getProjects());
		
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Prints full list of employees in database.
	 */
	void printEmployees() throws SQLException {
		try {
			this.<Employee>printObjects(this.getEmployees());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Prints full list of sprints in database
	 * @param projectId: integer project identification
	 */
	void printSprints(int projectId) throws SQLException {
		try {
			System.out.println("SprintID \t ProjectID \t Date \t sprintName");
			this.<Sprint>printObjects(this.getSprints(projectId));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Prints full list of user stories in database.
	 * @param projectId: integer project identification
	 */
	void printUserStories(int projectId) throws SQLException {
		try {
			System.out.println("As \tI want to\t Priority\tCreation date");
			this.<UserStory>printObjects(this.getUserStories(projectId));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Getter for employees in the database.
	 */
	List<Employee> getEmployees() throws SQLException {
		
		String query = "SELECT * FROM Employee";
		ResultSet result = this.state.executeQuery(query);
		List<Employee> res = new ArrayList<>();
		
		try {
			while (result.next()) {
				res.add(new Employee(result));
			}
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Getter for projects in database.
	 */
	List<Project> getProjects() throws SQLException {
		
		String query = "SELECT * FROM Project;";
		ResultSet result = this.state.executeQuery(query);
		List<Project> res = new ArrayList<>();
		
		try {
			while (result.next()) {
				res.add(new Project(result));
			}
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Getter for sprints in the database.
	 * @param projectId: integer project identification
	 */
	List<Sprint> getSprints(int projectId) throws SQLException {
		
		String query = "SELECT * FROM Sprint WHERE projectID = (?)";
		PreparedStatement ps = this.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<Sprint> result = new ArrayList<>();
		
		try {
			while (res.next()) {
				result.add(new Sprint(res));
			}
			res.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Getter for user stories in the database.
	 * @param projectId: integer project identification
	 */
	List<UserStory> getUserStories(int projectId) throws SQLException {
		
		String query = "SELECT * FROM UserStory WHERE projectID = (?)";
		PreparedStatement ps = this.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<UserStory> result = new ArrayList<>();
		
		try {
			while (res.next()) {
				result.add(new UserStory(res));
			}
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
