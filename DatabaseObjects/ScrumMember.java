import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScrumMember extends DatabaseObject {
    private int scrumId;
    private int employeeId;

    ScrumMember(Database db, int scrumId, int employeeId) {
        super(db);
        this.scrumId = scrumId;
        this.employeeId = employeeId;
    }

    ScrumMember(Database db, ResultSet result) throws SQLException {
        this(db, result.getInt(1), result.getInt(2));
    }

    @Override
    public void print() throws SQLException {
        System.out.println(this.scrumId + " \t" + this.employeeId);
    }

    @Override
    public String getTitle() throws SQLException {
        return null;
    }

    @Override
    public void insert() throws SQLException {
        String query = "INSERT INTO ScrumMember (scrumId, employeeId) VALUES (?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.setInt(2, this.employeeId);
        ps.execute();
    }

    @Override
    public void delete() throws SQLException {
        String query = "DELETE FROM ScrumMember WHERE scrumId = (?) AND employeeId= (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.setInt(2, this.employeeId);
        ps.execute();
    }

    Employee getEmployee() throws SQLException {
        String query = "SELECT Employee.* FROM ScrumMember NATURAL JOIN Employee WHERE (Employee.employeeId=(?));";
        Employee result = null;
        PreparedStatement ps = this.db.con.prepareStatement(query);
        ps.setInt(3, this.employeeId);
        ResultSet res = ps.executeQuery();
        if (res.next()) {
            result = new Employee(this.db, res);
        }
        return result;
    }
}
