/*
 * Christian Lepson
 */

package sdev460hw1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * EventLogger.java logs application events to
 * the log file
 */
public class EventLogger {

    private EventLogger() {}

    public static void logApplicationStart() {
        log("Application started");
    }

    public static void logApplicationEnd() {
        log("Application stopped");
    }

    public static void logSuccessfulUserLogin(String username) {
        log("Successful login for user: " + username);
    }

    public static void logFailedUserLoginAttempt(String username) {
        log("Failed login attempt for user: " + username);
    }

    private static void log (String message) {

        String logMessage = getFormattedMessage(message);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter("log.txt", true));
            writer.write(logMessage);
        }
        catch (IOException io) {
            System.out.println("File IO Exception" + io.getMessage());
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException io) {
                System.out.println("Issue closing the File." + io.getMessage());
            }
        }
    }

    private static String getFormattedMessage(String message) {
        String date = getFormattedDate();
        long timestamp = System.currentTimeMillis();

        String formattedMessage = date + " " + timestamp + " - " + message +
                System.lineSeparator();
        return formattedMessage;
    }

    public static String getFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-d hh:mm:ssa");
        String date = now.format(formatter);
        return date;
    }

}