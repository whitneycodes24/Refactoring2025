import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeDetails extends JFrame implements ActionListener {
    private EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    
    private JButton add, modify, delete, searchById, searchBySurname, listAll;

    public EmployeeDetails() {
        super("Employee Management System");
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new FlowLayout());

        add(add = new JButton("Add Employee"));
        add.addActionListener(this);
        add(modify = new JButton("Modify Employee"));
        modify.addActionListener(this);
        add(delete = new JButton("Delete Employee"));
        delete.addActionListener(this);
        add(searchById = new JButton("Search by ID"));
        searchById.addActionListener(this);
        add(searchBySurname = new JButton("Search by Surname"));
        searchBySurname.addActionListener(this);
        add(listAll = new JButton("List All Employees"));
        listAll.addActionListener(this);

        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            new AddRecordDialog(this);
        } 
    }

    public EmployeeDatabase getEmployeeDatabase() {
        return employeeDatabase;
    }

    public static void main(String[] args) {
        new EmployeeDetails();
    }
}
