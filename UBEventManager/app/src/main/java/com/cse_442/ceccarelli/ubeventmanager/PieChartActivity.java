package com.cse_442.ceccarelli.ubeventmanager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_activity);
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<Entry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new Entry(9f, 0));
        NoOfEmp.add(new Entry(1f, 1));
        NoOfEmp.add(new Entry(10f, 2));
        NoOfEmp.add(new Entry(4f, 3));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Attendence");

        ArrayList<String> activityTypes = new ArrayList<>();

        activityTypes.add("Athletics");
        activityTypes.add("Clubs");
        activityTypes.add("Speaker Series");
        activityTypes.add("Academic Events");

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