import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {
    private List<Employee> employees;

    public EmployeeDatabase() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee findEmployeeById(int id) {
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == id) {
                return emp;
            }
        }
        return null;
    }

    public boolean deleteEmployee(int id) {
        return employees.removeIf(emp -> emp.getEmployeeId() == id);
    }

    public List<Employee> findEmployeesBySurname(String surname) {
        List<Employee> matchingEmployees = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getSurname().equalsIgnoreCase(surname)) {
                matchingEmployees.add(emp);
            }
        }
        return matchingEmployees;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    // 🔹 **This method ensures IDs are assigned properly**
    public int getNextEmployeeId() {
        int maxId = 0;
        for (Employee emp : employees) {
            if (emp.getEmployeeId() > maxId) {
                maxId = emp.getEmployeeId();
            }
        }
        return maxId + 1; // Increment the highest ID found
    }
}
