import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomFile {
    private RandomAccessFile file;

    // Open the fie with read/write access
    public void open(String filePath) throws IOException {
        file = new RandomAccessFile(new File(filePath), "rw");
    }

    // Close the file
    public void close() throws IOException {
        if (file != null) {
            file.close();
        }
    }

    // Add a new employee to the file
    public void addEmployee(RandomAccessEmployeeRecord employee) throws IOException {
        file.seek(file.length()); // Move to end of file
        employee.write(file);
    }

    // Read an employee record by position
    public RandomAccessEmployeeRecord readEmployee(long position) throws IOException {
        file.seek(position * RandomAccessEmployeeRecord.RECORD_SIZE);
        RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
        employee.read(file);
        return employee;
    }

    // Find an employee by ID
    public RandomAccessEmployeeRecord findEmployeeById(int id) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
            employee.read(file);
            if (employee.getEmployeeId() == id) {
                return employee;
            }
        }
        return null;
    }

    // Update an employee record
    public boolean updateEmployee(int id, RandomAccessEmployeeRecord updatedEmployee) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
            employee.read(file);
            if (employee.getEmployeeId() == id) {
                file.seek(position);
                updatedEmployee.write(file);
                return true;
            }
        }
        return false;
    }

    // Delete an employee by ID
    public boolean deleteEmployee(int id) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
            employee.read(file);
            if (employee.getEmployeeId() == id) {
                file.seek(position);
                file.writeInt(0); // Mark as deleted
                return true;
            }
        }
        return false;
    }
}
