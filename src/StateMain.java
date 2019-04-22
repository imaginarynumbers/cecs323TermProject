
public class StateMain extends AState {
	
	@Override
	AState update() {
		String[] options = {
				"List all projects",
				"Quit"
		};
		int rep = this.scan.showOptions("Welcome", options);
		switch (rep) {
			case 1:
				System.out.println("hue");
				break;
			
			default:
				this.controller.stop();
				break;
		}
		return null;
	}

}
