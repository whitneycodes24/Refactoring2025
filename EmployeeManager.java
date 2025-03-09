import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeManager extends JFrame implements ActionListener {
    private EmployeeDatabase employeeDatabase;
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton add, modify, delete, searchById, searchBySurname, listAll;

    private static final String[] GENDER_OPTIONS = {"M", "F"};
    private static final String[] DEPARTMENT_OPTIONS = {"Administration", "Production", "Transport", "Management"};
    private static final String[] FULLTIME_OPTIONS = {"Yes", "No"};

    public EmployeeManager() {
        super("Employee Management System");
        employeeDatabase = new EmployeeDatabase();
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        add(createDetailsPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details:"));

        panel.add(new JLabel("ID:"));
        panel.add(idField = new JTextField(10));
        idField.setEditable(false);

        panel.add(new JLabel("PPS Number:"));
        panel.add(ppsField = new JTextField(10));

        panel.add(new JLabel("Surname:"));
        panel.add(surnameField = new JTextField(10));

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField = new JTextField(10));

        panel.add(new JLabel("Gender:"));
        panel.add(genderCombo = new JComboBox<>(GENDER_OPTIONS));

        panel.add(new JLabel("Department:"));
        panel.add(departmentCombo = new JComboBox<>(DEPARTMENT_OPTIONS));

        panel.add(new JLabel("Salary:"));
        panel.add(salaryField = new JTextField(10));

        panel.add(new JLabel("Full Time:"));
        panel.add(fullTimeCombo = new JComboBox<>(FULLTIME_OPTIONS));

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add = new JButton("Add Employee"));
        add.addActionListener(this);
        panel.add(modify = new JButton("Edit Employee"));
        modify.addActionListener(this);
        panel.add(delete = new JButton("Delete Employee"));
        delete.addActionListener(this);
        panel.add(searchById = new JButton("Search by ID"));
        searchById.addActionListener(this);
        panel.add(searchBySurname = new JButton("Search by Surname"));
        searchBySurname.addActionListener(this);
        panel.add(listAll = new JButton("List All Employees"));
        listAll.addActionListener(this);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            addEmployee();
        } else if (e.getSource() == modify) {
            modifyEmployee();
        } else if (e.getSource() == delete) {
            deleteEmployee();
        } else if (e.getSource() == searchById) {
            searchEmployeeById();
        } else if (e.getSource() == searchBySurname) {
            searchEmployeeBySurname();
        } else if (e.getSource() == listAll) {
            listAllEmployees();
        }
    }

    private void addEmployee() {
        try {
            int id = employeeDatabase.getNextEmployeeId();
            idField.setText(String.valueOf(id));

            String pps = ppsField.getText().trim();
            String surname = surnameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            char gender = genderCombo.getSelectedItem().toString().charAt(0);
            String department = departmentCombo.getSelectedItem().toString();
            double salary = Double.parseDouble(salaryField.getText().trim());
            boolean fullTime = fullTimeCombo.getSelectedItem().toString().equals("Yes");

            Employee employee = new Employee(id, pps, surname, firstName, gender, department, salary, fullTime);
            employeeDatabase.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee Added Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyEmployee() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID to Edit:");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Employee employee = employeeDatabase.findEmployeeById(id);
                if (employee != null) {
                    employee.setSurname(surnameField.getText().trim());
                    employee.setFirstName(firstNameField.getText().trim());
                    employee.setGender(genderCombo.getSelectedItem().toString().charAt(0));
                    employee.setDepartment(departmentCombo.getSelectedItem().toString());
                    employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
                    employee.setFullTime(fullTimeCombo.getSelectedItem().toString().equals("Yes"));
                    JOptionPane.showMessageDialog(this, "Employee Edited Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not Found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEmployee() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID to Delete:");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                boolean deleted = employeeDatabase.deleteEmployee(id);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchEmployeeById() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Employee employee = employeeDatabase.findEmployeeById(id);
                if (employee != null) {
                    JOptionPane.showMessageDialog(this, employee.toString(), "Employee Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchEmployeeBySurname() {
        String surname = JOptionPane.showInputDialog(this, "Enter Employee Surname:");
        List<Employee> employees = employeeDatabase.findEmployeesBySurname(surname);
        if (!employees.isEmpty()) {
            StringBuilder result = new StringBuilder("Employees Found:\n");
            for (Employee emp : employees) {
                result.append(emp.toString()).append("\n----------------------\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No employees found with that surname.");
        }
    }

    private void listAllEmployees() {
        List<Employee> employees = employeeDatabase.getAllEmployees();
        if (!employees.isEmpty()) {
            StringBuilder result = new StringBuilder("All Employees:\n");
            for (Employee emp : employees) {
                result.append(emp.toString()).append("\n----------------------\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No employees available.");
        }
    }

    public static void main(String[] args) {
        new EmployeeManager();
    }
}
