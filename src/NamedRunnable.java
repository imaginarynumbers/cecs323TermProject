import java.sql.SQLException;

public interface NamedRunnable {
	String name();
	void run() throws SQLException;
}
