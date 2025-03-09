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
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        panel.add(new JLabel("ID:"));
        panel.add(idField = new JTextField(10));
        idField.setEditable(false);
        generateEmployeeId(); // Automatically generate employee ID

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
        panel.add(modify = new JButton("Modify Employee"));
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

    private void generateEmployeeId() {
        idField.setText(String.valueOf(employeeDatabase.getNextEmployeeId()));
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String pps = ppsField.getText().trim();
            String surname = surnameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            char gender = genderCombo.getSelectedItem().toString().charAt(0);
            String department = departmentCombo.getSelectedItem().toString();
            double salary = Double.parseDouble(salaryField.getText().trim());
            boolean fullTime = fullTimeCombo.getSelectedItem().toString().equals("Yes");

            if (pps.isEmpty() || surname.isEmpty() || firstName.isEmpty() || salary <= 0) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee employee = new Employee(id, pps, surname, firstName, gender, department, salary, fullTime);
            employeeDatabase.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");

            clearFields();
            generateEmployeeId(); // Generate a new ID for the next employee
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyEmployee() {
        String idText = JOptionPane.showInputDialog(this, "Enter Employee ID to modify:");
        if (idText != null) {
            try {
                int id = Integer.parseInt(idText);
                Employee employee = employeeDatabase.findEmployeeById(id);
    
                if (employee != null) {
                    // Create a form panel to display and modify employee data
                    JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
                    panel.setBorder(BorderFactory.createTitledBorder("Modify Employee Details"));
    
                    JTextField ppsField = new JTextField(employee.getPps());
                    JTextField surnameField = new JTextField(employee.getSurname());
                    JTextField firstNameField = new JTextField(employee.getFirstName());
                    JComboBox<String> genderCombo = new JComboBox<>(GENDER_OPTIONS);
                    genderCombo.setSelectedItem(String.valueOf(employee.getGender()));
                    JComboBox<String> departmentCombo = new JComboBox<>(DEPARTMENT_OPTIONS);
                    departmentCombo.setSelectedItem(employee.getDepartment());
                    JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));
                    JComboBox<String> fullTimeCombo = new JComboBox<>(FULLTIME_OPTIONS);
                    fullTimeCombo.setSelectedItem(employee.getFullTime() ? "Yes" : "No");
    
                    // Add fields to panel
                    panel.add(new JLabel("PPS Number:"));
                    panel.add(ppsField);
                    panel.add(new JLabel("Surname:"));
                    panel.add(surnameField);
                    panel.add(new JLabel("First Name:"));
                    panel.add(firstNameField);
                    panel.add(new JLabel("Gender:"));
                    panel.add(genderCombo);
                    panel.add(new JLabel("Department:"));
                    panel.add(departmentCombo);
                    panel.add(new JLabel("Salary:"));
                    panel.add(salaryField);
                    panel.add(new JLabel("Full Time:"));
                    panel.add(fullTimeCombo);
    
                    // Show the panel in a modal dialog
                    int result = JOptionPane.showConfirmDialog(this, panel, "Modify Employee", JOptionPane.OK_CANCEL_OPTION);
                    
                    if (result == JOptionPane.OK_OPTION) {
                        // Save the updated data
                        employee.setPps(ppsField.getText().trim());
                        employee.setSurname(surnameField.getText().trim());
                        employee.setFirstName(firstNameField.getText().trim());
                        employee.setGender(genderCombo.getSelectedItem().toString().charAt(0));
                        employee.setDepartment(departmentCombo.getSelectedItem().toString());
                        employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
                        employee.setFullTime(fullTimeCombo.getSelectedItem().toString().equals("Yes"));
    
                        JOptionPane.showMessageDialog(this, "Employee modified successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void deleteEmployee() {
        String idText = JOptionPane.showInputDialog(this, "Enter Employee ID to delete:");
        if (idText != null) {
            try {
                int id = Integer.parseInt(idText);
                if (employeeDatabase.deleteEmployee(id)) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                    clearFields();
                    generateEmployeeId();
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

     // Clear input fields
     private void clearFields() {
        ppsField.setText("");
        surnameField.setText("");
        firstNameField.setText("");
        salaryField.setText("");
        genderCombo.setSelectedIndex(0);
        departmentCombo.setSelectedIndex(0);
        fullTimeCombo.setSelectedIndex(0);
    }


    private void searchEmployeeById() {
        String idText = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (idText != null) {
            try {
                int id = Integer.parseInt(idText);
                Employee employee = employeeDatabase.findEmployeeById(id);
                if (employee != null) {
                    JOptionPane.showMessageDialog(this, employee.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.");
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
        new EmployeeSummaryDialog(this, employeeDatabase);
    }

    public static void main(String[] args) {
        new EmployeeManager();
    }
}
