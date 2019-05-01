import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Project extends DatabaseObject {
	int projectId;
	String title;
	String description;

	Project(Database db, int projectId, String title, String description) {
		super(db);
		this.projectId = projectId;
		this.title = title;
		this.description = description;

	}

	Project(Database db, ResultSet result) throws SQLException {
		this(db, result.getInt(1), result.getString(2), result.getString(3));
	}

	@Override
	public void print() throws SQLException {
		System.out.println(this.projectId + " \t" + this.title + "\t\"" + this.description + "\"");
	}

	@Override
	public void insert() throws SQLException {
		String query = "INSERT INTO Project (title, description)" + " VALUES (?, ?)";
		PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, this.title);
		ps.setString(2, this.description);
		ps.execute();
		ResultSet keys = ps.getGeneratedKeys();
		if (keys.next())
			this.projectId = keys.getInt(1);
		else
			System.err.println("Did not get generated key: " + query);
	}

	@Override
	public String getTitle() throws SQLException {
		return this.title + " " + this.description;
	}

	@Override
	public void delete() throws SQLException {
		String query = "DELETE FROM Project WHERE projectId = ?;";
		PreparedStatement ps = db.con.prepareStatement(query);
		ps.setInt(1, this.projectId);
		ps.execute();
	}
}
