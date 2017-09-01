/*
 * Christian Lepson
 */

package sdev460hw1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * UserProfileScene.java is the scene that appears after
 * a user has successfully logged in.
 */
public class UserProfileScene {
    
    private static GridPane grid;
    
    /**
     * Presents the scene that appears after a user
     * has successfully logged in.
     * @param window the Stage that will present the scene
     */
    public static void show(Stage window) {

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text sceneTitle = new Text("Welcome sdev460admin! Thanks for logging in.");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Scene successScene = new Scene(grid, 500, 400);
        window.setScene(successScene);
        window.show();
    }
    
}
