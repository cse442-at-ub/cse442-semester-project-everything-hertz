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
                "{\"name\":\"Engineering Week\",\"organizer\":\"UB SEAS\",\"category\":\"Education\",\"date_and_time\":\"2020-05-06 09:00:00\",\"location\":\"Student Union\",\"points\":3,\"description\":\"Participate in engineering-related events.\",\"event_number\":2}," +
                "{\"name\":\"Power Yoga\",\"organizer\":\"UB Athletics\",\"category\":\"Sports\",\"date_and_time\":\"2020-05-12 11:00:00\",\"location\":\"Alumni Arena\",\"points\":2,\"description\":\"Participate in Power Yoga with an experienced and licensed instructor.\",\"event_number\":3}," +
                "{\"name\":\"Indoor Cycling\",\"organizer\":\"UB Recreation Services\",\"category\":\"Sports\",\"date_and_time\":\"2020-05-08 16:15:00\",\"location\":\"125 Alumni Arena Spinning Room\",\"points\":2,\"description\":\"A high energy class that is centered on achieving an anaerobic workout. A variety of jumps, climbs, sprints and hills will be utilized to get your heart rate elevated and make you break a sweat!\",\"event_number\":6}," +
                "{\"name\":\"Snacking Tuesdays\",\"organizer\":\"Health Promotion\",\"category\":\"Sports\",\"date_and_time\":\"2020-05-05 00:00:00\",\"location\":\"114 Student Union (SU)\",\"points\":1,\"description\":\"Free fruit to help you feel good and stay focused\",\"event_number\":8}," +
                "{\"name\":\"Data Visualization\",\"organizer\":\"Mariah Glass\",\"category\":\"Education\",\"date_and_time\":\"2020-05-15 08:00:00\",\"location\":\"Baird Research Park\",\"points\":4,\"description\":\"This course explores the benefits of using simple graphs to describe and attach meaning to data.\",\"event_number\":10}," +
                "{\"name\":\"Microbiology: Microbiology and Immunology Seminar\",\"organizer\":\"Vicki Evancho\",\"category\":\"Education\",\"date_and_time\":\"2020-06-02 12:15:00\",\"location\":\"2220A JSMBS\",\"points\":3,\"description\":\"\\\"Cytoskeletal architecture and cell morphogenesis in Trypanosoma brucei\\\" Dr. Chris de Graffenried Assistant Professor of Molecular Microbiolgy and Immunology Brown University\",\"event_number\":12}," +
                "{\"name\":\"Oral Biology Seminar Series\",\"organizer\":\"Dental School\",\"category\":\"Education\",\"date_and_time\":\"2020-05-13 12:00:00\",\"location\":\"215 Foster Hall\",\"points\":2,\"description\":\"Oral Biology Seminar\\r\\nDental and Surgical Research in the Military\",\"event_number\":13}," +
                "{\"name\":\"Amazon Virtual Q&A - Acing You\",\"organizer\":\"Career Services\",\"category\":\"Education\",\"date_and_time\":\"2020-05-15 13:00:00\",\"location\":\"Career Services\",\"points\":3,\"description\":\"Amazon wants you to ace your resume and interview! Join recruiters from Amazon in this virtual question and answer session to learn their insider tips on how to show off your best self on your resume and during an interview.\",\"event_number\":14}," +
                "{\"name\":\"Eastman's Organists' Day\",\"organizer\":\"Music Department\",\"category\":\"Music\",\"date_and_time\":\"2020-05-23 19:30:00\",\"location\":\"Lippes Concert Hall\",\"points\":7,\"description\":\"This annual concert features a selection of advanced, distinguished students from the renowned organ studio at the Eastman School of Music.\",\"event_number\":15},]}";
        String s2 = "{\"success\":1,\"data\":[]}";
        Hashtable<String, Integer> output1 = new Hashtable<String, Integer>();
        output1.put("Music",2);
        output1.put("Education",5);
        output1.put("Sports",3);

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
