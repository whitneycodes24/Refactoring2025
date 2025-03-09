import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeSummaryDialog extends JDialog implements ActionListener {
    private EmployeeDatabase employeeDatabase;
    private JButton backButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeSummaryDialog(JFrame parent, EmployeeDatabase employeeDatabase) {
        super(parent, "Employee Summary", true);
        this.employeeDatabase = employeeDatabase;
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        String[] columnNames = {"ID", "PPS Number", "Surname", "First Name", "Gender", "Department", "Salary", "Full Time"};
        tableModel = new DefaultTableModel(columnNames, 0);

        loadEmployeeData();

        employeeTable = new JTable(tableModel);
        employeeTable.setEnabled(false);
        employeeTable.setAutoCreateRowSorter(true);

        // Center align some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        employeeTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        employeeTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        employeeTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(backButton = new JButton("Back"));
        backButton.addActionListener(this);
        return panel;
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeDatabase.getAllEmployees();
        for (Employee emp : employees) {
            Object[] rowData = {
                    emp.getEmployeeId(),
                    emp.getPps(),
                    emp.getSurname(),
                    emp.getFirstName(),
                    emp.getGender(),
                    emp.getDepartment(),
                    emp.getSalary(),
                    //emp.getFullTime() ? "Yes" : "No"
            };
            tableModel.addRow(rowData);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
        }
    }
}
