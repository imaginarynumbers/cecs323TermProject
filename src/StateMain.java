import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMain extends State {
	
	@Override
	State update() throws SQLException {
		final StateMain state = this;
		NamedRunnable[] options = {
				new NamedRunnable() {
					@Override
					public String name() {
						return "List all projects";
					}

					@Override
					public void run() throws SQLException {
						state.db.printProjects();
					}
				}
		};
		/*
		String[] options = {
				"List all projects",
				"Add project",
				"Quit"
		};
		int rep = this.scan.showOptions("Welcome", options);
		switch (rep) {
			case 1:
				this.db.printProjects();
				break;
			
			case 2:
				return new StateAddProject();
			
			default:
				this.controller.stop();
				break;
		}
		*/
		return null;
	}

}
