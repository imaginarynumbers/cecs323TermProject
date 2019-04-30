
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScrumTeam implements IDatabaseObject {
    private int scrumId;
    private int projectId;
    private String name;
    private ArrayList<ScrumMember> teamMembers;
    public ScrumTeam(int scrumId, int projectId, String name) {
        this.scrumId = scrumId;
        this.projectId = projectId;
        this.name = name;
    }

    public ScrumTeam(ResultSet result) throws SQLException {
	this(result.getInt(1), result.getInt(2), result.getString(3));
    }
    
    @Override
    public void print() {
        System.out.println(this.scrumId + " \t" + this.projectId + "\t\"" + this.name + "\"");
        //print the members of the team
        for(ScrumMember temp : teamMembers) {
            System.out.println(temp.getScrumId() + " \t" + temp.employee.getName() + "\t\"" + temp.employee.getJobTitle() + "\"");
        }
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO ScrumTeam (name)"
                        + " VALUES (?)";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.name);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next())
            this.scrumId = keys.getInt(1);
        else
            System.err.println("Did not get generated key: " + query);
    }
    
    public void insert(ScrumMember member, int scrumEmployeeId) {
        ScrumMember newMember = new ScrumMember(0, scrumEmployeeId);
        teamMembers.add(newMember);
    }

    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM ScrumTeam WHERE scrumId = ?;";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.scrumId);
        ps.execute();
    }
    
}
