import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sprint extends DatabaseObject {
    int sprintId;
    int projectId;
    String sprintBegin;
    String name;

    Sprint(Database db, int sprintId, int projectId, String sprintBegin, String name) {
        super(db);
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.sprintBegin = sprintBegin;
        this.name = name;
    }

    Sprint(Database db, ResultSet rs) throws SQLException {
        this(db, rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
    }

    @Override
    public void print() throws SQLException {
        System.out.println(this.sprintId + "\t" + this.projectId + "\t" + this.sprintBegin + "\t" + this.name);
    }

    @Override
    public String getTitle() throws SQLException {
        return this.sprintBegin + " " + this.name;
    }

    @Override
    public void insert() throws SQLException {
        String query = "INSERT INTO Sprint (projectId, sprintBegin, name)" + " VALUES (?, ?, ?)";
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
    }

    @Override
    public void delete() throws SQLException {
        String query = "DELETE FROM Sprint where sprintId = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.sprintId);
        ps.execute();
    }
}
