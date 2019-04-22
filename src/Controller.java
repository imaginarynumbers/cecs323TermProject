import java.util.Scanner;

public class Controller {
	AState state = null;
	boolean running = false;
	MyScanner scan;
	Database db;
	
	Controller(Database db) {
		this.db = db;
		this.scan = new MyScanner();
	}
	
	void stop() {
		this.running = false;
	}
	
	void start() {
		this.running = true;
		while (this.running) {
			AState rep = state.update();
			if (rep != null) {
				// delete this.state?
				this.state = rep;
				this.state.setup(this, this.scan, this.db);
			}
		}
	}
}
