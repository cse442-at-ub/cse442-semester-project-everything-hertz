package com.cse_442.ceccarelli.ubeventmanager;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestAddEventActivity {

//    @Test
//    public void testVerifyInputs(){
//        assertTrue(true);
//    }

    @Test
    public void testVerifyInputs(){
        AddEventActivity aea = new AddEventActivity();
        assertEquals("Please enter an event name.", aea.verifyInputs("","org","time","loc","desc"));
        assertEquals("Invalid date and time format.",aea.verifyInputs("name", "org", "time", "loc", "desc"));
        assertEquals("success", aea.verifyInputs("name", "org", "0000-00-00 00:00:00", "loc", "desc"));
    }
}
