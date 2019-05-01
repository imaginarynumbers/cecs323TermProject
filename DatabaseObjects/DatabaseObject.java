import java.sql.ResultSet;
import java.sql.SQLException;

abstract class DatabaseObject {
	Database db;

	DatabaseObject(Database db) {
		this.db = db;
	}

	abstract void print() throws SQLException;

	abstract String getTitle() throws SQLException;

	abstract void insert() throws SQLException;

	abstract void delete() throws SQLException;
}
