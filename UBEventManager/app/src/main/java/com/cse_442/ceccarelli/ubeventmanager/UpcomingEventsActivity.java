package com.cse_442.ceccarelli.ubeventmanager;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.AdapterView.OnItemSelectedListener;

public class UpcomingEventsActivity extends AppCompatActivity{

    private String retText = "processing"; // Global text to store return value
    private LinearLayout scroll;

    // Separate class to fetch from database asynchronously
    private class FetchData extends AsyncTask<Void, Void, Void> {

        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            String url_str = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getUpcomingEvents.php";
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
            setRetText(result);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            setRetText(result);
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch from database
        new FetchData().execute();
        // Wait for asynchronous fetch success
        while (getRetText().compareTo("processing") == 0) ;
        //retText now has he JSON value (hopefully)

        // Create the linear layout
        setScroll((LinearLayout) findViewById(R.id.linlayout));

        // Create the JSON Object (in case try/catch defaults to catch)
        JSONObject jsonObjectConstruct = new JSONObject();
        try {
            jsonObjectConstruct = new JSONObject(getRetText());

            // Check if fetch from DB failed - if so, end
            if ((int) jsonObjectConstruct.get("success") != 1) {
                // No need to update TextViews because they have the error message by default.
                System.out.println("ERROR FETCHING DATA FROM DATABASE");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Make the JSONObject final
        final JSONObject jsonObject = jsonObjectConstruct;

        // Get search bar
        final EditText searchBar = (EditText) findViewById(R.id.searchBar);

        // Get spinner
        final Spinner categories = (Spinner) findViewById(R.id.spinner);

        // Set up listener for search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

            // Render TextViews with search applied
            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                String search = s.toString();
                String spinnerText = categories.getSelectedItem().toString();
                updateTextViews(jsonObject, spinnerText, search);
            }

            @Override
            public void afterTextChanged(final Editable s) {}
        });

        // Populate spinner with categories
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UpcomingEventsActivity.this,
                R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.Categories));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categories.setAdapter(myAdapter);

        // Set up listener for spinner
        categories.setOnItemSelectedListener(new OnItemSelectedListener() {

            // Render TextViews with category selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String searcher = searchBar.getText().toString();
                updateTextViews(jsonObject, item, searcher);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
    public String getTextfromJSON(JSONObject jsonObject, int event, String info) throws JSONException {

        if ((((JSONObject)((JSONArray) jsonObject.get("data")).get(event)).get(info)) == JSONObject.NULL){
            return "null";
        }
        return ((String)((JSONObject)((JSONArray) jsonObject.get("data")).get(event)).get(info));
    }

    public boolean searchEvents(JSONObject jsonObject, int i, String search){
        try{
            if (getTextfromJSON(jsonObject, i, "name").toLowerCase().contains(search.toLowerCase()) ||
                    getTextfromJSON(jsonObject, i, "desc").toLowerCase().contains(search.toLowerCase()) ||
                    getTextfromJSON(jsonObject, i, "loc").toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            return false;
        } catch (JSONException e){
            return false;
        }

    }

    public void updateTextViews(JSONObject jsonObject, String category, String search){
        try {
            getScroll().removeAllViews();
            for (int i = 0; i < ((JSONArray) jsonObject.get("data")).length(); i++) {
                if ((getTextfromJSON(jsonObject, i, "category").equals(category) || category.equals("None")) && searchEvents(jsonObject, i, search)) {
                    TextView spacer = new TextView(UpcomingEventsActivity.this);
                    spacer.setText("\n");
                    spacer.setHeight(30);
                    getScroll().addView(spacer);
                    TextView spacer2 = new TextView(UpcomingEventsActivity.this);
                    spacer2.setText("\n");
                    spacer2.setHeight(10);
                    spacer2.setBackgroundColor(getResources().getColor(R.color.darkGray));
                    getScroll().addView(spacer2);

                    for (int j = 0; j < 4; j++) {
                        TextView t1 = new TextView(UpcomingEventsActivity.this);

                        setTypeFace(j, t1);
                        t1.setTextColor(getResources().getColor(getCurrentColor(j)));
                        String text = getTextfromJSON(jsonObject, i, intToVar(j));
                        if (j == 1){
                            try {
                                text = reformatDate(text);
                            } catch (Exception e){
                                System.out.println("date format ERROR");
                            }
                        }
                        t1.setText(text);
                        t1.setMinHeight(getMinHeight(j));
                        t1.setTextSize(getTextSize(j));
                        getScroll().addView(t1);
                    }

                }

            }
            TextView spacer = new TextView(UpcomingEventsActivity.this);
            spacer.setText("\n");
            spacer.setHeight(150);
            getScroll().addView(spacer);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean setTypeFace(int i, TextView t){
        if (i >= 0 && i < 3){
            t.setTypeface(null, Typeface.BOLD);
            return true;
        }
        return false;
    }

    public int getCurrentColor(int i){
        if (i == 0){return R.color.darkRoyalBlue;}
        if (i == 1){return R.color.darkGray;}
        if (i == 2){return R.color.darkGray;}
        if (i == 3){return R.color.darkGray;}
        throw new IndexOutOfBoundsException();
    }

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
        if (i == 3){return 0;}
        throw new IndexOutOfBoundsException();
    }

    public String intToVar(int i){
        if (i == 0){return "name";}
        if (i == 1){return "date";}
        if (i == 2){return "loc";}
        if (i == 3){return "desc";}
        throw new IndexOutOfBoundsException();
    }

    public String reformatDate(String text) throws Exception{
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse(text);

        // Format for all day event
        // If event is scheduled for midnight, it is an all day event
        String newPattern;
        if (date.getHours() == 0) {
            newPattern = "EEE MMM dd, yyyy";
        } else {
            newPattern = "EEE MMM dd, yyyy hh:mm a";
        }

        SimpleDateFormat newFormat =new SimpleDateFormat(newPattern);
        text = newFormat.format(date);
        return text;
    }

    public LinearLayout getScroll(){
        return scroll;
    }

    public void setScroll(LinearLayout l){
        scroll = l;
    }

    public String getRetText(){
        return retText;
    }

    public void setRetText(String r){
        retText = r;
    }

}
