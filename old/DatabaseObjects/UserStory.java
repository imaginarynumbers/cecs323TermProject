import com.sun.org.apache.regexp.internal.RESyntaxException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserStory implements IDatabaseObject {
    int storyId;
    String userAs;
    String wantTo;
    String because;
    int priority;
    String status;
    String creationDate;
    int projectId;

    UserStory(int storyId, String userAs, String wantTo, String because, int priority, String status,
            String creationDate, int projectId) {
        this.storyId = storyId;
        this.userAs = userAs;
        this.wantTo = wantTo;
        this.because = because;
        this.priority = priority;
        this.status = status;
        this.creationDate = creationDate;
        this.projectId = projectId;
    }

    UserStory(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),
                rs.getString(7), rs.getInt(8));
    }

    @Override
    public void print() {
        System.out.println("As " + this.userAs + " I want to " + this.wantTo + " because " + this.because
                + ". priority:" + this.priority + "\t" + this.creationDate);
    }

    @Override
    public String getTitle() {
        return "As " + this.userAs + " I want to " + this.wantTo + " because " + this.because;
    }

    @Override
    public void insert(Database db) throws SQLException {
        String query = "insert into UserStory " + "(userAs, wantTo, because, priority, "
                + "userStatus, creationDate, projectId) " + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement ps = db.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.userAs);
        ps.setString(2, this.wantTo);
        ps.setString(3, this.because);
        ps.setInt(4, this.priority);
        ps.setString(5, this.status);
        ps.setString(6, this.creationDate);
        ps.setInt(7, this.projectId);
        ps.execute();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next())
            this.storyId = keys.getInt(1);
        else
            System.err.println("Did not get generated key: " + query);
    }

    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM UserStory wher storyId = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.storyId);
        ps.execute();
    }
}