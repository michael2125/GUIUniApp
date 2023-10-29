package com.unifx;

import com.unifx.model.Student;
import com.unifx.model.Subject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SubjectEnrolment extends Application {
    private static Student currentStudent;

    public static void show(Student student) {
        currentStudent = student;
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Subject Enrolment Menu");

        Button enrolButton = new Button("Enrol");
        enrolButton.setOnAction(e -> handleEnrolment());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, enrolButton);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Subject Enrolment");
        primaryStage.show();
    }

    private void handleEnrolment() {
        if (currentStudent.getEnrolledSubjects().size() >= 4) {
            showAlert("You are already enrolled in 4 subjects. Cannot enrol in more.");
            return;
        }

        // Generate a random 3-digit subject ID
        String subjectId = String.format("%03d", (int) (Math.random() * 1000));

        Subject subject = new Subject(subjectId);
        int mark = (int) (Math.random() * 101);
        subject.setMark(mark);
        currentStudent.getEnrolledSubjects().add(subject);

        showAlert("Enrolment Successful!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}