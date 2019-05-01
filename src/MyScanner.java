import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MyScanner {

	private Scanner scan;

	MyScanner() {
		this.scan = new Scanner(System.in);
	}

	public int showOptions(String title, String[] options) {
		System.out.println("--- " + title + " ---");
		for (int x = 0; x < options.length; x += 1) {
			System.out.println((x + 1) + ". " + options[x]);
		}
		int res = this.input(": ");
		if (res < 1 || res > options.length) {
			System.out.println("Enter value between 1 and " + options.length);
			return this.showOptions(title, options);
		}
		return res;
	}

	public String raw_input(String hint, String ifempty) {
		System.out.print(hint);
		String res = this.scan.nextLine();
		if (res.length() == 0) {
			return ifempty;
		}
		return res;
	}

	public String raw_input(String hint) {
		return this.raw_input(hint, "");
	}

	public int input(String hint) {
		System.out.print(hint);
		String line;
		line = this.scan.nextLine();
		try {
			return Integer.valueOf(line);
		} catch (Exception e) {
			System.out.println("Enter an integer!");
			return this.input(hint);
		}
	}

	public <T extends IDatabaseObject> T select(List<T> objects) throws SQLException {
		String[] options = new String[objects.size() + 1];
		int x = 0;
		if (objects.size() <= 0) {
			System.out.println("No objects!");
			return null;
		}
		for (T object : objects) {
			options[x] = object.getTitle();
			x += 1;
		}
		options[x] = "[CANCEL]";
		int selected = this.showOptions("Select an object", options);
		if (selected == objects.size() + 1) {
			return null;
		} else {
			return objects.get(selected - 1);
		}
	}

}
