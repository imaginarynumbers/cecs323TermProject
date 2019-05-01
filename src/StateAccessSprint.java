import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateAccessSprint extends State {

    Project project;
    Sprint sprint;

    void addBacklog() throws SQLException {
        UserStory us = this.scan.<UserStory>select(this.db.getUserStories(this.project.projectId));
        Employee emp = this.scan.<Employee>select(this.db.getEmployees());
        SprintBacklog bl = new SprintBacklog(emp.employeeId, this.sprint.sprintId, us.storyId);
        bl.insert(this.db);
    }

    StateAccessSprint(Project project, Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
    }

    void viewDevelopers() throws SQLException {
        String query = "select " +
                "    Employee.* " +
                "from Project " +
                "inner join ScrumTeam ST on Project.projectId = ST.projectId " +
                "inner join ScrumMember SM on ST.scrumId = SM.scrumId " +
                "inner join Employee on Employee.employeeId = SM.employeeId " +
                "inner join Sprint S on Project.projectId = S.projectId " +
                "WHERE  S.sprintId = (?)";
        PreparedStatement ps = this.db.con.prepareStatement(query);
        ps.setInt(1, this.sprint.sprintId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee emp = new Employee(rs);
            emp.print();
        }
    }

    @Override
    State update() throws SQLException {
        String[] options = { "Add backlog", "Delete backlog", "List backlog",
                "List developers of Sprint", "Return to main" };
        int rep = this.scan.showOptions("Sprint " + this.sprint.name, options);
        switch (rep) {
        case 1:
            this.addBacklog();
            break;

        case 2:
            break;

        case 3:
            break;

        case 4:
            this.viewDevelopers();

        default:
            return new StateMain();
        }
        return null;
    }
}
