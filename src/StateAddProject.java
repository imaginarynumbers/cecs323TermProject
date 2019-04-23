import java.sql.SQLException;

public class StateAddProject extends State {

	@Override
	State update() throws SQLException {
		
		String name = this.scan.raw_input("Project name: ");
		String desc = this.scan.raw_input("Project description: ");
		Project project = new Project(0, name, desc);
		project.insert(this.db);
		
		return new StateMain();
	}

}
