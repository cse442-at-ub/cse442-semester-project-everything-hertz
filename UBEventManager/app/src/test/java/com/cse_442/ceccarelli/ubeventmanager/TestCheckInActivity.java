package com.cse_442.ceccarelli.ubeventmanager;

import org.json.JSONException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONObject;

public class TestCheckInActivity {


    @Test
    public void testValidResult() throws JSONException {
        CheckInActivity activity = new CheckInActivity();
        JSONObject json = new JSONObject();
        json.put("event_id",5);
        boolean desc = activity.validResult(json);
        assertTrue(desc);

        json = new JSONObject("{}");
        desc = activity.validResult(json);
        assertFalse(desc);

        json = new JSONObject();
        json.put("event_id",5);
        json.put("name","hi");
        System.out.println(json.length());
        desc = activity.validResult(json);
        assertFalse(desc);

        json = new JSONObject();
        json.put("event_id","5");
        desc = activity.validResult(json);
        assertFalse(desc);

        json = new JSONObject();
        json.put("event_id","5");
        desc = activity.validResult(json);
        assertFalse(desc);
    }
    @Test
    public void testUpdateCheckInView(){
        CheckInActivity activity = new CheckInActivity();
        String jstring = "{\"success\":'1',\"data\":\"Added attendance for user: for event number 5\"}";
        assertTrue(activity.addCheckInSuccess(jstring));
        String jstring2 = "{\"success\":0,\"data\":\"Added attendance for user: for event number \"}";
        assertFalse(activity.addCheckInSuccess(jstring2));
    }
}
