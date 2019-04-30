
import java.sql.SQLException;

public class StateAddUserStory extends State {
    
    private Project project;
    
    public StateAddUserStory(Project project) {
        this.project = project;
    }

    @Override
    State update() throws SQLException {
        String userAs = this.scan.raw_input("User as: ");
        String wantTo = this.scan.raw_input("Want to: ");
        String because = this.scan.raw_input("Because: ");
        int priority = this.scan.input("Priority: ");
        String status = this.scan.raw_input("Status: ");
        String creationDate = this.scan.raw_input("Date: ");
        int projectId = this.project.projectId;
        UserStory userStoryNew = new UserStory(0, userAs, wantTo, because, priority, status, creationDate, projectId);
        userStoryNew.insert(this.db);

        return new StateMain();
    }
    
}
