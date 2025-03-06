import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeeManager extends JFrame implements ActionListener {
    private EmployeeDatabase employeeDatabase;
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton add, searchById, listAll;

    private static final String[] GENDER_OPTIONS = {"", "M", "F"};
    private static final String[] DEPARTMENT_OPTIONS = {"", "Administration", "Production", "Transport", "Management"};
    private static final String[] FULLTIME_OPTIONS = {"", "Yes", "No"};

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
        panel.add(searchById = new JButton("Search by ID"));
        searchById.addActionListener(this);
        panel.add(listAll = new JButton("List All Employees"));
        listAll.addActionListener(this);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            addEmployee();
        } else if (e.getSource() == searchById) {
            searchById();
        } else if (e.getSource() == listAll) {
            listAllEmployees();
        }
    }

    private void addEmployee() {
        try {
            String idText = idField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id = Integer.parseInt(idText);

            String pps = ppsField.getText().trim().toUpperCase();
            String surname = surnameField.getText().trim();
            String firstName = firstNameField.getText().trim();

            String genderStr = genderCombo.getSelectedItem().toString();
            if (genderStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            char gender = genderStr.charAt(0);

            String department = departmentCombo.getSelectedItem() != null ? departmentCombo.getSelectedItem().toString() : "Unknown";

            String salaryText = salaryField.getText().trim();
            if (salaryText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Salary field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salary = Double.parseDouble(salaryText);

            boolean fullTime = fullTimeCombo.getSelectedItem().toString().equals("Yes");

            employeeDatabase.addEmployee(id, pps, surname, firstName, gender, department, salary, fullTime);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format in ID or Salary.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchById() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
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

    private void listAllEmployees() {
        StringBuilder list = new StringBuilder("All Employees:\n");
        for (Employee emp : employeeDatabase.getAllEmployees()) {
            list.append(emp.toString()).append("\n----------------------\n");
        }
        JOptionPane.showMessageDialog(this, list.toString());
    }

    public static void main(String[] args) {
        new EmployeeManager();
    }
}
