package com.cse_442.ceccarelli.ubeventmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LogInActivity extends AppCompatActivity {
    public final String LOGGED_IN = "logged_in";
    public final String USERNAME = "username";
    public final String HAVENAME = "havename";
    String username,password,result,userType;

    EditText usernameInput;
    EditText passwordInput;

    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOGGED_IN,false);
        editor.putBoolean(HAVENAME, false);
        editor.commit();
        /*
        logged_in = false;
        global_username = "";
         */
        usernameInput = (EditText) findViewById(R.id.usernameInputText);
        passwordInput = (EditText) findViewById(R.id.passwordInputText);

        submit = (Button) findViewById(R.id.loginButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                result = "nothing";

                tryLogIn("log_in",username,password);
            }
        });

    }

    public void tryLogIn(String type, String username, String password) {
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(String output) {
                if(output.compareTo("pass") == 0){
                    getUserType();
                }
                else if(output.compareTo("fail") == 0){
                    TextView t1 = (TextView) findViewById(R.id.log_in_text);
                    t1.setText("Incorrect password");
                }
                else if(output.compareTo("invalid username") == 0){
                    TextView t1 = (TextView) findViewById(R.id.log_in_text);
                    t1.setText("Invalid username");
                }
                else{
                    TextView t1 = (TextView) findViewById(R.id.log_in_text);
                    t1.setText("Try again");
                }
            }
        }).execute(type,username,password);
    }

    public boolean updateUserType(String output){
        if (!output.equals("No results")) {
            userType = output;
            return true;
        } return false;
    }

    public void getUserType(){
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(String output) {
                updateUserType(output);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(USERNAME, usernameInput.getText().toString());
                editor.putBoolean(LOGGED_IN,true);
                editor.commit();
                Log.d("LogInActivity", "Preferences were committed");

                if (userType.contains("student")){
                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else if (userType.contains("coordinator")){
                    Intent intent = new Intent(LogInActivity.this,AddEventActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    TextView t1 = (TextView) findViewById(R.id.log_in_text);
                    t1.setText("Invalid user type");

                }


            }
        }).execute("get_user_type",username);
    }

}
