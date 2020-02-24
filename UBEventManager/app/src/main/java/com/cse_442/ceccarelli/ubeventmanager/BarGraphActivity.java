package com.cse_442.ceccarelli.ubeventmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.ValueFormatter;
import java.util.ArrayList;

public class BarGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
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




