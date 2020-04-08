package com.cse_442.ceccarelli.ubeventmanager;

import android.widget.TextView;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONException;
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
    public void testSetTextSize(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertEquals(18, t.getTextSize(2));
        try{
            int error = t.getTextSize(5);
            fail();
        } catch (IndexOutOfBoundsException e){}
    }

    @Test
    public void testGetMinHeight(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertEquals(18, t.getTextSize(2));
        try{
            int error = t.getTextSize(5);
            fail();
        } catch (IndexOutOfBoundsException e){}
    }

    @Test
    public void testIntToVar(){
        UpcomingEventsActivity ma = new UpcomingEventsActivity();
        String desc = ma.intToVar(2);
        assertEquals(desc, "loc");

        try{
            String error = ma.intToVar(5);
            fail();
        } catch (IndexOutOfBoundsException e){}


    }

    @Test
    public void testSetTypeFace(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertTrue(t.setTypeFace(2, new TextView(t)));
        assertFalse(t.setTypeFace(6, new TextView(t)));
    }

    @Test
    public void testGetCurrentColor(){
        UpcomingEventsActivity t = new UpcomingEventsActivity();
        assertEquals(R.color.darkRoyalBlue, t.getCurrentColor(0));
        assertEquals(R.color.darkGray, t.getCurrentColor(2));
        try{
            t.getCurrentColor(10);
            fail();
        } catch (IndexOutOfBoundsException e){}

    }

    @Test
    public void testReformatDate() throws Exception {
        UpcomingEventsActivity ma = new UpcomingEventsActivity();

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
    public void testSearchEvents(){
        UpcomingEventsActivity uea = new UpcomingEventsActivity();

        try{
            JSONObject jsonObject = new JSONObject();
            JSONArray sub1 = new JSONArray();
            JSONObject sub2 = new JSONObject();

            sub2.put("name", "Spring Fest");
            sub2.put("loc", "Alumni Arena");
            sub2.put("desc", "Concert series for undergraduates.");
            sub1.put(0, sub2);
            jsonObject.put("data", sub1);

            assertTrue(uea.searchEvents(jsonObject, 0, "spring"));
            assertFalse(uea.searchEvents(jsonObject, 0, "Spring Feast"));
            assertTrue(uea.searchEvents(jsonObject, 0, " arena"));
            assertFalse(uea.searchEvents(jsonObject, 0, "the arena"));
            assertTrue(uea.searchEvents(jsonObject, 0, "Undergraduates"));
            assertFalse(uea.searchEvents(jsonObject, 0, "undergrads"));
        } catch (JSONException e){
            fail();
        }
    }
}
