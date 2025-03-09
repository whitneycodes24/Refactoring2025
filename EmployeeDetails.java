import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EmployeeDetails extends JFrame implements ActionListener {
    
    private EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    private File file;

    private JMenuItem open, save, saveAs, create, modify, delete, searchById, searchBySurname, listAll, closeApp;
    private JButton add, edit, deleteButton, displayAll, searchId, searchSurname;
    private JTextField searchByIdField, searchBySurnameField;

    public EmployeeDetails() {
        super("Employee Management System");
        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout());
        add(createSearchPanel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.SOUTH);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Creates menu bar
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(open = new JMenuItem("Open"));
        open.addActionListener(this);
        fileMenu.add(save = new JMenuItem("Save"));
        save.addActionListener(this);
        fileMenu.add(saveAs = new JMenuItem("Save As"));
        saveAs.addActionListener(this);

        JMenu recordMenu = new JMenu("Records");
        recordMenu.add(create = new JMenuItem("Create New Record"));
        create.addActionListener(this);
        recordMenu.add(modify = new JMenuItem("Modify Record"));
        modify.addActionListener(this);
        recordMenu.add(delete = new JMenuItem("Delete Record"));
        delete.addActionListener(this);

        JMenu navigateMenu = new JMenu("Navigate");
        navigateMenu.add(searchById = new JMenuItem("Search by ID"));
        searchById.addActionListener(this);
        navigateMenu.add(searchBySurname = new JMenuItem("Search by Surname"));
        searchBySurname.addActionListener(this);
        navigateMenu.add(listAll = new JMenuItem("List All Records"));
        listAll.addActionListener(this);

        JMenu exitMenu = new JMenu("Exit");
        exitMenu.add(closeApp = new JMenuItem("Close"));
        closeApp.addActionListener(this);

        menuBar.add(fileMenu);
        menuBar.add(recordMenu);
        menuBar.add(navigateMenu);
        menuBar.add(exitMenu);

        return menuBar;
    }

    // Creates search panel
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));

        searchPanel.add(new JLabel("Search by ID:"));
        searchPanel.add(searchByIdField = new JTextField(10));
        searchPanel.add(searchId = new JButton("Go"));
        searchId.addActionListener(this);

        searchPanel.add(new JLabel("Search by Surname:"));
        searchPanel.add(searchBySurnameField = new JTextField(10));
        searchPanel.add(searchSurname = new JButton("Go"));
        searchSurname.addActionListener(this);

        return searchPanel;
    }

    // Creates button panel
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(add = new JButton("Add Record"));
        add.addActionListener(this);
        buttonPanel.add(edit = new JButton("Edit Record"));
        edit.addActionListener(this);
        buttonPanel.add(deleteButton = new JButton("Delete Record"));
        deleteButton.addActionListener(this);
        buttonPanel.add(displayAll = new JButton("List All Records"));
        displayAll.addActionListener(this);

        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeApp) {
            exitApp();
        } else if (e.getSource() == open) {
            openFile();
        } else if (e.getSource() == save) {
            saveFile();
        } else if (e.getSource() == saveAs) {
            saveFileAs();
        } else if (e.getSource() == searchById) {
            searchEmployeeById();
        } else if (e.getSource() == searchBySurname) {
            searchEmployeeBySurname();
        } else if (e.getSource() == add) {
            addNewEmployee();
        } else if (e.getSource() == modify) {
            editEmployeeDetails();
        } else if (e.getSource() == delete) {
            deleteEmployeeRecord();
        }
    }

    // File handling methods
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Employee Records", "dat"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            // Load data from file (To be implemented)
        }
    }

    private void saveFile() {
        if (file == null) {
            saveFileAs();
        } else {
           
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Employee Records", "dat"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            
        }
    }

    // Employee management methods
    private void searchEmployeeById() {
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

    private void addNewEmployee() {
        new AddRecordDialog(this);
    }

    // Provides access to the EmployeeDatabase instance
    public EmployeeDatabase getEmployeeDatabase() {
        return this.employeeDatabase;
    }

    private void editEmployeeDetails() {
        JOptionPane.showMessageDialog(this, "Modify Employee feature not yet implemented.");
    }

    private void deleteEmployeeRecord() {
        JOptionPane.showMessageDialog(this, "Delete Employee feature not yet implemented.");
    }

    private void exitApp() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting?", "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            saveFile();
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new EmployeeDetails();
    }
}
