package com.cse_442.ceccarelli.ubeventmanager;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLogInActivity {

    @Test
    public void testUpdateUserType(){
        LogInActivity la = new LogInActivity();
        assertFalse(la.updateUserType("No results"));
        assertTrue(la.updateUserType("anything else"));
    }
}
