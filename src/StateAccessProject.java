import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StateAccessProject extends State {

	Project project;

	StateAccessProject(Project project) {
		this.project = project;
	}

	void deleteProject() throws SQLException {
		this.project.delete();
		System.out.println("Deleted project " + this.project.title);
	}

	void insertUserStory() throws SQLException {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		String as = this.scan.raw_input("As: ");
		String wantTo = this.scan.raw_input("I want to : ");
		String because = this.scan.raw_input("Because: ");
		int priority = Integer.parseInt(this.scan.raw_input("Priority (int): "));
		String status = this.scan.getUserStoryStatus();
		UserStory us = new UserStory(this.db, 0, as, wantTo, because, priority, status, date, this.project.projectId);
		us.insert();
	}

	Sprint insertSprint() throws SQLException {
		String name = this.scan.raw_input("Sprint name: ");
		String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
		Sprint sprint = new Sprint(this.db, 0, this.project.projectId, date, name);
		System.out.println("Adding sprint:");
		sprint.print();
		sprint.insert();
		return sprint;

	}

	void viewScrumTeam() throws SQLException {
		ScrumTeam team = this.project.getScrumTeam();
		List<ScrumMember> members = team.getMembers();
		for (ScrumMember member : members) {
			member.getEmployee().print();
		}
	}

	@Override
	State update() throws SQLException {
		String[] options = { "Delete project", "Create Sprint", "Create UserStory", "View Sprints", "Access Sprint",
				"View project backlog", "View ScrumTeam", "Return to main" };
		int rep = this.scan.showOptions("Project " + this.project.title, options);
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
			this.project.printSprints();
			break;

		case 5:
			Sprint sprint = this.scan.<Sprint>select(this.project.getSprints());
			if (sprint != null)
				return new StateAccessSprint(this.project, sprint);
			break;
		case 6:
			this.project.printUserStories();
			break;

		case 7:
			this.viewScrumTeam();
			break;

		default:
			return new StateMain();
		}
		return null;
	}
}
