import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * 
 * Allows the user to add new employee objects and new project objects. Provides a menu to print 
 * employees and projects.
 */
public class StateMain extends State {

	/*
	 * Create a new employee object.
	 */
	void addEmployee() throws SQLException {
		String fName = this.scan.raw_input("First name: ");
		String lName = this.scan.raw_input("Last name: ");
		String jobTitle = this.scan.raw_input("Job title: ");
		
		try {
			Employee employee = new Employee(0, fName, lName, jobTitle);
			employee.insert(this.db);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * Create a new project object.
	 */
	void addProject() throws SQLException {
		String name = this.scan.raw_input("Project name: ");
		String desc = this.scan.raw_input("Project description: ");
		
		try {
			Project project = new Project(0, name, desc);
			project.insert(this.db);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Overrides update method from State class.
	 * Lists a menu that gives the user the ability to list and add projects, and to list and add employees.
	 */
	@Override
	State update() throws SQLException {
		String[] options = { "List all projects", "Add project", "Add an employee", "List all employees",
				"Access project", "Quit" };
		int rep = this.scan.showOptions("Welcome", options);
		try {
			switch (rep) {
			case 1:
				this.db.printProjects();
				break;
	
			case 2:
				this.addProject();
				break;
	
			case 3:
				this.addEmployee();
				break;
	
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
