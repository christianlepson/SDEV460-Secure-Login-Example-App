/*
 * Christian Lepson
 */

package sdev460hw1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * MultiFactorScene.java is a multi-factor validation scene
 * that confirms the user's identity
 */
public class MultiFactorScene {
    private static GridPane grid;
    
    /**
     * Presents the multi-factor validation scene that confirms
     * the user's identity
     * @param window the Stage that will present the scene 
     */
    public static void show(Stage window) {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text sceneTitle = 
                new Text("Please enter the 6 digit authentication code sent to your e-mail.");
        grid.add(sceneTitle, 0, 0, 3, 1);

        String errorMessage = "Invalid code. Please try again.";
        Text authErrorText = new Text(errorMessage);
        authErrorText.setFill(Color.FIREBRICK);

        Label enterLabel = new Label("Enter code:");
        grid.add(enterLabel, 0, 1);

        TextField codeTextField = new TextField();
        grid.add(codeTextField, 1, 1);

        Button enterButton = new Button("Enter");
        grid.add(enterButton, 2, 1);
        enterButton.setOnAction(e -> {
            String codeString = codeTextField.getText();
            boolean codeIsValid = MultiFactorAuthenticator.isValidAuthCode(codeString);
            if (codeIsValid) {
                EventLogger.logSuccessfulUserLogin();
                UserProfileScene.show(window);
            } else {
                grid.getChildren().remove(authErrorText);
                grid.add(authErrorText, 1, 2, 2, 1);
            }
        });

        Button requestButton = new Button("Request New Code");
        grid.add(requestButton, 0, 4);
        requestButton.setOnAction(e -> {
            Mailer.sendNewMultiFactorAuthCode();
        });

        Button exitButton = new Button("Back to Login");
        grid.add(exitButton, 2, 4);
        exitButton.setOnAction(e -> {
            LoginScene.show(window);
        });

        Scene scene = new Scene(grid, 575, 400);
        window.setScene(scene);
        window.show();
    }

}
