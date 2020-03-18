package com.cse_442.ceccarelli.ubeventmanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestMainActivity {

    @Test
    public void testIntToVar(){
        MainActivity ma = new MainActivity();
        String desc = ma.intToVar(2);
        assertEquals(desc, "loc");

        try{
            String error = ma.intToVar(5);
            assertTrue(false);
        } catch (IndexOutOfBoundsException e){}


    }

    @Test
    public void testReformatDate() throws Exception {
        MainActivity ma = new MainActivity();

        // Test PM conversion
        String unformattedDate = "2020-03-26 17:00:00";
        String reformattedDate = "Thu Mar 26, 2020 05:00 PM";
        String reformattedDateTest = ma.reformatDate(unformattedDate);
        assertEquals(reformattedDateTest, reformattedDate);

        // Test AM case
        unformattedDate = "2021-09-12 06:00:00";
        reformattedDate = "Sun Sep 12, 2021 06:00 AM";
        reformattedDateTest = ma.reformatDate(unformattedDate);
        assertEquals(reformattedDateTest, reformattedDate);
    }

    @Test
    public void testGetViewTag(){
        MainActivity ma = new MainActivity();

        String ret = ma.getViewTag(2,3);
        assertEquals(ret, "event_3_desc");

        ret = ma.getViewTag(0,0);
        assertEquals(ret, "event_1_name");
    }
}