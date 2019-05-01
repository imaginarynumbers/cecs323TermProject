import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee extends DatabaseObject {
    int employeeId;
    String fName;
    String lName;
    String jobTitle;

    Employee(Database db, int employeeId, String fName, String lName, String jobTitle) {
        super(db);
        this.employeeId = employeeId;
        this.fName = fName;
        this.lName = lName;
        this.jobTitle = jobTitle;
    }

    Employee(Database db, ResultSet resultSet) throws SQLException {
        this(db, resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
    }

    @Override
    public void print() throws SQLException {
        System.out.println(this.employeeId + "\t" + this.fName + "\t" + this.lName + "\t" + this.jobTitle);
    }

    @Override
    public String getTitle() throws SQLException {
        return this.fName + " " + this.lName;
    }

    @Override
    public void insert() throws SQLException {
        String query = "INSERT INTO Employee (fname, lname, jobTitle) " + "VALUES (?, ?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.fName);
        ps.setString(2, this.lName);
        ps.setString(3, this.jobTitle);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            this.employeeId = keys.getInt(1);
        } else {
            System.err.println("Did not get generated keys: " + query);
        }
    }

    @Override
    public void delete() throws SQLException {
        String query = "DELETE FROM Employee WHERE employeeId = ?;";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.employeeId);
        ps.execute();
    }
}
