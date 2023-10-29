package com.unifx.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private String id;
    private int mark;
    private String grade;
    private static final long serialVersionUID = 1L;

    // Constructor
    public Subject(String id) {
        this.id = id;
        this.mark = 0; // Default value
        this.grade = calculateGrade(this.mark); // Calculate grade based on mark
    }

    // Getter methods
    public String getId() {
        return id;
    }
    public int getMark() {
        return mark;
    }
    public String getGrade() {
        return grade;
    }
    // Setter methods
    public void setMark(int mark) {
        this.mark = mark;
        this.grade = calculateGrade(mark); // Update grade based on new mark
    }

    public void setGrade(String grade) {
        this.grade = grade;  // new line added by SID
    }

    // Private method to calculate grade based on mark
    private String calculateGrade(int mark) {
        if (mark >= 85) {
            return "HD"; // High Distinction
        } else if (mark >= 75) {
            return "D";  // Distinction
        } else if (mark >= 65) {
            return "C";  // Credit
        } else if (mark >= 50) {
            return "P";  // Pass
        } else {
            return "F";  // Fail
        }
    }
}
