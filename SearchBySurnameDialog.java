import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class SearchBySurnameDialog extends JDialog implements ActionListener {
    private EmployeeDetails parent;
    private JButton search, cancel;
    private JTextField searchField;

    // Constructor for SearchBySurnameDialog
    public SearchBySurnameDialog(EmployeeDetails parent) {
        setTitle("Search by Surname");
        setModal(true);
        this.parent = parent;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        add(createSearchPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // Create the search panel
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Search by Employee Surname"));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Surname:"));
        inputPanel.add(searchField = new JTextField(15));

        panel.add(inputPanel);
        return panel;
    }

    // Create the button panel
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(search = new JButton("Search"));
        search.addActionListener(this);
        panel.add(cancel = new JButton("Cancel"));
        cancel.addActionListener(this);
        return panel;
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchEmployeeBySurname();
        } else if (e.getSource() == cancel) {
            dispose();
        }
    }

    // Search for an employee by surname
    private void searchEmployeeBySurname() {
        String surname = searchField.getText().trim();
        if (surname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Surname field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Employee> matchingEmployees = parent.getEmployeeDatabase().findEmployeesBySurname(surname);

        if (!matchingEmployees.isEmpty()) {
            StringBuilder result = new StringBuilder("Matching Employees:\n");
            for (Employee emp : matchingEmployees) {
                result.append(emp.toString()).append("\n----------------------\n");
            }
            JOptionPane.showMessageDialog(this, result.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No employees found with that surname.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
