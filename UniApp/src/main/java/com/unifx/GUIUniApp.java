package com.unifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.regex.Pattern;

import com.unifx.model.Database;
import com.unifx.model.Student;
import com.unifx.model.Subject;

public class GUIUniApp extends Application {

    private List<Student> students = Database.loadAllStudents();

    private void saveStudents() {
        Database.saveAllStudents(students);
    }

    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@university\\.com$";
        return Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean isValidPassword(String password) {
        String PASSWORD_REGEX = "^[A-Z][a-zA-Z]{5,}[0-9]{3,}$";
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GUIUniApp");

        VBox welcomeLayout = new VBox();
        Button adminButton = new Button("Admin");
        Button studentButton = new Button("Student");
        Button exitButton = new Button("Exit");    
        
        VBox registrationLayout = new VBox();
        TextField emailFieldReg = new TextField();
        PasswordField passwordFieldReg = new PasswordField();
        Button registerButtonReg = new Button("Register");

        VBox loginLayout = new VBox();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Scene loginScene = new Scene(loginLayout, 300, 200);

        VBox subjectEnrolmentLayout = new VBox();
        Button enrolButton = new Button("Enroll");
        Scene subjectEnrolmentScene = new Scene(subjectEnrolmentLayout, 300, 200);


         studentButton.setOnAction(event -> {
            primaryStage.setScene(registrationScene);
        });

        adminButton.setOnAction(event -> handleAdminLogin());
        
        exitButton.setOnAction(event -> {
            primaryStage.close();
        });

        welcomeLayout.getChildren().addAll(adminButton,studentButton,exitButton);


        registerButtonReg.setOnAction(event -> {
            String emailReg = emailFieldReg.getText();
            String passwordReg = passwordFieldReg.getText();
            primaryStage.setScene(loginScene);


            if (isValidEmail(emailReg) && isValidPassword(passwordReg)) {
                students.add(new Student(emailReg, passwordReg, "Name"));
                saveStudents();
                showAlert("Registration Successful", "You can now login with your credentials.");
                registrationLayout.setVisible(false);
                loginLayout.setVisible(true);
            } else {
                showAlert("Registration Failed", "Invalid email or password format");
            }
        });

        registrationLayout.getChildren().addAll(new Label("Email:"), emailFieldReg, new Label("Password:"), passwordFieldReg, registerButtonReg);

        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            Student currentStudent = null;

            for (Student student : students) {
                if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    currentStudent = student;
                    break;
                }
            }
        
            VBox subjectEnrolmentLayout = new VBox();
            Button enrolButton = new Button("Enrol");
            subjectEnrolmentLayout.getChildren().add(enrolButton);
            Scene subjectEnrolmentScene = new Scene (subjectEnrolmentLayout, 300, 200);    

            if (currentStudent != null) {
                showAlert("Login Successful", "Welcome, " + currentStudent.getName() + "!");
                // Transition to Subject Enrolment
                primaryStage.setScene(subjectEnrolmentScene);
            } else {
                showAlert("Login Failed", "Incorrect email or password");
            }
        });

        loginLayout.getChildren().addAll(new Label("Email:"), emailField, new Label("Password:"), passwordField, loginButton);


        enrolButton.setOnAction(event -> {
            if (currentStudent != null){
            Random random = new Random();
            String subjectId = String.format("%03d", random.nextInt(1000));    
            Subject subject = new Subject(subjectId);
            int mark = random.nextInt(101); // Generate a random mark between 0 and 100
            subject.setMark(mark); // Set the generated mark to the subject
            subject.getGrade();
            currentStudent.getEnrolledSubjects().add(subject);    
            saveStudents();
            showAlert("Enrollment is Succesful. You are now enrolled in Subject ID:" +subjectId);
            }
        }); 
        subjectEnrolmentLayout.getChildren().add(enrolButton);

        
        Scene scene = new Scene(welcomeLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

     private void showAlert(String string) {    //to fix showAlert in enrolButton
    }

    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }
    
    private void showAlert(String message) {
        showAlert("Admin", message); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
