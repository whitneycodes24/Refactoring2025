import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchByIdDialog extends JDialog implements ActionListener {
    
    private EmployeeDetails parent;
    private JTextField searchField;
    private JButton search, cancel;

    public SearchByIdDialog(EmployeeDetails parent) {
        super(parent, "Search Employee by ID", true);
        this.parent = parent;

        setLayout(new BorderLayout());
        add(createSearchPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    //panel for search input field
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Enter Employee ID"));
        
        panel.add(new JLabel("ID:"));
        panel.add(searchField = new JTextField(10));
        
        return panel;
    }

    //panel for Search/Cancel buttons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(search = new JButton("Search"));
        search.addActionListener(this);
        panel.add(cancel = new JButton("Cancel"));
        cancel.addActionListener(this);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchEmployeeById();
        } else if (e.getSource() == cancel) {
            dispose();
        }
    }

    //search employee by ID
    private void searchEmployeeById() {
        try {
            String idText = searchField.getText().trim();
            
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Employee ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);
            Employee employee = parent.getEmployeeDatabase().findEmployeeById(id);

            if (employee != null) {
                JOptionPane.showMessageDialog(this, employee.toString(), "Employee Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
