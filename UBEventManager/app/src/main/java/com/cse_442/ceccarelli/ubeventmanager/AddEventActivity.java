package com.cse_442.ceccarelli.ubeventmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button submit = findViewById(R.id.submit_button_add_event);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String organizer = ((EditText) findViewById(R.id.organizer)).getText().toString();
                String category = ((Spinner) findViewById(R.id.category)).getSelectedItem().toString();
                String date_and_time = ((EditText) findViewById(R.id.date_and_time)).getText().toString();
                String location = ((EditText) findViewById(R.id.location)).getText().toString();
                String points = ((Spinner) findViewById(R.id.points)).getSelectedItem().toString();
                String description = ((EditText) findViewById(R.id.description)).getText().toString();

                String retStr = verifyInputs(name, organizer,date_and_time,location,description);

                if (retStr.equals("success")){
                    // Call background worker to add to DB
                    BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            if (output.contains("success")) {
                                Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) "Successfully added event!", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
                                intent.putExtra("coordinator", true);
                                startActivity(intent);

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) "Adding event failed!", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(AddEventActivity.this, AddEventActivity.class);
                                startActivity(intent);

                            }

                        }
                    }).execute("add_event", name, organizer, category, date_and_time, location, points, description);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) retStr, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
    public String verifyInputs(String name, String organizer, String date_and_time, String location, String description){
        if(name.isEmpty()){
            return "Please enter an event name.";
        } else if (organizer.isEmpty()){
            return "Please enter an event organizer.";
        } else if (date_and_time.isEmpty()){
            return "Please enter a date and time.";
        } else if (!date_and_time.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9]")) {
            return "Invalid date and time format.";
        } else if (location.isEmpty()){
            return "Please enter an event location.";
        } else if (description.isEmpty()){
            return "Please enter an event description.";
        } else if (description.length() > 200){
            return "Description too long";
        } else{
            return "success";
        }
    }
}
