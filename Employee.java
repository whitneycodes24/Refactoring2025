public class Employee {
	
    private int employeeId;
    private String pps;
    private String surname;
    private String firstName;
    private char gender;
    private String department;
    private double salary;
    private boolean fullTime;

    public Employee(int employeeId, String pps, String surname, String firstName, char gender, String department, double salary, boolean fullTime) {
    	
        this.employeeId = employeeId;
        this.pps = pps;
        this.surname = surname;
        this.firstName = firstName;
        this.gender = gender;
        this.department = department;
        this.salary = salary;
        this.fullTime = fullTime;
    }

    public int getEmployeeId() { return employeeId; }
    public String getPps() { return pps; }
    public String getSurname() { return surname; }
    public String getFirstName() { return firstName; }
    public char getGender() { return gender; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public boolean isFullTime() { return fullTime; }

    public void setSurname(String surname) { this.surname = surname; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setGender(char gender) { this.gender = gender; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setFullTime(boolean fullTime) { this.fullTime = fullTime; }

    @Override
    public String toString() {
        return "ID: " + employeeId + "\nPPS: " + pps + "\nName: " + firstName + " " + surname +
               "\nGender: " + gender + "\nDepartment: " + department +
               "\nSalary: " + salary + "\nFull-Time: " + (fullTime ? "Yes" : "No");
    }
}
