import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 
 * Project class, allows manipulation of and access to project objects. Implements the IDatabase Interface.
 */
public class Project implements IDatabaseObject {
	int projectId;
	String title;
	String description;
	

	Project(int projectId, String title, String description) {
		this.projectId = projectId;
		this.title = title;
		this.description = description;
		
	}

	
	Project(ResultSet result) throws SQLException {
		this(result.getInt(1), result.getString(2), result.getString(3));
	}

	/*
	     * Overrides IDatabase Interface method print().
	     * Prints project id, title, and description.
	 */
	@Override
	public void print() {
		System.out.println(this.projectId + " \t" + this.title + "\t\"" + this.description + "\"");
	}

	/*
	 * Overrides IDatabase Interface method insert().
	 * Inserts a new project into database.
	 * @param db: database object to be manipulated.
	 */
	@Override
	public void insert(Database db) throws SQLException {
		String query = "INSERT INTO Project (title, description)"
				+ " VALUES (?, ?)";
		try {
			PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.execute();
			ResultSet keys = ps.getGeneratedKeys();
			if (keys.next())
				this.projectId = keys.getInt(1);
			else
				System.err.println("Did not get generated key: " + query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Overrides IDatabase Interface method getTitle().
	 * Returns the project title in a string.
	 */
	@Override
	public String getTitle() {
		return this.title + " " + this.description;
	}
	
	/*
	 * Overrides IDatabase Interface method getTitle().
	 * Deletes projects from database.
	 * @param db: database object to be manipulated.
	 */
	@Override
	public void delete(Database db) throws SQLException {
		String query = "DELETE FROM Project WHERE projectId = ?;";
		try {
	        PreparedStatement ps = db.con.prepareStatement(query);
	        ps.setInt(1, this.projectId);
	        ps.execute();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
