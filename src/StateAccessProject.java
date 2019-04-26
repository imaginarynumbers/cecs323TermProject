import java.sql.SQLException;

public class StateAccessProject extends State {

	Project project;
	
	StateAccessProject(Project project) {
		this.project = project;
	}
	
	void deleteProject() throws SQLException {
		this.project.delete(this.db);
		System.out.println("Deleted project " + this.project.title);
	}

	void insertSprint() throws SQLException {
		String name = this.scan.raw_input("Sprint name: ");
		String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
		Sprint sprint = new Sprint(0, this.project.projectId, date, name);
		sprint.insert(this.db);

	}
	
	@Override
	State update() throws SQLException {
		// TODO Auto-generated method stub
		String[] options = {
				"Delete project",
				"Add sprint associated with project",
				"Return to main"
		};
		int rep = this.scan.showOptions("Project " + this.project.title, options);
		switch (rep) {
			case 1:
				String y = this.scan.raw_input("Are you sure you want to DELETE this project? (y/n) ");
				if (y.toLowerCase().equals("y")) {
					this.deleteProject();
					return new StateMain();
				}
				break;

			case 2:
				insertSprint();
				return new StateMain();

			default:
				return new StateMain();
		}
		return null;
	}
}
