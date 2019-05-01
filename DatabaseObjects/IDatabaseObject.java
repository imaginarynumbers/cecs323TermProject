import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabaseObject {
	void print() throws SQLException;

	String getTitle() throws SQLException;

	void insert(Database db) throws SQLException;

	void delete(Database db) throws SQLException;
}
