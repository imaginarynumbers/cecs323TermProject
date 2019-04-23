import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabaseObject {
	void print();
	void insert(Database db) throws SQLException;
}
