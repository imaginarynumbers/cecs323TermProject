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
	
	@Override
	State update() throws SQLException {
		// TODO Auto-generated method stub
		String[] options = {
				"Delete project",
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
			
			default:
				return new StateMain();
		}
		return null;
	}
}
