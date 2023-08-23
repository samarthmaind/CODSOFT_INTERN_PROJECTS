//Student management system
//Task 3 of codsoft internship.

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Student {
    String name;
    int rollNumber;
    String grade;
    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }
}
class StudentManagementSystem {
    ArrayList<Student> students = new ArrayList<>();
    public void addStudent(Student student) {
        students.add(student);
    }
    public void removeStudent(Student student) {
        students.remove(student);
    }
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.rollNumber == rollNumber) {
                return student;
            }
        }
        return null; 
    }
    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println("Name: " + student.name + ", Roll Number: " + student.rollNumber + ", Grade: " + student.grade);
        }
    }
}
public class SMS_Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        System.out.println("Choose interface:");
        System.out.println("1. Console-Based Interface");
        System.out.println("2. GUI-Based Interface");
        System.out.print("Enter your choice: ");
        int interfaceChoice = scanner.nextInt();

        if (interfaceChoice == 1) {
            while (true) {
                System.out.println("Options:");
                System.out.println("1. Add Student");
                System.out.println("2. Remove Student");
                System.out.println("3. Search Student");
                System.out.println("4. Display All Students");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
            }
        } else if (interfaceChoice == 2) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new StudentManagementFrame(sms);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
class StudentManagementFrame extends JFrame {
    private StudentManagementSystem sms;
    private JTextField rollNumberField;
    private JTextArea outputArea;

    public StudentManagementFrame(StudentManagementSystem sms) {
        this.sms = sms;
        setTitle("Student Management System");
        setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Roll Number: "));
        rollNumberField = new JTextField(10);
        inputPanel.add(rollNumberField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        inputPanel.add(searchButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                Student student = sms.searchStudent(rollNumber);
                if (student != null) {
                    outputArea.append("Student found - Name: " + student.name + ", Grade: " + student.grade + "\n");
                } else {
                    outputArea.append("Student not found.\n");
                }
            } catch (NumberFormatException e) {
                outputArea.append("Invalid roll number.\n");
            }
        }
    }
}

