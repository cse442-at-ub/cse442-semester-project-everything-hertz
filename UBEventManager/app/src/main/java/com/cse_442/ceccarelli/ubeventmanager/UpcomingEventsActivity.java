package com.cse_442.ceccarelli.ubeventmanager;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.AdapterView.OnItemSelectedListener;

public class UpcomingEventsActivity extends AppCompatActivity implements OnItemSelectedListener{

    public String retText = "processing"; // Global text to store return value
    final String url_str = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getUpcomingEvents.php";
    public LinearLayout scroll;
    public JSONObject jsonObject;

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
        categories.setOnItemSelectedListener(this);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UpcomingEventsActivity.this,
                R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.Categories));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categories.setAdapter(myAdapter);

        // Fetch from database
        new FetchData().execute();
        // Wait for asynchronous fetch success
        while (retText.compareTo("processing") == 0) ;
        //retText now has he JSON value (hopefully)

        // Create the linear layout
        scroll = (LinearLayout) findViewById(R.id.linlayout);

        // Create the JSON Object (in case try/catch defaults to catch)
        jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(retText);

            // Check if fetch from DB failed - if so, end
            if ((int) jsonObject.get("success") != 1) {
                // No need to update TextViews because they have the error message by default.
                System.out.println("ERROR FETCHING DATA FROM DATABASE");
                return;
            }
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

    public void updateTextViews(String category){
        try {
            scroll.removeAllViews();
            for (int i = 0; i < ((JSONArray) jsonObject.get("data")).length(); i++) {
                if (getTextfromJSON(jsonObject, i, "category").equals(category) || category.equals("None")) {
//                    TextView spacer = new TextView(UpcomingEventsActivity.this);
//                    spacer.setText("\n");
//                    spacer.setHeight(30);
//                    scroll.addView(spacer);
//                    TextView spacer2 = new TextView(UpcomingEventsActivity.this);
//                    spacer2.setText("\n");
//                    spacer2.setHeight(10);
//                    spacer2.setBackgroundColor(getResources().getColor(R.color.darkGray));
//                    scroll.addView(spacer2);

                    for (int j = 0; j < 4; j++) {
                        TextView t1 = new TextView(UpcomingEventsActivity.this);

//                        setTypeFace(j, t1);
//                        t1.setTextColor(getResources().getColor(getCurrentColor(j)));
                        String text = getTextfromJSON(jsonObject, i, intToVar(j));
//                        if (j == 1){
//                            try {
//                                text = reformatDate(text);
//                            } catch (Exception e){
//                                System.out.println("date format ERROR");
//                            }
//                        }
                        t1.setText(text);
                        t1.setMinHeight(getMinHeight(j));
//                        t1.setTextSize(getTextSize(j));
                        scroll.addView(t1);
                    }

                }

            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

//    public void setTypeFace(int i, TextView t){
//        if (i < 3){
//            t.setTypeface(null, Typeface.BOLD);
//        }
//    }
//
//    public int getCurrentColor(int i){
//        if (i == 0){return R.color.darkRoyalBlue;}
//        if (i == 1){return R.color.darkGray;}
//        if (i == 2){return R.color.darkGray;}
//        if (i == 3){return R.color.darkGray;}
//        throw new IndexOutOfBoundsException();
//    }

    public int getTextSize(int i){
        if (i == 0){return 22;}
        if (i == 1){return 18;}
        if (i == 2){return 18;}
        if (i == 3){return 18;}
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

//    public String reformatDate(String text) throws Exception{
//        String pattern = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        Date date = format.parse(text);
//
//        // Format for all day event
//        // If event is scheduled for midnight, it is an all day event
//        String newPattern;
//        if (date.getHours() == 0) {
//            newPattern = "EEE MMM dd, yyyy";
//        } else {
//            newPattern = "EEE MMM dd, yyyy hh:mm a";
//        }
//
//        SimpleDateFormat newFormat =new SimpleDateFormat(newPattern);
//        text = newFormat.format(date);
//        return text;
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        updateTextViews(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Do nothing
    }


}
