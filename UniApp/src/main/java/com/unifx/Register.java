package com.unifx;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Register {

    public static void show() {
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Student Registration");

        VBox registrationLayout = new VBox();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField nameField = new TextField();
        Button registerButton = new Button("Register");

        registerButton.setOnAction(event -> {
            // Add logic to handle registration here
            // Retrieve values from the fields (emailField.getText(), passwordField.getText(), nameField.getText())
            // Create a new Student object and add it to the database
            // You'll need to implement this logic
        });

        registrationLayout.getChildren().addAll(new Label("Email:"), emailField, new Label("Password:"), passwordField, new Label("Name:"), nameField, registerButton);
        Scene registrationScene = new Scene(registrationLayout, 300, 200);

        registrationStage.setScene(registrationScene);
        registrationStage.show();
    }
}
