import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
	State state = null;
	boolean running = false;
	MyScanner scan;
	Database db;

	Controller(Database db) {
		this.db = db;
		this.scan = new MyScanner();
		changeState(new StateMain());
	}

	private void changeState(State state) {
		this.state = state;
		this.state.setup(this, this.scan, this.db);
	}

	void stop() {
		this.running = false;
	}

	void start() {
		this.running = true;
		while (this.running) {
			try {
				State rep = state.update();
				if (rep != null) {
					// delete this.state?
					this.changeState(rep);
				}
			} catch (SQLException e) {
				System.err.println("Unhandled SQL exception: ");
				e.printStackTrace();
			}
		}
	}
}
