import java.sql.SQLException;
import java.util.List;

public class StateAddSprint extends State {
    Project project;

    public StateAddSprint(Project project) {
        this.project = project;
    }

    @Override
    State update() throws SQLException {
        String name = this.scan.raw_input("Sprint name: ");
        String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
        Sprint sprint = new Sprint(0, this.project.projectId, date, name);
        sprint.insert(this.db);

        return new StateMain();
    }


}
