import java.sql.SQLException;

public class StateAddEmployee extends State {

    @Override
    State update() throws SQLException {
        String fName = this.scan.raw_input("First name: ");
        String lName = this.scan.raw_input("Last name: ");
        String jobTitle = this.scan.raw_input("Job title: ");
        Employee employee = new Employee(0, fName, lName, jobTitle);
        employee.insert(db);

        return new StateMain();
    }

}
