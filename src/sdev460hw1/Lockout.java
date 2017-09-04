/*
 * Christian Lepson
 */

package sdev460hw1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Lockout.java is responsible for managing the lockout state
 * when a user exceeds the maximum number of failed login attempts
 */
public class Lockout {
    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final int LOCKOUT_TIME_IN_MINUTES = 60;
    public static final long LOCKOUT_TIME_IN_MILLISECONDS =
            TimeUnit.MINUTES.toMillis(LOCKOUT_TIME_IN_MINUTES);
    private static final String LOGIN_ATTEMPTS_FILE_NAME = "loginAttempts.txt";

    /**
     * Determines if the user is currently locked out
     * @return true if user is locked out, false if not
     */
    public static boolean isLockoutActive() {
        boolean numFailedAttemptsExceeded = Lockout.isMaxFailedAttemptsExceeded();
        boolean lockoutTimeWaitReached = Lockout.isLockoutWaitTimeReached();
        return numFailedAttemptsExceeded && !lockoutTimeWaitReached;
    }

    /**
     * Determines if the maximum number of failed attempts
     * has been exceeded
     * @return true if maximum number of failed attempts has been
     * exceeded, false if not
     */
    private static boolean isMaxFailedAttemptsExceeded() {
        int numFailedAttempts = getNumFailedAttempts();
        return numFailedAttempts >= MAX_FAILED_ATTEMPTS;
    }

    /**
     * Determines if the set lockout wait time has passed since the last
     * failed login attempt
     * @return true if lockout wait time has been reached, false if not
     */
    public static boolean isLockoutWaitTimeReached() {
        long lastFailedAttempt = getLastFailedAttemptTimestamp();
        long currentTime = System.currentTimeMillis();
        return currentTime >= lastFailedAttempt + LOCKOUT_TIME_IN_MILLISECONDS;
    }

    /**
     * Returns the number of current failed login attempts
     * @return the number of current failed login attempts
     */
    public static int getNumFailedAttempts() {
        String failedAttemptsFileContents = readFailedAttemptsFile();
        String[] fileLines = failedAttemptsFileContents.split(" ");
        int numFailedAttempts = Integer.parseInt(fileLines[0]);
        return numFailedAttempts;
    }

    /**
     * Returns the timestamp of the last failed login attempt
     * @return the timestamp of the last failed login attempt
     */
    public static long getLastFailedAttemptTimestamp() {
        String failedAttemptsFileContents = readFailedAttemptsFile();
        String[] fileLines = failedAttemptsFileContents.split(" ");
        long lastFailedAttemptTimestamp = Long.parseLong(fileLines[1]);
        return lastFailedAttemptTimestamp;
    }

    /**
     * Reads number of failed logins and latest timestamp from text file
     * @return contents of failed logins text file
     */
    private static String readFailedAttemptsFile() {
        String failedAttempts;
        try {
            failedAttempts = new String(Files.readAllBytes(Paths.get(LOGIN_ATTEMPTS_FILE_NAME)));
        } catch (NoSuchFileException e) {
            System.out.println("Login attempts file (" +
                    LOGIN_ATTEMPTS_FILE_NAME +
                    ") was not found. A new  login attempts file was created.");
            resetFailedAttempts();
            failedAttempts = readFailedAttemptsFile();
        } catch (IOException e) {
            System.out.println("Unable to read file " + LOGIN_ATTEMPTS_FILE_NAME);
            e.printStackTrace();
            failedAttempts = MAX_FAILED_ATTEMPTS + " " + System.currentTimeMillis();
        }
        return failedAttempts;
    }

    /**
     * Increments the current number of failed attempts and writes
     * failed attempt data to text file
     */
    public static void incrementFailedAttempts() {
        int newNumFailedAttempts = getNumFailedAttempts() + 1;
        if (newNumFailedAttempts >= MAX_FAILED_ATTEMPTS - 1) {
            long previousFailedTimestamp = getLastFailedAttemptTimestamp();
            writeToFailedAttemptsFile(newNumFailedAttempts, previousFailedTimestamp);
        } else {
            long currentTimestamp = System.currentTimeMillis();
            writeToFailedAttemptsFile(newNumFailedAttempts, currentTimestamp);
        }
    }

    /**
     * Resets the data contained in the failed attempts text file
     */
    public static void resetFailedAttempts() {
        int numFailedAttempts = 0;
        long currentTimestamp = System.currentTimeMillis();
        writeToFailedAttemptsFile(numFailedAttempts, currentTimestamp);
    }

    /**
     * Writes failed attempt data to failed attempts text file
     * @param numFailedAttempts current number of failed login attempts
     * @param currentTimestamp current system timestamp
     */
    private static void writeToFailedAttemptsFile(int numFailedAttempts, long currentTimestamp) {
        String fileContents = numFailedAttempts + " " + currentTimestamp;

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(LOGIN_ATTEMPTS_FILE_NAME));
            writer.write(fileContents);
        } catch (IOException io) {
            System.out.println("File IO Exception" + io.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException io) {
                System.out.println("Issue closing the File." + io.getMessage());
            }
        }
    }


}