package com.unifx.model;

import utils.In;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Admin {

    public static void adminMenu() {
        while (true) {
            System.out.print("\u001B[36m"); // Set text color to light blue
            System.out.print("\tAdmin System (c/g/p/r/s/x): ");
            System.out.print("\u001B[0m"); // Reset text color to default
            String input = In.nextLine();
            if (input.isEmpty()) {
            System.out.println("\033[31m\tInvalid choice. Try again.\u001B[0m");
            continue;
            }
            char choice = input.charAt(0);
            switch (choice) {
                case 'C':
                case 'c':
                    clearDatabase();
                    break;
                case 'G':
                case 'g':
                    gradeGrouping();
                    break;
                case 'P':
                case 'p':
                    passFailPartition();
                    break;
                case 'R':
                case 'r':
                    removeStudent();
                    break;
                case 'S':
                case 's':
                    viewStudents();
                    break;
                case 'X':
                case 'x':
                    return;
                default:
                    System.out.println("\033[31m\tInvalid choice. Try again.\u001B[0m");
            }
        }
    }

    private static void viewStudents() {
        List<Student> students = Database.loadAllStudents();
        System.out.println("\u001B[33m\tStudent List\u001B[0m");
        if (students.isEmpty()) {
            System.out.println("\t\t< Nothing to Display >");
            return;
        }
        
        for (Student student : students) {
            System.out.println("\t" + student.getName() + " :: " + student.getStudentID() + " --> Email: " + student.getEmail());
        }
    }

        private static void gradeGrouping() {
        List<Student> students = Database.loadAllStudents();
        System.out.println("\u001B[33m\tGrade Grouping\u001B[0m");
        if (students.isEmpty()) {
            System.out.println("\t\t< Nothing to Display >");
            return;
        }

        // Create a map to group students by their average grade
        Map<String, List<Student>> groupedByGrade = new HashMap<>();

        for (Student student : students) {
            String grade = student.averageGrade();
            if (!groupedByGrade.containsKey(grade)) {
                groupedByGrade.put(grade, new ArrayList<>());
            }
            groupedByGrade.get(grade).add(student);
        }
        // Custom comparator for sorting grades
        List<String> sortedGrades = new ArrayList<>(groupedByGrade.keySet());
        Collections.sort(sortedGrades, (grade1, grade2) -> {
            List<String> order = Arrays.asList("F", "P", "C", "D", "HD");
            return order.indexOf(grade1) - order.indexOf(grade2);
        });

        for (String grade : sortedGrades) {
            for (Student student : groupedByGrade.get(grade)) {
                System.out.println("\t" + grade + " --> [" + student.getName() + " :: " + student.getStudentID() + " -- > GRADE: " + student.averageGrade() + " - MARK: " + student.averageMark() + "]");
            }
        }
    }

    private static void passFailPartition() {
        List<Student> students = Database.loadAllStudents();
        System.out.println("\u001B[33m\tPASS/FAIL Partition\u001B[0m");
        if (students.isEmpty()) {
            System.out.println("\tFAIL --> []");
            System.out.println("\tPASS --> []");
            return;
        }
        // Lists to hold students based on pass/fail criteria
        List<Student> passStudents = new ArrayList<>();
        List<Student> failStudents = new ArrayList<>();
    
        for (Student student : students) {
            if (student.averageMark() >= 50) {
                passStudents.add(student);
            } else {
                failStudents.add(student);
            }
        }
    
        System.out.print("\tFAIL --> [");
        for (int i = 0; i < failStudents.size(); i++) {
            Student student = failStudents.get(i);
            System.out.print(student.getName() + " :: " + student.getStudentID() + " --> GRADE: " + student.averageGrade() + " - MARK: " + student.averageMark());
            if (i < failStudents.size() - 1) { //to avoid printing a comma after the details of the last student
                System.out.print(", ");
            }
        }
        System.out.println("]");
    
        System.out.print("\tPASS --> [");
        for (int i = 0; i < passStudents.size(); i++) {
            Student student = passStudents.get(i);
            System.out.print(student.getName() + " :: " + student.getStudentID() + " --> GRADE: " + student.averageGrade() + " - MARK: " + student.averageMark());
            if (i < passStudents.size() - 1) { //to avoid printing a comma after the details of the last student
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void removeStudent() {
        System.out.print("\tRemove by ID: ");
        String studentId = In.nextLine();
    
        List<Student> students = Database.loadAllStudents();
        boolean studentRemoved = false;
    
        for (Student student : students) {
            if (student.getStudentID().equals(studentId)) {
                students.remove(student);
                studentRemoved = true;
                break;
            }
        }
    
        if (studentRemoved) {
            Database.saveAllStudents(students); // Save the updated list back to the file
            System.out.println("\u001B[33m\tRemoving Student " + studentId + " Account\u001B[0m");
        } else {
            System.out.println("\u001B[31m\tStudent " + studentId + " does not exist\u001B[0m");
        }
    }
    

    public static void clearDatabase() {
        System.out.println("\u001B[33m\tClearing students database\u001B[0m");
        System.out.print("\u001B[31m\tAre you sure you want to clear the database (Y)ES/(N)O: \u001B[0m");
        char choice = In.nextChar();
        if (choice == 'Y' || choice == 'y') {
            Database.clearAllStudents();
            System.out.println("\u001B[33m\tStudents data cleared\u001B[0m");
        }
    }

}
