package com.unifx;

import com.unifx.model.Student;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EnrolmentDetails extends Application {
    private static Student currentStudent;


    public static void show(Student student) {
        currentStudent = student;
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Enrolment Menu");

        Button enrolButton = new Button("Enrol in Subjects");
        enrolButton.setOnAction(e -> handleEnrolment());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, enrolButton);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Enrolment");
        primaryStage.show();
    }

    private void handleEnrolment() {
        if (currentStudent.getEnrolledSubjects().size() >= 4) {
            showAlert("You are already enrolled in 4 subjects. Cannot enrol in more.");
            return;
        }
        SubjectEnrolment.show(currentStudent);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
