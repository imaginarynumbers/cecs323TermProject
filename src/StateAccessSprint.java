import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Provides access to sprint objects to allow updates.
 */
public class StateAccessSprint extends State {

    Project project;
    Sprint sprint;

    /*
     * Adds user story to sprint backlog.
     */
    void addUserStoryToSprintBacklog() throws SQLException {
    	try {
		  UserStory us = this.scan.<UserStory>select(this.db.getUserStories(this.project.projectId));
		  Employee emp = this.scan.<Employee>select(this.db.getEmployees());
		  SprintBacklog bl = new SprintBacklog(emp.employeeId, this.sprint.sprintId, us.storyId);
		  bl.insert(this.db);
		  
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
      
    }

    /*
     * Gives user access to sprint object to allow CRUD operations.
     */
    StateAccessSprint(Project project, Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
    }

    /*
     * Allows the addition of a new user story to the sprint backlog.
     */
    @Override
    State update() throws SQLException {
    	
    	try {
    		  String[] options = { "Add user story to sprint backlog", "Delete dev", "Add dev", "Return to main" };
    	        int rep = this.scan.showOptions("Sprint " + this.sprint.name, options);
    	        switch (rep) {
    	        case 1:
    	            this.addUserStoryToSprintBacklog();
    	            break;

    	        case 2:
    	            break;

    	        case 3:
    	            break;

    	        default:
    	            return new StateMain();
    	        }
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
      
        return null;
    }
}
