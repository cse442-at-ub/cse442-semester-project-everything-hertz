package com.cse_442.ceccarelli.ubeventmanager;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpcomingEventsActivity extends AppCompatActivity {

    public String retText = "processing"; // Global text to store return value
    final String url_str = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getUpcomingEvents.php";

    // Separate class to fetch from database asynchronously
    private class FetchData extends AsyncTask<Void, Void, Void> {

        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL(url_str);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String stringBuffer;
                String string = "";
                while ((stringBuffer = bufferedReader.readLine()) != null){
                    string = String.format("%s%s", string, stringBuffer);
                }
                bufferedReader.close();
                result = string;
            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            retText = result;
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            retText = result;
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner categories = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UpcomingEventsActivity.this,
                R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.Categories));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categories.setAdapter(myAdapter);

        // Fetch from database
        new FetchData().execute();
        // Wait for asynchronous fetch success
        while (retText.compareTo("processing") == 0) ;
        //retText now has he JSON value (hopefully)

        LinearLayout scroll = (LinearLayout) findViewById(R.id.linlayout);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(retText);

            // Check if fetch from DB failed - if so, end
            if ((int) jsonObject.get("success") != 1) {
                // No need to update TextViews because they have the error message by default.
                System.out.println("ERROR FETCHING DATA FROM DATABASE");
                return;
            }
            // Iterate through textviews and update
            updateTextViews(scroll, jsonObject, "None");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String getTextfromJSON(JSONObject jsonObject, int event, String info) throws JSONException {

        if ((((JSONObject)((JSONArray) jsonObject.get("data")).get(event)).get(info)) == JSONObject.NULL){
            return "null";
        }
        return ((String)((JSONObject)((JSONArray) jsonObject.get("data")).get(event)).get(info));
    }

    public void updateTextViews(LinearLayout scroll, JSONObject jsonObject, String category) throws JSONException{
        if (category == "None"){category = "null";}
        for (int i = 0; i < ((JSONArray) jsonObject.get("data")).length(); i++){
            if (category == "null"){
                for (int j = 0; j < 4; j++){
                    TextView t1 = new TextView(UpcomingEventsActivity.this);
                    t1.setText(getTextfromJSON(jsonObject, i, intToVar(j)));
                    t1.setMinHeight(getMinHeight(j));
                    t1.setTextSize(getTextSize(j));
                    scroll.addView(t1);
                }

            } else{
                if (getTextfromJSON(jsonObject, i, "category").equals(category)){
                    // Implement to make it work with selecting from spinner
                }

            }


        }
    }

    public int getTextSize(int i){
        if (i == 0){return 30;}
        if (i == 1){return 20;}
        if (i == 2){return 20;}
        if (i == 3){return 20;}
        throw new IndexOutOfBoundsException();
    }

    public int getMinHeight(int i){
        if (i == 0){return 50;}
        if (i == 1){return 20;}
        if (i == 2){return 20;}
        if (i == 3){return 200;}
        throw new IndexOutOfBoundsException();
    }

    public String intToVar(int i){
        if (i == 0){return "name";}
        if (i == 1){return "date";}
        if (i == 2){return "loc";}
        if (i == 3){return "desc";}
        throw new IndexOutOfBoundsException();
    }


}
