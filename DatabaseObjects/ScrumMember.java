import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * 
 * ScrumMember class, allows creation, manipulation, and access to ScrumMember objects. Implements the IDatabase Interface.
 */
public class ScrumMember implements IDatabaseObject {
    private int scrumId;
    private int employeeId;
    private Employee employee;

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

    /*
     * Overrides IDatabase Interface method print().
     * Prints the scrum id number and the employee id number.
     */
    @Override
    public void print() {
        System.out.println(this.scrumId + " \t" + this.employeeId);
    }

    /*
     * Overrides IDatabase Interface method getTitle().
     * Returns the title of the scrum as a string.
     */
    @Override
    public String getTitle() {
        return null;
    }

    /*
     * Overrides IDatabase Interface method insert().
     * Creates and inserts a new scrum member object into the database.
     * @param db: database object to be manipulated.
     */
    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO ScrumMember (scrumId, employeeId) VALUES (?, ?)";
        
        try {
            PreparedStatement ps = db.con.prepareStatement(query);
            ps.setInt(1, this.scrumId);
            ps.setInt(2, this.employeeId);
            ps.execute();
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }

    /*
     * Overrides IDatabase Interface method delete().
     * Deletes scrum member objects from database.
     * @param db: database object to be manipulated.
     */
    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM ScrumMember WHERE scrumId = (?) AND employeeId= (?)";
       
        try {
            PreparedStatement ps = db.con.prepareStatement(query);
            ps.setInt(1, this.scrumId);
            ps.setInt(2, this.employeeId);
            ps.execute();
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
}
