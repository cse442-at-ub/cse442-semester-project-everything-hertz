package com.cse_442.ceccarelli.ubeventmanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class BarGraphActivity extends AppCompatActivity {
    public final String LOGGED_IN = "logged_in";
    public final String USERNAME = "username";
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BarGraphActivity.this);
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
                    System.out.println("successful");
                    Hashtable<String, Integer> data = parseData(output);
//                    createBarChart(data);

                }
            }
        }).execute("get_monthly_attendance",username,"2020");
    }

    public Hashtable<String, Integer> parseData(String output) {
        System.out.println(output);
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
        Hashtable<String, Integer> monthCount = new Hashtable<String, Integer>();
        try {
            JSONObject obj = new JSONObject(output);
            JSONArray data = obj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++){
                JSONObject pairing = (JSONObject) data.get(i);
                Integer month = (Integer) pairing.get("Month");
                Integer count = (Integer) pairing.get("count");
                String monthStr = months[month];
                monthCount.put(monthStr,count);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(monthCount);
        return monthCount;
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
    public void createBarChart(Hashtable<String, Integer> data2) {
        BarChart chart = findViewById(R.id.barChart);

        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new BarEntry(5, 0));
        NoOfEmp.add(new BarEntry(1, 1));
        NoOfEmp.add(new BarEntry(2, 2));
        NoOfEmp.add(new BarEntry(1, 3));

        BarDataSet barDataSet = new BarDataSet(NoOfEmp, "No Of Events Attended");

        ArrayList<String> months = new ArrayList<>();
        months.add("Sept");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");


        chart.animateY(2000);

        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        BarData data = new BarData(months, barDataSet);
        data.setValueTextSize(14);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        chart.setData(data);
        chart.getLegend().setEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        //This max should not be hardcoded
        chart.getAxisLeft().setAxisMaxValue(6);
        chart.getAxisLeft().setAxisMinValue(0);
        chart.getAxisLeft().setTextSize(14);
        chart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        chart.getAxisLeft().setLabelCount(5);
        chart.setDescription("");
    }
}




