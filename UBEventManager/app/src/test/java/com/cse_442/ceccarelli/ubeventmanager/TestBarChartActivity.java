package com.cse_442.ceccarelli.ubeventmanager;

import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBarChartActivity {

    @Test
    public void testParseData(){
        BarGraphActivity activity = new BarGraphActivity();
        String s1 = "{\"success\":1,\"data\":[{\"Month\":5,\"count\":1},{\"Month\":6,\"count\":1}]}";
        String s2 = "{\"success\":1,\"data\":[]}";

        Hashtable<String, Integer> output1 = new Hashtable<String, Integer>();
        output1.put("Jun",1);
        output1.put("Jul",1);
        Hashtable<String, Integer> output2 = new Hashtable<String, Integer>();

        assertEquals(output1.toString(),activity.parseData(s1).toString());
        assertEquals(output2.toString(),activity.parseData(s2).toString());
    }

    @Test
    public void testEventDataSuccess(){
        BarGraphActivity activity = new BarGraphActivity();
        String jstring = "{\"success\":'1',\"data\":[{\"Month\":5,\"count\":1},{\"Month\":6,\"count\":1}]}";
        assertTrue(activity.eventDataSuccess(jstring));
        String jstring2 = "{\"success\":0,\"data\":[]}";
        assertFalse(activity.eventDataSuccess(jstring2));
    }
}
