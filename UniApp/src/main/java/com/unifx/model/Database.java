package com.unifx.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String STUDENTS_DATA_FILE = "students.data";

    public static void saveStudentData(Student student) {
        List<Student> students = loadAllStudents(); // Load all students from the file

        // Update the student data in the list
        students.removeIf(s -> s.getEmail().equals(student.getEmail())); // Remove the old data of the student
        students.add(student); // Add the updated student data

        // Save the updated list back to the file
        try (FileOutputStream fos = new FileOutputStream(STUDENTS_DATA_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e);
            e.printStackTrace(); // Print the entire stack trace
        }
    }

    public static List<Student> loadAllStudents() {
    List<Student> students = new ArrayList<>();
    File file = new File(STUDENTS_DATA_FILE);
        if (!file.exists() || file.length() == 0) {
            return students; // Return an empty list if the file doesn't exist or is empty
        }
        try (FileInputStream fis = new FileInputStream(STUDENTS_DATA_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e);
            e.printStackTrace(); // Print the entire stack trace for more details
        }
        return students;
    }

    public static void saveAllStudents(List<Student> students) {
        try (FileOutputStream fos = new FileOutputStream(STUDENTS_DATA_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e);
            e.printStackTrace(); // Print the entire stack trace
        }
    }

    public static void clearAllStudents() {
        try (FileOutputStream fos = new FileOutputStream(STUDENTS_DATA_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new ArrayList<Student>());
        } catch (IOException e) {
            System.out.println("Error clearing student data: " + e);
            e.printStackTrace(); // Print the entire stack trace
        }
    }


}