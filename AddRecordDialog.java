import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddRecordDialog extends JDialog implements ActionListener {
    
    private EmployeeDetails parent;
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton save, cancel;

    private static final String[] GENDER_OPTIONS = {"M", "F"};
    private static final String[] DEPARTMENT_OPTIONS = {"Administration", "Production", "Transport", "Management"};
    private static final String[] FULLTIME_OPTIONS = {"Yes", "No"};

    public AddRecordDialog(EmployeeDetails parent) {
        super(parent, "Add New Employee", true);
        this.parent = parent;

        setLayout(new BorderLayout());
        add(createDetailsPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    // Panel for employee details input
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        panel.add(new JLabel("ID:"));
        panel.add(idField = new JTextField(10));
        idField.setEditable(false);
        idField.setText(String.valueOf(generateNextId()));

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

    // Panel for Save/Cancel buttons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(save = new JButton("Save"));
        save.addActionListener(this);
        panel.add(cancel = new JButton("Cancel"));
        cancel.addActionListener(this);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            addEmployee();
        } else if (e.getSource() == cancel) {
            dispose();
        }
    }

    // Generate next available Employee ID
    private int generateNextId() {
        return parent.getEmployeeDatabase().getAllEmployees().size() + 1;
    }

    // Add employee and validate inputs
    private void addEmployee() {
        try {
            String idText = idField.getText().trim();
            String pps = ppsField.getText().trim().toUpperCase();
            String surname = surnameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String genderStr = genderCombo.getSelectedItem().toString();
            String department = departmentCombo.getSelectedItem().toString();
            String salaryText = salaryField.getText().trim();
            boolean fullTime = fullTimeCombo.getSelectedItem().toString().equals("Yes");

            // Input validation
            if (pps.isEmpty() || surname.isEmpty() || firstName.isEmpty() || salaryText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);
            char gender = genderStr.charAt(0);
            double salary = Double.parseDouble(salaryText);

            if (salary <= 0) {
                JOptionPane.showMessageDialog(this, "Salary must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee newEmployee = new Employee(id, pps, surname, firstName, gender, department, salary, fullTime);
            parent.getEmployeeDatabase().addEmployee(newEmployee);
            JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
