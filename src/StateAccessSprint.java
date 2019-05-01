import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateAccessSprint extends State {

    Project project;
    Sprint sprint;

    void addBacklog() throws SQLException {
        UserStory us = this.scan.<UserStory>select(this.project.getUserStories());
        Employee emp = this.scan.<Employee>select(this.db.getEmployees());
        SprintBacklog bl = new SprintBacklog(this.db, emp.employeeId, this.sprint.sprintId, us.storyId);
        bl.insert();
    }

    void deleteBacklog() throws SQLException {
        SprintBacklog backlog = this.scan.<SprintBacklog>select(this.sprint.getBacklogs());
        backlog.delete();
    }

    StateAccessSprint(Project project, Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
    }

    @Override
    State update() throws SQLException {
        String[] options = { "Add backlog", "Delete backlog", "List backlogs", "Return to project" };
        int rep = this.scan.showOptions("Sprint " + this.sprint.name, options);
        switch (rep) {
        case 1:
            this.addBacklog();
            break;

        case 2:
            this.deleteBacklog();
            break;

        case 3:
            this.sprint.printBacklogs();
            break;

        default:
            return new StateAccessProject(project);
        }
        return null;
    }
}
