import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessEmployeeRecord extends Employee {
    public static final int RECORD_SIZE = 175; // Fixed size for each record in the file

    // Default constructor creates an empty record
    public RandomAccessEmployeeRecord() {
        this(0, "", "", "", '\0', "", 0.0, false);
    }

    // Constructor with parameters
    public RandomAccessEmployeeRecord(int employeeId, String pps, String surname, String firstName, char gender,
                                      String department, double salary, boolean fullTime) {
        super(employeeId, pps, surname, firstName, gender, department, salary, fullTime);
    }

    // Read a record from the RandomAccessFile
    public void read(RandomAccessFile file) throws IOException {
        setEmployeeId(file.readInt());
        setPps(readFixedString(file, 9));
        setSurname(readFixedString(file, 20));
        setFirstName(readFixedString(file, 20));
        setGender(file.readChar());
        setDepartment(readFixedString(file, 20));
        setSalary(file.readDouble());
        setFullTime(file.readBoolean());
    }

    // Write a record to the RandomAccessFile
    public void write(RandomAccessFile file) throws IOException {
        file.writeInt(getEmployeeId());
        writeFixedString(file, getPps(), 9);
        writeFixedString(file, getSurname(), 20);
        writeFixedString(file, getFirstName(), 20);
        file.writeChar(getGender());
        writeFixedString(file, getDepartment(), 20);
        file.writeDouble(getSalary());
        file.writeBoolean(getFullTime());
    }

    // Read string from the file
    private String readFixedString(RandomAccessFile file, int length) throws IOException {
        char[] name = new char[length];
        for (int i = 0; i < name.length; i++) {
            name[i] = file.readChar();
        }
        return new String(name).trim();
    }

    // Writeh string to the file
    private void writeFixedString(RandomAccessFile file, String text, int length) throws IOException {
        StringBuffer buffer = new StringBuffer(text);
        buffer.setLength(length);
        file.writeChars(buffer.toString());
    }
}
