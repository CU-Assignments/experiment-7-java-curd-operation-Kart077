EASY:

import java.sql.*;

public class MySQLConnectionExample {
    public static void main(String[] args) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";

        // SQL query to fetch all records from the employee table
        String query = "SELECT EmpID, Name, Salary FROM employee";

        // Establishing connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Displaying results
            System.out.println("EmpID | Name | Salary");
            System.out.println("-------------------------");
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(empId + " | " + name + " | " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


MEDIUM:

import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            boolean exit = false;
            while (!exit) {
                System.out.println("\nProduct Management System");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1 -> addProduct(conn, scanner);
                    case 2 -> viewProducts(conn);
                    case 3 -> updateProduct(conn, scanner);
                    case 4 -> deleteProduct(conn, scanner);
                    case 5 -> exit = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection conn, Scanner scanner) {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        
        String query = "INSERT INTO product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void viewProducts(Connection conn) {
        String query = "SELECT * FROM product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nProductID | ProductName | Price | Quantity");
            System.out.println("-------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") + " | " + rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Connection conn, Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();
        
        String query = "UPDATE product SET Price = ?, Quantity = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setDouble(1, price);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Connection conn, Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        
        String query = "DELETE FROM product WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }
}


HARD:

import java.sql.*;
import java.util.Scanner;

// Model Class
class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

// Controller Class
class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public void addStudent(Student student) {
        String query = "INSERT INTO student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewStudents() {
        String query = "SELECT * FROM student";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nStudentID | Name | Department | Marks");
            System.out.println("------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("StudentID") + " | " + rs.getString("Name") + " | " + rs.getString("Department") + " | " + rs.getDouble("Marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(int studentID, double marks) {
        String query = "UPDATE student SET Marks = ? WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setDouble(1, marks);
            pstmt.setInt(2, studentID);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentID) {
        String query = "DELETE FROM student WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, studentID);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// View Class
public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentController controller = new StudentController();
        
        boolean exit = false;
        while (!exit) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    controller.addStudent(new Student(id, name, department, marks));
                }
                case 2 -> controller.viewStudents();
                case 3 -> {
                    System.out.print("Enter Student ID to update: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter new Marks: ");
                    double marks = scanner.nextDouble();
                    controller.updateStudent(id, marks);
                }
                case 4 -> {
                    System.out.print("Enter Student ID to delete: ");
                    int id = scanner.nextInt();
                    controller.deleteStudent(id);
                }
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
