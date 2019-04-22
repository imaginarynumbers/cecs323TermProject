import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMain extends State {
	
	@Override
	State update() throws SQLException {
		String[] options = {
				"List all projects",
				"Quit"
		};
		int rep = this.scan.showOptions("Welcome", options);
		switch (rep) {
			case 1:
				ResultSet res = this.db.executeQuery("SELECT * FROM Project;");
				while (res.next()) {
					Project pro = new Project(res);
					pro.print();
				}
				break;
			
			default:
				this.controller.stop();
				break;
		}
		return null;
	}

}
