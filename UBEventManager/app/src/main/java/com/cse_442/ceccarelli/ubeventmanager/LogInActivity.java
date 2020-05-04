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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LogInActivity extends AppCompatActivity {
    public final String LOGGED_IN = "logged_in";
    public final String USERNAME = "username";
    public final String HAVENAME = "havename";
    String username,password,result;

    EditText usernameInput;
    EditText passwordInput;

    Button submit, register;



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


                //System.out.println(username + ", " + password);
                /*
                Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                startActivity(intent);
                 */
                tryLogIn("log_in",username,password);
            }
        });

        /*
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(LogInActivity.this, registrationActivity.class);
               startActivity(intent);
            }
        });

         */

    }

    public void tryLogIn(String type, String username, String password) {
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(String output) {
                if(output.compareTo("pass") == 0){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(USERNAME, usernameInput.getText().toString());
                    editor.putBoolean(LOGGED_IN,true);
                    editor.commit();
                    Log.d("LogInActivity", "Preferences were committed");
                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
}
