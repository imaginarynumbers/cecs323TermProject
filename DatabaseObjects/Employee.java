import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee implements IDatabaseObject {
    int employeeID;
    String fName;
    String lName;
    String jobTitle;

    Employee(int employeeID, String fName, String lName, String jobTitle) {
        this.employeeID = employeeID;
        this.fName = fName;
        this.lName = lName;
        this.jobTitle = jobTitle;
    }

    Employee(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4));
    }


    @Override
    public void print() {
        System.out.println(this.employeeID + "\t" + this.fName + "\t" + this.lName + "\t" + this.jobTitle);
    }

    @Override
    public String getTitle() {
        return this.fName + " " + this.lName;
    }

    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO Employee (fname, lname, jobTitle) " +
                "VALUES (?, ?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.fName);
        ps.setString(2, this.lName);
        ps.setString(3, this.jobTitle);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            this.employeeID = keys.getInt(1);
        } else {
            System.err.println("Did not get generated keys: " + query);
        }
    }
}
