package sdev460hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Christian on 9/3/17.
 */
public class TermsDialogTest {
    @Test
    public void termsAndConditionsNotEmpty() throws Exception {
        String terms = TermsDialog.getTermsAndConditions();
        boolean isTermsEmpty = terms.isEmpty() || terms == "";
        assertFalse(isTermsEmpty);
    }

}