
public class EmployeeFactory {
	public static Employee createEmployee(int employeeId, String pps, String surname, String firstName, char gender, String department, double salary, boolean fullTime) {
		
		
		//validates pps number 
		if (!pps.matches("\\d{6}[A-Za-z]")) {
			throw new IllegalArgumentException("PPS is invalid - Must be 6 digits and end in a letter");
		}
		
		
		//validation
		if (surname == null || surname.trim().isEmpty()) {
			throw new IllegalArgumentException("Surname can't be empty");
		}
		if (firstName == null || firstName.trim().isEmpty()) {
			throw new IllegalArgumentException("First Name can't be empty");
		}
		
		if (gender != 'M' && gender != 'F') {
			throw new IllegalArgumentException("Invalid Gender - Enter 'M' or 'F'");
		}
		
		if (!department.equals("Administration") && !department.equals("Production") && !department.equals("Transport") && !department.equals("Management")) {
			throw new IllegalArgumentException("Wrong department selection");
		}
		
		if (salary <= 0) {
			throw new IllegalArgumentException("Salary must be greater than 0");
		}
		
		return new Employee(employeeId, pps, surname, firstName, gender, department, salary, fullTime);
	}

}
