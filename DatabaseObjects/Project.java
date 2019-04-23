import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	@Override
	public void print() {
		System.out.println(this.projectId + " \t" + this.title + "\t\"" + this.description + "\"");
	}

	@Override
	public void insert(Database db) throws SQLException {
		String query = "INSERT INTO Project (projectId, title, description)"
				+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps = db.con.prepareStatement(query);
		ps.setInt(1, this.projectId);
		ps.setString(2, this.title);
		ps.setString(3, this.description);
		ps.execute();
	}
	
}
