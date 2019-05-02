import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	void printSprints() throws SQLException {
		System.out.println("SprintID \t ProjectID \t Date \t sprintName");
		this.db.<Sprint>printObjects(this.getSprints());
	}

	void printUserStories() throws SQLException {
		this.db.<UserStory>printObjects(this.getUserStories());
	}

	List<Sprint> getSprints() throws SQLException {
		String query = "SELECT * FROM Sprint WHERE projectId = (?)";
		PreparedStatement ps = this.db.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<Sprint> result = new ArrayList<>();

		while (res.next()) {
			result.add(new Sprint(this.db, res));
		}
		res.close();
		return result;
	}

	List<UserStory> getUserStories() throws SQLException {
		String query = "SELECT * FROM UserStory EXCEPT SELECT * FROM Sprint WHERE projectId = (?)";
		PreparedStatement ps = this.db.con.prepareStatement(query);
		ps.setInt(1, projectId);
		ResultSet res = ps.executeQuery();
		List<UserStory> result = new ArrayList<>();

		while (res.next()) {
			result.add(new UserStory(this.db, res));
		}
		res.close();
		return result;
	}
}
