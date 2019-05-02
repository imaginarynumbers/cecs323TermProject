import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        String rep = this.scan.raw_input("Are you sure you want to remove: " + backlog.getTitle() + "\n(y/n): ");
        if (rep.toLowerCase().equals("y"))
            backlog.delete();
    }

    void listDevelopers() throws SQLException {
        List<Employee> employees = this.sprint.getEmployees();
        for (Employee emp : employees) {
            emp.print();
        }
    }

    StateAccessSprint(Project project, Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
    }



    @Override
    State update() throws SQLException {
        String[] options = { "Add backlog", "Delete backlog", "List backlogs", "List developers", "Return to project" };
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

        case 4:
            this.listDevelopers();
            break;
        
        default:
            return new StateAccessProject(project);
        }
        return null;
    }
}
