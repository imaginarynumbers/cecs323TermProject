import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SprintBacklog extends DatabaseObject {
    int employeeId;
    int sprintId;
    int storyId;

    SprintBacklog(Database db, int employeeId, int sprintId, int storyId) {
        super(db);
        this.employeeId = employeeId;
        this.sprintId = sprintId;
        this.storyId = storyId;

    }

    SprintBacklog(Database db, ResultSet rs) throws SQLException {
        this(db, rs.getInt(1), rs.getInt(2), rs.getInt(3));
    }

    @Override
    public void print() throws SQLException {
        System.out.println(this.getTitle());
    }

    @Override
    public String getTitle() throws SQLException {
        UserStory us = this.getUserStory();
        Employee emp = this.getEmployee();
        StringBuilder sb = new StringBuilder();

        sb.append("Priority: " + us.priority);
        sb.append("\tDev: " + emp.fName);
        sb.append(" " + emp.lName);
        sb.append("\tStory: As " + us.userAs);
        sb.append(" I want to " + us.wantTo);
        sb.append(" because " + us.because);
        sb.append(". Status: " + us.status);
        return sb.toString();
    }

    @Override
    public void insert() throws SQLException {
        String query = "INSERT INTO SprintBacklog (employeeId, sprintId, storyId) VALUES (?, ?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.employeeId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.storyId);
        ps.execute();
    }

    @Override
    public void delete() throws SQLException {
        String query = "DELETE FROM SprintBacklog WHERE employeeId = (?) AND sprintId = (?) AND storyId = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.employeeId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.storyId);
        ps.execute();
    }

    private ResultSet getObject(String tableName) throws SQLException {
        String query = "SELECT DISTINCT " + tableName + ".* FROM SprintBacklog NATURAL JOIN Employee,Sprint,UserStory"
                + " WHERE (UserStory.storyId=(?) AND Sprint.sprintId=(?) AND Employee.employeeId=(?))"
                + " ORDER BY UserStory.priority;";
        PreparedStatement ps = this.db.con.prepareStatement(query);
        ps.setInt(1, this.storyId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.employeeId);
        ResultSet res = ps.executeQuery();
        return res;
    }

    public Employee getEmployee() throws SQLException {
        ResultSet res = this.getObject("Employee");
        Employee result = null;

        if (res.next()) {
            result = new Employee(this.db, res);
        }
        res.close();
        return result;
    }

    public Sprint getSprint() throws SQLException {
        ResultSet res = this.getObject("Sprint");
        Sprint result = null;

        if (res.next()) {
            result = new Sprint(this.db, res);
        }
        res.close();
        return result;
    }

    public UserStory getUserStory() throws SQLException {
        ResultSet res = this.getObject("UserStory");
        UserStory result = null;

        if (res.next()) {
            result = new UserStory(this.db, res);
        }
        res.close();
        return result;
    }
}
