import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMain extends State {
	
	@Override
	State update() throws SQLException {
		String[] options = {
				"List all projects",
				"Add project",
				"Add an employee",
				"List all employees",
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
				this.db.printEmployees();
				break;
			case 5:
				Project selected = this.scan.<Project>select(this.db.getProjects());
				if (selected != null)
					return new StateAccessProject(selected);
				break;
			default:
				this.controller.stop();
				break;
		}
		return null;
	}

}
