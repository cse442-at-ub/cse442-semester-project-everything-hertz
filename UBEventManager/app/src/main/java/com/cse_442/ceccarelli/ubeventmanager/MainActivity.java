package com.cse_442.ceccarelli.ubeventmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import android.util.Log;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String retText = "processing"; // Global text to store return value
    final String url_str = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442k/getHomePageEvents.php";

    // Separate class to fetch from database asynchronously
    private class FetchData extends AsyncTask<Void, Void, Void>{

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getTotalPoints();
        // textViews holds TextViews corresponding to each box for three upcoming activities
        HashMap<Integer, HashMap<String, TextView>> textViews = buildTextViews();

        // Fetch from database
        new FetchData().execute();
        // Wait for asynchronous fetch success
        while (retText.compareTo("processing") == 0);
        //retText now has he JSON value (hopefully)

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(retText);

            // Check if fetch from DB failed - if so, end
            if ((int) jsonObject.get("success") != 1){
                // No need to update TextViews because they have the error message by default.
                System.out.println("ERROR FETCHING DATA FROM DATABASE");
                return;
            }
            // Iterate through textviews and update
            updateTextViews(jsonObject, textViews);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    public String getViewTag(int i, int j){
        return "event_" + Integer.toString(((int) i)+1) + "_" + intToVar(j);
    }

    public HashMap<Integer, HashMap<String, TextView>> buildTextViews(){
        LinearLayout parent = (LinearLayout) findViewById(R.id.linear);
        HashMap<Integer, HashMap<String, TextView>> textViews = new HashMap<>();
        for (int i = 0; i < 3; i++){
            HashMap<String, TextView> temp = new HashMap<>();
            for (int j = 0; j < 4; j++){
                TextView t = (TextView) parent.findViewWithTag(getViewTag(i,j));
                temp.put(intToVar(j), t);

            }
            textViews.put(i+1, temp);
        }
        return textViews;
    }

    public String getTextfromJSON(JSONObject jsonObject, int event, String info) throws JSONException {
        return ((String)((JSONObject)((JSONArray) jsonObject.get("data")).get(event)).get(info));
    }

    public void updateTextViews(JSONObject jsonObject, HashMap<Integer, HashMap<String, TextView>> textViews) throws Exception{

        for (int event = 0; event < 3; event++){
            for (int i = 0; i < 4; i++){
                String info = intToVar(i);

                // Get data from DB
                String text = getTextfromJSON(jsonObject, event, info);

                // Format the date for users to see more easily
                if (info == "date"){
                    text = reformatDate(text);
                }

                // Add some padding on last textview so users can see full description
                if (event == 2 && info == "desc"){
                    text += "\n\n\n\n";
                }
                textViews.get(event+1).get(info).setText(text);
            }
        }

    }

    public void getTotalPoints() {
        String username = "user1";
        String type = "get_user_points";

        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(String output) {
                updateTotalPointsView(output);
            }
        }).execute(type,username);
    }

    public String extractData(String output){
        System.out.println(output);
        String retVal= "";
        try {
            JSONObject obj = new JSONObject(output);
            System.out.println("1");
            System.out.println(obj);
            JSONObject data = obj.getJSONObject("data");
            System.out.println("2");
            System.out.println(data);
            retVal = data.getString("total_points");
            System.out.println("3");
            System.out.println(retVal);
        }catch(Exception e){
            Log.e("log_tag", "Error parsing data "+e.toString());
        }

        return retVal;
    }
    public void updateTotalPointsView(String output) {
        TextView totalPoints = (TextView) findViewById(R.id.totalPoints);
        totalPoints.setText(extractData(output));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_page) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_qr_reader) {
            Intent intent = new Intent(this, CheckInActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_analytics) {
            Intent intent = new Intent(this, AnalyticsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(this, UpcomingEventsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
