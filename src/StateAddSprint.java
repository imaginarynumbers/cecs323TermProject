import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateAddSprint extends State {

    Project project;
    Sprint sprint;

    void insertSprint() throws SQLException {
        System.out.println("Adding sprint:");
        sprint.print();
        this.sprint.insert(this.db);

    }

    StateAddSprint(Project project) {
        this.project = project;
        String name = this.scan.raw_input("Sprint name: ");
        String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
        this.sprint = new Sprint(0, this.project.projectId, date, name);
    }

    @Override
    State update() throws SQLException {
        String[] options = { "Add another user story", "Delete dev", "Add dev", "Cancel", "Done" };
        int rep = this.scan.showOptions("Project " + this.project.title, options);
        switch (rep) {
        case 1:
            break;

        case 2:
            break;

        case 3:
            break;

        case 4:
            System.out.println("CANCELLED!");
            return new StateMain();

        default:
            this.insertSprint();
            return new StateMain();
        }
        return null;
    }
}
