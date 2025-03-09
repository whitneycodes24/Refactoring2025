import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomFile {
    private RandomAccessFile file;

    //open the file with read/write access
    public void open(String filePath) throws IOException {
        file = new RandomAccessFile(new File(filePath), "rw");
    }

    //close the file
    public void close() throws IOException {
        if (file != null) {
            file.close();
        }
    }

    //add a new employee to the file
    public void addEmployee(RandomAccessEmployeeRecord employee) throws IOException {
        file.seek(file.length()); // Move to end of file
        employee.write(file);
    }

    //read an employee record by position
    public RandomAccessEmployeeRecord readEmployee(long position) throws IOException {
        file.seek(position * RandomAccessEmployeeRecord.RECORD_SIZE);
        RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
        employee.read(file);
        return employee;
    }

    //find an employee by ID
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

    //update an employee record
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

    //delete an employee by ID
    public boolean deleteEmployee(int id) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            RandomAccessEmployeeRecord employee = new RandomAccessEmployeeRecord();
            employee.read(file);
            if (employee.getEmployeeId() == id) {
                file.seek(position);
                file.writeInt(0); 
                return true;
            }
        }
        return false;
    }
}
