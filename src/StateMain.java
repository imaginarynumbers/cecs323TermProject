import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMain extends State {
	
	@Override
	State update() throws SQLException {
		String[] options = {
				"List all projects",
				"Add project",
				"Add an employee",
				"Access project",
				"Quit"
		};
		int rep = this.scan.showOptions("Welcome", options);
		switch (rep) {
			case 1:
				this.db.printProjects();
				break;
			
			case 2:
				return new StateAddProject();
			
			case 3:
				return new StateAddEmployee();
			case 4:
				Project selected = this.scan.<Project>select(this.db.getProjects());
				if (selected != null)
					selected.print();
				break;
				
			default:
				this.controller.stop();
				break;
		}
		return null;
	}

}
