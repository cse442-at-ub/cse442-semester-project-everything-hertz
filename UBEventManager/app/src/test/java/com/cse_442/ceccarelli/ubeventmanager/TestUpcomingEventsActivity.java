package com.cse_442.ceccarelli.ubeventmanager;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestUpcomingEventsActivity {

    @Test
    public void testGetTextFromJSON(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();

        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray sub1 = new JSONArray();
            JSONObject sub2 = new JSONObject();

            sub2.put("test1", "Spring Fest");
            sub2.put("test2", JSONObject.NULL);
            sub1.put(0, sub2);
            jsonObject.put("data", sub1);

            assertEquals("Spring Fest", (String) t.getTextfromJSON(jsonObject, 0, "test1"));
            assertEquals("null", (String) t.getTextfromJSON(jsonObject, 0, "test2"));
        } catch (Exception e){
            fail();
            e.printStackTrace();

        }
    }

    @Test
    public void testGetTextSize(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertEquals(18, t.getTextSize(2));
        try{
            int error = t.getTextSize(5);
            assertTrue(false);
        } catch (IndexOutOfBoundsException e){}
    }

    @Test
    public void testGetMinHeight(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertEquals(18, t.getTextSize(2));
        try{
            int error = t.getTextSize(5);
            assertTrue(false);
        } catch (IndexOutOfBoundsException e){}
    }

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
}
