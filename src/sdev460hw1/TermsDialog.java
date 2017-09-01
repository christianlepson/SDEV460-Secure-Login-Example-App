/*
 * Christian Lepson
 */

package sdev460hw1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TermsDialog.java is a dialog that presents the user
 * with the terms and conditions of the application
 */
public class TermsDialog {

    private static boolean userAgrees = false;

    private TermsDialog() {}

    /**
     * Presents the user with a dialog showing the terms and
     * conditions
     * @return true if user agrees to terms, false if user disagrees
     */
    public static boolean show() {
        String termsAndConditions = getTermsAndConditions();
        if (termsAndConditions.isEmpty()) {
            return userAgrees = false;
        }

        Stage window = new Stage();
        window.setTitle("Terms and Conditions");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(300);

        Text terms = new Text(termsAndConditions);
        terms.setWrappingWidth(250);

        Button agreeButton = new Button("Agree");
        agreeButton.setOnAction(e -> {
            userAgrees = true;
            window.close();
        });

        Button disagreeButton = new Button("Disagree");
        disagreeButton.setOnAction(e -> {
            System.exit(0);
        });

        window.setOnCloseRequest(e -> {
            System.exit(0);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(terms, agreeButton, disagreeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);

        window.showAndWait();

        return userAgrees;

    }

    /**
     * Retrieves the terms and conditions from the text file
     * @return the terms and conditions
     */
    private static String getTermsAndConditions() {
        String terms = "";
        try {
            terms = new String(Files.readAllBytes(Paths.get("terms.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Terms and conditions file (terms.txt) was not found");
        }
        return terms;
    }

}