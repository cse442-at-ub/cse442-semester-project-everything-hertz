package com.cse_442.ceccarelli.ubeventmanager;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPieChartActivity {

    @Test
    public void testParseData(){
        PieChartActivity activity = new PieChartActivity();
        String s1 = "{\"success\":1,\"data\":[{\"name\":\"Spring Fest\",\"organizer\":\"UB SA\",\"category\":\"Music\",\"date_and_time\":\"2020-05-05 19:00:00\",\"location\":\"Alumni Arena\",\"points\":5,\"description\":\"Free concerts for UB students featuring Khalid, Cage the Elephant, and Hozier.\",\"event_number\":1}," +
                "{\"name\":\"Engineering Week\",\"organizer\":\"UB SEAS\",\"category\":\"Sports\",\"date_and_time\":\"2020-05-06 09:00:00\",\"location\":\"Student Union\",\"points\":3,\"description\":\"Participate in engineering-related events.\",\"event_number\":2}," +
                "{\"name\":\"Power Yoga\",\"organizer\":\"UB Athletics\",\"category\":\"Sports\",\"date_and_time\":\"2020-05-12 11:00:00\",\"location\":\"Alumni Arena\",\"points\":2,\"description\":\"Participate in Power Yoga with an experienced and licensed instructor.\",\"event_number\":3},]}";
        String s2 = "{\"success\":1,\"data\":[]}";
        System.out.println(s1);

        Hashtable<String, Integer> output1 = new Hashtable<String, Integer>();
        output1.put("Music",1);
        output1.put("Sports",2);

        Hashtable<String, Integer> output2 = new Hashtable<String, Integer>();

        assertEquals(output1.toString(),activity.parseData(s1).toString());
        assertEquals(output2.toString(),activity.parseData(s2).toString());
    }

    @Test
    public void testEventDataSuccess(){
        PieChartActivity activity = new PieChartActivity();
        String jstring = "{\"success\":'1',\"data\":{}}";
        assertTrue(activity.eventDataSuccess(jstring));
        String jstring2 = "{\"success\":0,\"data\":[]}";
        assertFalse(activity.eventDataSuccess(jstring2));
    }
}
