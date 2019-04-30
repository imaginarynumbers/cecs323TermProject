import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * 
 * Provides state access to database objects: project, sprint, user story, project backlog, or return to main.
 */
public class StateAccessProject extends State {

	Project project;

	/*
	 * Contstructor.
	 */
	StateAccessProject(Project project) {
		this.project = project;
	}

	/*
	 * Deletes projects from the database.
	 */
	void deleteProject() throws SQLException {
		try {
			this.project.delete(this.db);
			System.out.println("Deleted project " + this.project.title);
			
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		
	}

	/*
	 * Creates and inserts new user story into the database.
	 */
	void insertUserStory() throws SQLException {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		String as = this.scan.raw_input("As: ");
		String wantTo = this.scan.raw_input("I want to : ");
		String because = this.scan.raw_input("Because: ");
		int priority = Integer.parseInt(this.scan.raw_input("Priority (int): "));
		String status = this.scan.raw_input("Status: "); // Should be an enum here
		
		try {
			UserStory us = new UserStory(0, as, wantTo, because, priority, status, date, this.project.projectId);
			us.insert(this.db);
			
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	
	}

	/*
	 * Creates and inserts new sprint into the database.
	 */
	Sprint insertSprint() throws SQLException {
		String name = this.scan.raw_input("Sprint name: ");
		String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
		Sprint sprint = new Sprint(0, this.project.projectId, date, name);
		System.out.println("Adding sprint:");
		sprint.print();
		
		try {
			sprint.insert(this.db);
			
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	
		return sprint;

	}

	/*
	 * Updates database by allowing users to delete, access, and insert new objects into each category
	 * (project, sprint, user story, project backlog, or return to main) by 
	 * calling one of the above methods.
	 */
	@Override
	State update() throws SQLException {
		String[] options = { "Delete project", "Create Sprint", "Create UserStory", "View Sprints", "Access Sprint",
				"View project backlog", "Return to main" };
		int rep = this.scan.showOptions("Project " + this.project.title, options);
		
		try {
			switch (rep) {
			case 1:
				String y = this.scan.raw_input("Are you sure you want to DELETE this project? (y/n) ");
				if (y.toLowerCase().equals("y")) {
					this.deleteProject();
					return new StateMain();
				}
				break;
	
			case 2:
				return new StateAccessSprint(this.project, this.insertSprint());
	
			case 3:
				this.insertUserStory();
				break;
	
			case 4:
				this.db.printSprints(this.project.projectId);
				break;
	
			case 5:
				Sprint sprint = this.scan.<Sprint>select(this.db.getSprints(this.project.projectId));
				return new StateAccessSprint(this.project, sprint);
	
			case 6:
				this.db.printUserStories(this.project.projectId);
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
