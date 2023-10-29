package com.unifx.model;

import utils.In;

public class College {
    public static void main(String[] args) {
        while (true) {
            System.out.print("\u001B[36m"); // Set text color to light blue
            System.out.print("University System: (A)dmin, (S)tudent, or X: ");
            System.out.print("\u001B[0m"); // Reset text color to default
            String input = In.nextLine();
            if (input.isEmpty()) {
                System.out.println("\u001B[31mInvalid choice. Try again.\u001B[0m");
                continue;
            }
            char choice = input.charAt(0);
            switch (choice) {
                case 'S':
                case 's':
                    Student.studentMenu();
                    break;
                case 'A':
                case 'a':
                    Admin.adminMenu(); // This will navigate to the admin menu
                    break;
                case 'X':
                case 'x':
                    System.out.print("\u001B[33m"); // Set text color to yellow
                    System.out.println("Thank You");
                    System.out.print("\u001B[0m"); // Reset text color to default
                    System.exit(0);
                default:
                    System.out.println("\u001B[31mInvalid choice. Try again.\u001B[0m");
            }
        }
    }
}
