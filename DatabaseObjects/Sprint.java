import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
 * 
 * Sprint class, allows the creation of, manipulation of, and access to sprint objects. Implements the IDatabase Interface.
 */
public class Sprint implements IDatabaseObject {
    int sprintId;
    int projectId;
    String sprintBegin;
    String name;

    
    Sprint(int sprintId, int projectId, String sprintBegin, String name) {
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.sprintBegin = sprintBegin;
        this.name = name;
    }

    
    Sprint(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
    }

    /*
     * Overrides IDatabase Interface method print().
     * Prints sprint id number, project id number, and the name of the sprint.
     */
    @Override
    public void print() {
        System.out.println(this.sprintId + "\t" + this.projectId + "\t" + this.sprintBegin + "\t" + this.name);
    }

    /*
     * Overrides IDatabase Interface method getTitle().
     * 
     */
    @Override
    public String getTitle() {
        return this.sprintBegin + " " + this.name;
    }

    /*
     * Overrides IDatabase Interface method insert().
     * Creates and inserts a new sprint into the database.
     * @param db: database object to be manipulated
     */
    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO Sprint (projectId, sprintBegin, name)" + " VALUES (?, ?, ?)";
        
        try {
            PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, this.projectId);
            ps.setString(2, this.sprintBegin);
            ps.setString(3, this.name);
            ps.execute();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next())
                this.sprintId = keys.getInt(1);
            else
                System.err.println("Did not get generated key: " + query);
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }

    /*
     * Overrides IDatabase Interface method delete().
     * Deletes sprint from database.
     * @param db: database object to be manipulated.
     */
    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM Sprint where sprintId = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.sprintId);
        ps.execute();
    }
}
