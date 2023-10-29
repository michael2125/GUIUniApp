package com.unifx.model;

import utils.In;
import java.util.regex.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Student implements Serializable {
    private String email;
    private String password;
    private String name;
    private String studentID;
    private List<Subject> enrolledSubjects; // List to keep track of enrolled subjects
    private static final long serialVersionUID = 1L;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@university\\.com$";
    private static final String PASSWORD_REGEX = "^[A-Z][a-zA-Z]{5,}[0-9]{3,}$";

    // Constructor
    public Student(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enrolledSubjects = new ArrayList<>();
        this.studentID = generateStudentID(); // Generate a new student ID upon registration
    }

    private String generateStudentID() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generate a random 6-digit ID
    }
    
    public String getStudentID() {
        return studentID;
    }

    public static void studentMenu() {
        while (true) {
            System.out.print("\u001B[36m"); // Set text color to light blue
            System.out.print("\tStudent System (l/r/x): ");
            System.out.print("\u001B[0m"); // Reset text color to default
            String input = In.nextLine();
            if (input.isEmpty()) {
                System.out.println("\033[31m\tInvalid choice. Try again.\033[0m");
                continue;
            }
            char choice = input.charAt(0);
            switch (choice) {
                case 'L':
                case 'l':
                    login();
                    break;
                case 'R':
                case 'r':
                    register();
                    break;
                case 'X':
                case 'x':
                    return;
                default:
                    System.out.println("\033[31m\tInvalid choice. Try again.\033[0m");
            }
        }
    }

    public static void login() {
        while (true) {
            System.out.println("\033[32m\tStudent Sign In\033[0m");  // Green color
            System.out.print("\tEmail: ");
            String email = In.nextLine();
            System.out.print("\tPassword: ");
            String password = In.nextLine();
    
            if (!isValidEmail(email) || !isValidPassword(password)) {
                System.out.println("\033[31m\tIncorrect email or password format\033[0m");  // Red color
                continue;  // Go back to the start of the loop
            }
    
            System.out.println("\033[33m\temail and password formats acceptable\033[0m");  // Yellow color
    
            // Validate email and password against the data in "students.data"
            List<Student> students = Database.loadAllStudents();
            for (Student student : students) {
                if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    studentCourseMenu(student);  // Here, we're passing the student object
                    return;
                }
            }
            System.out.println("\033[31m\tStudent does not exist\033[0m");  // Red color
            break;  // Exit the loop and go back to the student system menu
        }
    }

    public static void register() {
        System.out.println("\033[32m\tStudent Sign Up\033[0m");
        System.out.print("\tEmail: ");
        String email = In.nextLine();
        System.out.print("\tPassword: ");
        String password = In.nextLine();
    
        // Validate email and password formats
        if (!isValidEmail(email) || !isValidPassword(password)) {
            System.out.println("\033[31m\tIncorrect email or password format!\033[0m");
            return;
        }
    
        // Check if student with the same email already exists
        List<Student> students = Database.loadAllStudents();
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                System.out.println("\033[31m\tStudent " + student.getName() + " already exists\033[0m");
                return;
            }
        }
        // Register new student
        System.out.print("\tName: ");
        String name = In.nextLine();
        Student newStudent = new Student(email, password, name);
        students.add(newStudent);
        Database.saveStudentData(newStudent);
        System.out.println("\tEnrolling Student " + name);
    }

    private static boolean isValidEmail(String email) {
        // Email regex ensures that the dot (.), the (@) and the (university.com) are present
        return email.matches("^[a-zA-Z0-9._%+-]+@university\\.com$");
    }

    private static boolean isValidPassword(String password) {
        // Password regex ensures that entered passwords are:
        // - Start with upper case
        // - Minimum 6 letters
        // - Followed by minimum 3-digits
        return password.matches("^[A-Z][a-zA-Z]{5,}\\d{3,}$");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String newPassword){            //new code added by Sid       
        this.password=newPassword;
    }

    private static void studentCourseMenu(Student currentStudent) {
        while (true) {
            System.out.print("\u001B[36m"); // Set text color to light blue
            System.out.print("\tStudent Course Menu (c/e/r/s/x): ");
            System.out.print("\u001B[0m"); // Reset text color to default
            String input = In.nextLine();
            if (input.isEmpty()) {
                System.out.println("\t\u001B[31mInvalid choice. Try again.\u001B[0m");
                continue;
            }
            char choice = input.charAt(0);
            switch (choice) {
                case 'C':
                case 'c':
                    changePassword(currentStudent);
                    break;
                case 'E':
                case 'e':
                    enrollSubject(currentStudent);
                    break;
                case 'R':
                case 'r':
                    removeSubject(currentStudent);
                    break;
                case 'S':
                case 's':
                    showSubjects(currentStudent);
                    break;
                case 'X':
                case 'x':
                    return;
                default:
                    System.out.println("\033[31m\tInvalid choice. Try again.\033[0m");
            }
        }
    }

    private static void changePassword(Student currentStudent) {
        System.out.println("\u001B[33m\tUpdating Password\033[0m"); // Yellow
        System.out.print("\tNew Password: ");
        String newPassword = In.nextLine();

        if (Pattern.matches(PASSWORD_REGEX, newPassword)) {
            System.out.print("\tConfirm Password: ");
            String confirmPassword = In.nextLine();

            if (newPassword.equals(confirmPassword)) {
                currentStudent.password = newPassword; // Update the student's password
                Database.saveStudentData(currentStudent); // Save the updated student data to the file
            } else {
                System.out.println("\033[31m\tPassword does not match - try again\033[0m"); //Red
            }
        } else {
            System.out.println("\033[31m\tIncorrect password format!\033[0m");
        }
    }

    private static void enrollSubject(Student currentStudent) {
        if (currentStudent.enrolledSubjects.size() >= 4) {
            System.out.println("\u001B[31m\tStudents are allowed to enrol in 4 subjects only\u001B[0m"); //red
            return;
        }
    
        // Generate a random 3-digit subject ID
        Random random = new Random();
        String subjectId = String.format("%03d", random.nextInt(1000));
    
        // Enroll the student in the subject
        Subject subject = new Subject(subjectId);
        int mark = random.nextInt(101); // Generate a random mark between 0 and 100
        subject.setMark(mark); // Set the generated mark to the subject
        currentStudent.enrolledSubjects.add(subject);
        System.out.print("\u001B[33m"); // Set text color to yellow
        System.out.println("\tYou are now enrolled in subject ID: " + subjectId);
        System.out.println("\tYou are now enrolled in " + currentStudent.enrolledSubjects.size() + " out of 4 subjects");
        System.out.print("\u001B[0m"); // Reset text color to default
        
        // Save updated student data
        Database.saveStudentData(currentStudent);
    }

    private static void removeSubject(Student currentStudent) {
        System.out.print("\tRemove Subject by ID: ");
        String subjectId = In.nextLine();

        boolean removed = currentStudent.enrolledSubjects.removeIf(subject -> subject.getId().equals(subjectId));
        if (removed) {
            System.out.print("\u001B[33m"); // Set text color to yellow
            System.out.println("\tDropping Subject-" + subjectId);
            System.out.println("\tYou are now enrolled in " + currentStudent.enrolledSubjects.size() + " out of 4 subjects");
            System.out.print("\u001B[0m"); // Reset text color to default
            // Save updated student data
            Database.saveStudentData(currentStudent);
        } else {
            System.out.println("\033[31m\tSubject not found in your enrolled subjects.\u001B[0m");
        }
    }

    private static void showSubjects(Student currentStudent) {
        System.out.print("\u001B[33m"); // Yellow
        System.out.println("\tShowing " + currentStudent.enrolledSubjects.size() + " subjects");
        System.out.print("\u001B[0m"); // Default color
        for (Subject subject : currentStudent.enrolledSubjects) {
            System.out.println("\t[ Subject::" + subject.getId() + " -- mark = " + subject.getMark() + " -- grade = " + subject.getGrade() + "]");
        }
    }
    public String getName() {
        return name;
    }

    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public int averageMark() {
        if (enrolledSubjects.isEmpty()) {
            return 0; // Return 0 if no subjects are enrolled
        }
        int totalMarks = 0;
        for (Subject subject : enrolledSubjects) {
            totalMarks += subject.getMark();
        }

        return (int) totalMarks / enrolledSubjects.size();
    }
    
    public String averageGrade() {
        int avgMark = averageMark(); // Use the averageMark() method we added earlier

        // Now, determine the grade based on the average mark
        if (avgMark >= 85) {
            return "HD"; // High Distinction
        } else if (avgMark >= 75) {
            return "D";  // Distinction
        } else if (avgMark >= 65) {
            return "C";  // Credit
        } else if (avgMark >= 50) {
            return "P";  // Pass
        } else {
            return "F";  // Fail
        }
    }

}
