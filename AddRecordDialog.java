/*
 * 
 * This is a dialog for adding new Employees and saving records to file
 * 
 * */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRecordDialog extends JDialog implements ActionListener {
    private EmployeeDatabase employeeDatabase;
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton save, cancel;

    private static final String[] GENDER_OPTIONS = {"", "M", "F"};
    private static final String[] DEPARTMENT_OPTIONS = {"", "Administration", "Production", "Transport", "Management"};
    private static final String[] FULLTIME_OPTIONS = {"", "Yes", "No"};

    public AddRecordDialog(EmployeeDatabase database) {
        this.employeeDatabase = database;
        setTitle("Add New Employee");
        setModal(true);
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        add(createDetailsPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        setSize(500, 300);
        setLocationRelativeTo(null);
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
        panel.add(save = new JButton("Save"));
        save.addActionListener(this);
        panel.add(cancel = new JButton("Cancel"));
        cancel.addActionListener(this);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            addEmployee();
        } else if (e.getSource() == cancel) {
            dispose();
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
            if (salaryText.isEmpty() || !salaryText.matches("\\d+(\\.\\d+)?")) {
                salaryField.setBackground(new Color(255, 150, 150));
                JOptionPane.showMessageDialog(this, "Invalid salary input!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salary = Double.parseDouble(salaryText);

            boolean fullTime = fullTimeCombo.getSelectedItem().toString().equals("Yes");

            Employee newEmployee = EmployeeFactory.createEmployee(id, pps, surname, firstName, gender, department, salary, fullTime);
            employeeDatabase.addEmployee(id, pps, surname, firstName, gender, department, salary, fullTime);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format in ID or Salary.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}







/*public class AddRecordDialog extends JDialog implements ActionListener {
	JTextField idField, ppsField, surnameField, firstNameField, salaryField;
	JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
	JButton save, cancel;
	EmployeeDetails parent;
	// constructor for add record dialog
	public AddRecordDialog(EmployeeDetails parent) {
		setTitle("Add Record");
		setModal(true);
		this.parent = parent;
		this.parent.setEnabled(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane(dialogPane());
		setContentPane(scrollPane);
		
		getRootPane().setDefaultButton(save);
		
		setSize(500, 370);
		setLocation(350, 250);
		setVisible(true);
	}// end AddRecordDialog

	// initialize dialog container
	public Container dialogPane() {
		JPanel empDetails, buttonPanel;
		empDetails = new JPanel(new MigLayout());
		buttonPanel = new JPanel();
		JTextField field;

		empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

		empDetails.add(new JLabel("ID:"), "growx, pushx");
		empDetails.add(idField = new JTextField(20), "growx, pushx, wrap");
		idField.setEditable(false);
		

		empDetails.add(new JLabel("PPS Number:"), "growx, pushx");
		empDetails.add(ppsField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Surname:"), "growx, pushx");
		empDetails.add(surnameField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("First Name:"), "growx, pushx");
		empDetails.add(firstNameField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Gender:"), "growx, pushx");
		empDetails.add(genderCombo = new JComboBox<String>(this.parent.gender), "growx, pushx, wrap");

		empDetails.add(new JLabel("Department:"), "growx, pushx");
		empDetails.add(departmentCombo = new JComboBox<String>(this.parent.department), "growx, pushx, wrap");

		empDetails.add(new JLabel("Salary:"), "growx, pushx");
		empDetails.add(salaryField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Full Time:"), "growx, pushx");
		empDetails.add(fullTimeCombo = new JComboBox<String>(this.parent.fullTime), "growx, pushx, wrap");

		buttonPanel.add(save = new JButton("Save"));
		save.addActionListener(this);
		save.requestFocus();
		buttonPanel.add(cancel = new JButton("Cancel"));
		cancel.addActionListener(this);

		empDetails.add(buttonPanel, "span 2,growx, pushx,wrap");
		// loop through all panel components and add fonts and listeners
		for (int i = 0; i < empDetails.getComponentCount(); i++) {
			empDetails.getComponent(i).setFont(this.parent.font1);
			if (empDetails.getComponent(i) instanceof JComboBox) {
				empDetails.getComponent(i).setBackground(Color.WHITE);
			}// end if
			else if(empDetails.getComponent(i) instanceof JTextField){
				field = (JTextField) empDetails.getComponent(i);
				if(field == ppsField)
					field.setDocument(new JTextFieldLimit(9));
				else
				field.setDocument(new JTextFieldLimit(20));
			}// end else if
		}// end for
		idField.setText(Integer.toString(this.parent.getNextFreeId()));
		return empDetails;
	}

	// add record to file
	public void addRecord() {
		boolean fullTime = false;
		Employee theEmployee;

		if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes"))
			fullTime = true;
		// create new Employee record with details from text fields
		theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(), surnameField.getText().toUpperCase(),
				firstNameField.getText().toUpperCase(), genderCombo.getSelectedItem().toString().charAt(0),
				departmentCombo.getSelectedItem().toString(), Double.parseDouble(salaryField.getText()), fullTime);
		this.parent.currentEmployee = theEmployee;
		this.parent.addRecord(theEmployee);
		this.parent.displayRecords(theEmployee);
	}

	// check for input in text fields
	public boolean checkInput() {
		boolean valid = true;
		// if any of inputs are in wrong format, colour text field and display message
		if (ppsField.getText().equals("")) {
			ppsField.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		if (this.parent.correctPps(this.ppsField.getText().trim(), -1)) {
			ppsField.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		if (surnameField.getText().isEmpty()) {
			surnameField.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		if (firstNameField.getText().isEmpty()) {
			firstNameField.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		if (genderCombo.getSelectedIndex() == 0) {
			genderCombo.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		if (departmentCombo.getSelectedIndex() == 0) {
			departmentCombo.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		try {// try to get values from text field
			Double.parseDouble(salaryField.getText());
			// check if salary is greater than 0
			if (Double.parseDouble(salaryField.getText()) < 0) {
				salaryField.setBackground(new Color(255, 150, 150));
				valid = false;
			}// end if
		}// end try
		catch (NumberFormatException num) {
			salaryField.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end catch
		if (fullTimeCombo.getSelectedIndex() == 0) {
			fullTimeCombo.setBackground(new Color(255, 150, 150));
			valid = false;
		}// end if
		return valid;
	}// end checkInput

	// set text field to white colour
	public void setToWhite() {
		ppsField.setBackground(Color.WHITE);
		surnameField.setBackground(Color.WHITE);
		firstNameField.setBackground(Color.WHITE);
		salaryField.setBackground(Color.WHITE);
		genderCombo.setBackground(Color.WHITE);
		departmentCombo.setBackground(Color.WHITE);
		fullTimeCombo.setBackground(Color.WHITE);
	}// end setToWhite

	// action performed
	public void actionPerformed(ActionEvent e) {
		// if chosen option save, save record to file
		if (e.getSource() == save) {
			// if inputs correct, save record
			if (checkInput()) {
				addRecord();// add record to file
				dispose();// dispose dialog
				this.parent.changesMade = true;
			}// end if
			// else display message and set text fields to white colour
			else {
				JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
				setToWhite();
			}// end else
		}// end if
		else if (e.getSource() == cancel)
			dispose();// dispose dialog
	}// end actionPerformed
}// end class AddRecordDialog*/