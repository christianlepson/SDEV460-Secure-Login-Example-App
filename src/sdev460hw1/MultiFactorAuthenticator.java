/*
 * Christian Lepson
 */

package sdev460hw1;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MultiFactorAuthenticator.java is responsible for validation
 * and generation of multi-factor authentication codes.
 */
public class MultiFactorAuthenticator {
    private static int currentAuthCode;

    /**
     * Determines the validity of a user-entered authentication
     * code
     * @param codeString user-entered text containing authorization code
     * to be validated
     * @return true if valid code, false if invalid
     */
    public static boolean isValidAuthCode(String codeString) {
        try {
            int code = Integer.parseInt(codeString);
            return code == currentAuthCode;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Generates a random 6-digit authorization code
     * @return a random 6-digit authorization code
     */
    protected static int generateMultiFactorAuthCode() {
        int min = 100000;
        int max = 999999;
        int randomCode = ThreadLocalRandom.current().nextInt(min, max + 1);
        currentAuthCode = randomCode;
        return currentAuthCode;
    }

}