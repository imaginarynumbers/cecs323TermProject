import java.sql.SQLException;

public class StateAddProject extends State {

	@Override
	State update() throws SQLException {
		String name = this.scan.raw_input("Project name: ");
		System.out.println(name);
		return new StateMain();
	}

}
