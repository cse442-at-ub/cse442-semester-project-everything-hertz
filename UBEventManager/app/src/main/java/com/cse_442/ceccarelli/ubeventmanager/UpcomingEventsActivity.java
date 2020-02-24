package com.cse_442.ceccarelli.ubeventmanager;

import android.graphics.Color;
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

import org.w3c.dom.Text;

public class UpcomingEventsActivity extends AppCompatActivity {

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

        LinearLayout scroll = (LinearLayout) findViewById(R.id.linlayout);
            TextView t1 = new TextView(UpcomingEventsActivity.this);
            t1.setText("Spring Fest");
            t1.setMinHeight(150);
            t1.setTextSize(30);
            scroll.addView(t1);

         t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nFree concert for students");
        t1.setMinHeight(250);
        t1.setTextSize(20);
        scroll.addView(t1);

            TextView v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Engineering Week");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Student Union \nDate: 2-21-20 \nMeet and greet for engineering students and companies");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);

        v = new TextView(UpcomingEventsActivity.this);
        v.setMinimumHeight(1);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        scroll.addView(v);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Power Yoga");
        t1.setMinHeight(150);
        t1.setTextSize(30);
        scroll.addView(t1);

        t1 = new TextView(UpcomingEventsActivity.this);
        t1.setText("Location: Alumni Arena \nDate: 2-21-20 \nYoga class with a licensed instructor");
        t1.setMinHeight(200);
        t1.setTextSize(20);
        scroll.addView(t1);



        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         */
    }

}
