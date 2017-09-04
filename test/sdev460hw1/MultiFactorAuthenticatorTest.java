package sdev460hw1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Christian on 9/3/17.
 */
public class MultiFactorAuthenticatorTest {

    @Test
    public void multiFactorAuthCodeIsInRange() throws Exception {

        int generatedCode = MultiFactorAuthenticator.generateMultiFactorAuthCode();

        assertTrue(generatedCode >= 100000);
        assertTrue(generatedCode <= 999999);

    }

}