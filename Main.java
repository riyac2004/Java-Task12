import java.io.*;
import java.util.Scanner;

// Student class implementing Serializable
class Student implements Serializable {

    // Recommended serialVersionUID
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String course;

    // Sensitive field (will not be serialized)
    private transient String password;

    public Student(int id, String name, String course, String password) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.password = password;
    }

    // Display student details
    public void display() {
        System.out.println("Student ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Course       : " + course);
        System.out.println("Password     : " + password); // Will be null after deserialization
    }
}

public class Main {

    static final String FILE_NAME = "student.ser";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Serialization System =====");
            System.out.println("1. Serialize Student Object");
            System.out.println("2. Deserialize Student Object");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    serializeStudent(sc);
                    break;
                case 2:
                    deserializeStudent();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 3);

        sc.close();
    }

    // Method to serialize student object
    static void serializeStudent(Scanner sc) {

        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            Student student = new Student(id, name, course, password);

            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(student);

            oos.close();
            fos.close();

            System.out.println("Student object serialized successfully!");

        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    // Method to deserialize student object
    static void deserializeStudent() {

        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                System.out.println("Serialized file not found!");
                return;
            }

            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Student student = (Student) ois.readObject();

            ois.close();
            fis.close();

            System.out.println("\n===== Deserialized Student Data =====");
            student.display();

            System.out.println("\nNote: Password is null because it is transient.");

        } catch (IOException e) {
            System.out.println("Error during deserialization: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }
    }
}
