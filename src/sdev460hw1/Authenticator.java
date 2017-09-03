package sdev460hw1;

/**
 * Created by Christian on 9/3/17.
 */
public class Authenticator {

    /**
     * @return isValid true for valid login credentials
     */
    public static boolean loginIsValid(String username, String password) {

        boolean isValid = false;
        // User and password are hardcoded for this example app
        if (username.equalsIgnoreCase("sdev460admin")
                && password.equals("460!pass")) {
            isValid = true;
        }

        return isValid;
    }

}
