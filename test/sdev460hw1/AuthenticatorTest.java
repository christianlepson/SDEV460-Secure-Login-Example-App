package sdev460hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Christian on 9/3/17.
 */
public class AuthenticatorTest {

    @Test
    public void authenticatorRecognizesInvalidCredentials() throws Exception {
        boolean isValid = Authenticator.loginIsValid("invalid", "invalid");

        assertFalse(isValid);
    }

    @Test
    public void authenticatorRecognizesValidCredentials() throws Exception {
        boolean isValid = Authenticator.loginIsValid("sdev460admin", "460!pass");

        assertTrue(isValid);
    }

}