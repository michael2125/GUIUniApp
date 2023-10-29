package com.unifx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

import com.unifx.model.Database;
import com.unifx.model.Student;
import com.unifx.model.Admin;

public class Login extends Application {
    public static void show() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(emailField.getText(), passwordField.getText()));
        adminLoginButton.setOnAction(e -> handleAdminLogin());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void handleLogin(String email, String password) {
        List<Student> students = Database.loadAllStudents();
        for (Student student : students) {
            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                EnrolmentDetails.show(student);
                return;
            }
        }
        showAlert("Invalid credentials. Please try again.");
    }

    private void handleAdminLogin() {
        EnrolmentDetails.show(student);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
