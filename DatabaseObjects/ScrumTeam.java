
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrumTeam extends DatabaseObject {
    private int scrumId;
    private int projectId;

    public ScrumTeam(Database db, int scrumId, int projectId) {
        super(db);
        this.scrumId = scrumId;
        this.projectId = projectId;
    }

    public ScrumTeam(Database db, ResultSet result) throws SQLException {
        this(db, result.getInt(1), result.getInt(2));
    }

    @Override
    public void print() throws SQLException {
        System.out.println(this.getTitle());
    }

    @Override
    public String getTitle() throws SQLException {
        return this.scrumId + "\t" + this.projectId;
    }

    public void insert() throws SQLException {
        String query = "INSERT INTO ScrumTeam (projectId) VALUES (?)";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.projectId);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next())
            this.scrumId = keys.getInt(1);
        else
            System.err.println("Did not get generated key: " + query);
    }

    @Override
    public void delete() throws SQLException {
        String query = "DELETE FROM ScrumTeam WHERE scrumId = ?;";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.execute();
    }
}
