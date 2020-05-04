package com.cse_442.ceccarelli.ubeventmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class registrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Load school year spinner
        final Spinner spinner = findViewById(R.id.schoolYearSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(registrationActivity.this, R.array.school_years, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //References to edit text views
        final EditText username_tv = findViewById(R.id.usernameRegistrationText);
        final EditText password_tv = findViewById(R.id.passwordRegistrationText);
        final EditText first_name_tv = findViewById(R.id.firstNameRegistrationText);
        final EditText last_name_tv = findViewById(R.id.lastNameRegistrationText);
        final EditText major_tv = findViewById(R.id.majorRegistrationText);

        //Reference to submit button
        Button register_button = findViewById(R.id.registrationSubmitButton);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "register";
                String username = username_tv.getText().toString();
                String password = password_tv.getText().toString();
                String firstName = first_name_tv.getText().toString();
                String lastName = last_name_tv.getText().toString();
                String schoolYear = spinner.getSelectedItem().toString();
                String major = major_tv.getText().toString();

                if(username.compareTo("") == 0 || password.compareTo("") == 0 || firstName.compareTo("") == 0 || lastName.compareTo("") == 0 || schoolYear.compareTo("") == 0 || major.compareTo("") == 0){
                    TextView textView = findViewById(R.id.registrationText);
                    textView.setText("One or more fields are empty");
                }
                else{
                    BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            if (output.compareTo("Username already exists") == 0){
                                TextView textView = findViewById(R.id.registrationText);
                                textView.setText(output);
                            }
                            else{
                                Intent intent = new Intent(registrationActivity.this, LogInActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).execute(type,username,password,firstName,lastName,schoolYear,major);
                }
            }
        });
    }
}
