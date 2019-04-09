import java.sql.ResultSet;
import java.sql.SQLException;

public class Project implements IDatabaseObject {
	String title;
	String description;
	int projectId;

	Project(String title, String description, int projectId) {
		this.title = title;
		this.description = description;
		this.projectId = projectId;
	}

	Project(ResultSet result) throws SQLException {
		this(result.getString(1), result.getString(2), result.getInt(3));
	}

	@Override
	public void print() {
		System.out.println(this.projectId + '\t' + this.title + '\t' + this.description);
	}
	
}
