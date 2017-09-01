/*
 * Christian Lepson
 */

package sdev460hw1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * LoginScene.java is a scene that allows users to enter their
 * login credentials
 */
public class LoginScene {
    
    private static GridPane grid;
    private static TextField userTextField;
    private static PasswordField pwBox;
    private static Text errorText;
    
    private LoginScene() {}
    
    /**
     * Presents the scene that allows users to enter their
     * login credentials
     * @param window the Stage that will present the scene
     */
    public static void show(Stage window) {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text sceneTitle = new Text("Welcome. Login to continue.");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Login");
        grid.add(btn, 1, 4);

        btn.setOnAction(e -> {
            if (loginIsValid()) {
                Lockout.resetFailedAttempts();
                Mailer.sendNewMultiFactorAuthCode();
                MultiFactorScene.show(window);
            } else {
                EventLogger.logFailedUserLoginAttempt();
                Lockout.incrementFailedAttempts();

                boolean lockoutIsActive = Lockout.isLockoutActive();
                boolean lockoutWaitTimeReached = Lockout.isLockoutWaitTimeReached();
                if (lockoutIsActive) {
                    grid.getChildren().remove(errorText);
                    LockoutDialog.display();
                } else if (lockoutWaitTimeReached) {
                    Lockout.resetFailedAttempts();
                    Lockout.incrementFailedAttempts();
                    showUnsuccessfulLoginMessage();
                } else {
                    showUnsuccessfulLoginMessage();
                }
            }
        });

        Scene scene = new Scene(grid, 575, 400);
        window.setScene(scene);
        window.show();
    }
    
    /**
     * @return isValid true for valid login credentials
     */
    private static boolean loginIsValid() {
        String user = userTextField.getText();
        String password = pwBox.getText();

        boolean isValid = false;
        // User and password are hardcoded for this example app
        if (user.equalsIgnoreCase("sdev460admin")
                && password.equals("460!pass")) {
            isValid = true;
        }

        return isValid;
    }
    
    /**
     * Alerts the user that their login credentials are
     * invalid
     */
    private static void showUnsuccessfulLoginMessage() {
        String errorMessage = "Please try again. " +
                "Failed attempt " +
                Lockout.getNumFailedAttempts() +
                " of " +
                Lockout.MAX_FAILED_ATTEMPTS +
                ".";

        if (errorText == null) {
            errorText = new Text();
        } else {
            grid.getChildren().remove(errorText);
        }

        grid.add(errorText, 1, 6);
        errorText.setFill(Color.FIREBRICK);
        errorText.setText(errorMessage);
    }

}