/*
 * Christian Lepson
 */
package sdev460hw1;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * SDEV460HW1.java Homework 1 Christian Lepson
 */
public class SDEV460HW1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        EventLogger.logApplicationStart();
        
        primaryStage.setTitle("SDEV460 Login");

        primaryStage.setOnCloseRequest(e -> {
            EventLogger.logApplicationEnd();
        });

        boolean userAgreesToTerms = TermsDialog.show();
        if (!userAgreesToTerms) {
            System.exit(0);
        }

        boolean lockoutIsActive = Lockout.isLockoutActive();
        if (lockoutIsActive) {
            LockoutDialog.display();
        }

        LoginScene.show(primaryStage);

    }
    

    public static void main(String[] args) {
        launch(args);
    }

}