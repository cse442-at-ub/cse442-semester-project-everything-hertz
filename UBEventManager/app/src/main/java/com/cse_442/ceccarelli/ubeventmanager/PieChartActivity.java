package com.cse_442.ceccarelli.ubeventmanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class PieChartActivity extends AppCompatActivity {
    public final String LOGGED_IN = "logged_in";
    public final String USERNAME = "username";
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_activity);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PieChartActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        if(preferences.getBoolean(LOGGED_IN,false)){
            username = preferences.getString(USERNAME,"user1");
        }
        else {
            username = "user1";
        }
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(final String output) {

                if(eventDataSuccess(output)) {
                    Hashtable<String, Integer> data = parseData(output);
                    setPieChart(data);

                }
            }
        }).execute("get_event_information",username);


    }

    public boolean eventDataSuccess(String output) {
        try {
            JSONObject obj = new JSONObject(output);
            return obj.getInt("success")==1;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Hashtable<String, Integer> parseData(String output){
        Hashtable<String, Integer> dictionary = new Hashtable<String, Integer>();
        try {
            JSONObject obj = new JSONObject(output);
            JSONArray eventList = obj.getJSONArray("data");
            for( int i = 0; i< eventList.length();i++){
                JSONObject event = (JSONObject) eventList.get(i);
                String category = event.getString("category");
                if(dictionary.get(category) == null){
                    dictionary.put(category,1);
                }else{
                    Integer count = dictionary.get(category)+1;
                    dictionary.remove(category);
                    dictionary.put(category, count);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private void setPieChart(Hashtable<String, Integer> input) {

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<Entry> NoOfEmp = new ArrayList<>();
        ArrayList<String> activityTypes = new ArrayList<>();

        int count = 0;
        for( String key: input.keySet()) {
            String category = key;
            if(key.equals("null")){
                category = "Other";
            }
            activityTypes.add(category);
            NoOfEmp.add(new Entry(input.get(key),count));
            count++;
        }
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Attendence");
//        NoOfEmp.add(new Entry(9f, 0));
//        NoOfEmp.add(new Entry(1f, 1));
//        NoOfEmp.add(new Entry(10f, 2));
//        NoOfEmp.add(new Entry(4f, 3));
//
//
//


//        activityTypes.add("Athletics");
//        activityTypes.add("Clubs");
//        activityTypes.add("Speaker Series");
//        activityTypes.add("Academic Events");

        PieData data = new PieData(activityTypes, dataSet);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setValueTextSize(14);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        pieChart.setData(data);
        pieChart.animateXY(2000, 2000);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("This is the breakdown of your attendence");
    }
}