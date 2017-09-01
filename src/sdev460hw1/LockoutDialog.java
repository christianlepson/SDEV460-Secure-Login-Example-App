/*
 * Christian Lepson
 */

package sdev460hw1;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * LockoutDialog.java is a dialog that alerts the user that
 * they are currently locked out of the system
 */
public class LockoutDialog {

    private static long secondsRemaining;
    private static Timer timer;
    private static Stage window;
    private static Text countdownText;

    private LockoutDialog() {}

    public static void display() {

        window = new Stage();
        window.setTitle("User Lockout");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setOnCloseRequest(e -> {
            e.consume();
            System.exit(0);
        });

        Text lockoutText = new Text("You have exceeded the maximum number of login attempts.\n" +
                "You are forbidden from signing in for " +
                Lockout.LOCKOUT_TIME_IN_MINUTES +
                " minutes.");
        lockoutText.setWrappingWidth(350);
        lockoutText.setFill(Color.FIREBRICK);

        countdownText = new Text("Countdown text");
        countdownText.setFill(Color.CADETBLUE);
        beginTimer();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(lockoutText, countdownText);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);

        window.showAndWait();
    }

    /**
     * Starts a countdown timer that tells the user how long until
     * lockout period is over
     */
    private static void beginTimer() {
        long lastFailedAttempt = Lockout.getLastFailedAttemptTimestamp();
        long lockoutPeriod = Lockout.LOCKOUT_TIME_IN_MILLISECONDS;
        long now = System.currentTimeMillis();
        long millisecondsRemaining = (lockoutPeriod - (now - lastFailedAttempt) );
        secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisecondsRemaining);
        String initialCountDownMessage = getCountdownMessage(secondsRemaining);
        countdownText.setText(initialCountDownMessage);
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if (secondsRemaining <= 0) {
                        countdownText.setText("Wait time over. You may now log in.");
                        Lockout.resetFailedAttempts();
                        timer.cancel();
                        window.close();
                    } else {
                        String countdownMessage = getCountdownMessage(secondsRemaining);
                        countdownText.setText(countdownMessage);
                        secondsRemaining--;
                    }
                });
            }
        }, delay, period);
    }

    /**
     * Retrieves a formatted message for the countdown timer
     * @param seconds total seconds that the user must wait
     * @return formatted message for countdown timer display
     */
    private static String getCountdownMessage(long seconds) {
        int minutesLeft = (int) seconds / 60;
        int secondsLeft = (int) seconds % 60;
        String message = minutesLeft + " minutes and " + secondsLeft + " seconds remaining.";
        return message;
    }

}
