import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {
    private List<Employee> employees;

    public EmployeeDatabase() {
        employees = new ArrayList<>();
    }

    // Add a new employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Find an employee by ID
    public Employee findEmployeeById(int id) {
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == id) {
                return emp;
            }
        }
        return null;
    }

    // Find employees by surname
    public List<Employee> findEmployeesBySurname(String surname) {
        List<Employee> matchingEmployees = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getSurname().equalsIgnoreCase(surname)) {
                matchingEmployees.add(emp);
            }
        }
        return matchingEmployees;
    }

    // Get the next available employee ID
    public int getNextEmployeeId() {
        return employees.size() + 1;
    }

    // Delete an employee by ID
    public boolean deleteEmployee(int id) {
        return employees.removeIf(emp -> emp.getEmployeeId() == id);
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
