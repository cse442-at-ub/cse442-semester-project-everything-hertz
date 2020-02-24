package com.cse_442.ceccarelli.ubeventmanager;
import android.os.Bundle;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
public class AnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

//        BarChart pieChart = findViewById(R.id.piechart);

        Button btnPieChart = findViewById(R.id.eventTypeBtn);
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AnalyticsActivity.this, PieChartActivity.class);
                startActivity(I);
            }
        });


        Button btnBarChart = findViewById(R.id.frequencyBtn);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(AnalyticsActivity.this, BarGraphActivity.class);
                startActivity(I);
            }
        });

    }

}
