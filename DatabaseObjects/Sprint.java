import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sprint implements IDatabaseObject{
    int sprintID;
    int projectID;
    String sprintBegin;
    String name;

    Sprint(int sprintID, int projectID, String sprintBegin, String name) {
        this.sprintID = sprintID;
        this.projectID = projectID;
        this.sprintBegin = sprintBegin;
        this.name = name;
    }

    Sprint(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getString(3),
                rs.getString(4));
    }

    @Override
    public void print() {
        System.out.println(this.sprintID + "\t" + this.projectID + "\t" + this.sprintBegin + "\t" + this.name);
    }

    @Override
    public String getTitle() {
        return this.sprintBegin + " " + this.name;
    }

    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO Sprint (projectId, sprintBegin, name)"
                + " VALUES (?, ?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.projectID);
        ps.setString(2, this.sprintBegin);
        ps.setString(3, this.name);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next())
            this.sprintID = keys.getInt(1);
        else
            System.err.println("Did not get generated key: " + query);
    }

    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM Sprint where sprintID = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.sprintID);
        ps.execute();
    }
}
