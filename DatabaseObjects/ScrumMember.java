import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScrumMember implements IDatabaseObject {
    private int scrumId;
    private int employeeId;
    public Employee employee;

    public ScrumMember(int scrumId, int employeeId) {
        this.scrumId = scrumId;
        this.employeeId = employeeId;
    }
    
    public ScrumMember(Employee employee) {
        this.employee = employee;
    }

    public ScrumMember(ResultSet result) throws SQLException {
        this(result.getInt(1), result.getInt(2));
    }

    @Override
    public void print() {
        System.out.println(this.scrumId + " \t" + this.employeeId);
    }

    @Override
    public String getTitle() {
        return null;
    }
    
    public int getScrumId() {
        return scrumId;
    }

    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO ScrumMember (scrumId, employeeId) VALUES (?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.setInt(2, this.employeeId);
        ps.execute();
    }

    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM ScrumMember WHERE scrumId = (?) AND employeeId= (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.setInt(2, this.employeeId);
        ps.execute();
    }
}
