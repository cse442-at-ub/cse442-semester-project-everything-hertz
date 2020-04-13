package com.cse_442.ceccarelli.ubeventmanager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener  {

    private TextView checkInResult, checkInResultPoints;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        Button buttonScan;
        buttonScan = findViewById(R.id.buttonScan);
        checkInResult = findViewById(R.id.checkInResult);
        checkInResultPoints = findViewById(R.id.checkInResult_points);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Code Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    if(validResult(obj)){
                        String event_id = obj.getString("event_id");
                        checkIn(event_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    checkInResult.setText("Unable to check in to the event");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkIn(final String event_id){
            final String username;
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(CheckInActivity.this);
            if(preference.getBoolean("logged_in", false)){
                username = preference.getString("username", "user1");
            }
            else {
                username = "user1";
            }

            BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
                @Override
                public void processFinish(final String output) {
                    if(addCheckInSuccess(output)) {
                        setSuccessInUI(event_id,username);
                        System.out.println("the output"+output);
                    }else{
                        setUnsuccessfullUI(event_id);
                    }
                }
            }).execute("event_check_in",username,event_id);
    }

    private void setUnsuccessfullUI(String event_id) {
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @SuppressLint("SetTextI18n")
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    checkInResult.setText("There was an error with checking into "+obj.getString("data"));
                }catch (JSONException e) {
                    checkInResult.setText("There was an error with checking in");
                    e.printStackTrace();
                }
            }
        }).execute("get_event_name",event_id);
    }

    private void setSuccessInUI(String event_id, String username) {
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @SuppressLint("SetTextI18n")
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    System.out.println(obj.getString("data"));
                    checkInResult.setText("You have checked into " + obj.getString("data"));
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("get_event_name",event_id);
        BackgroundWorker backgroundWorker2 = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @SuppressLint("SetTextI18n")
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    System.out.println(obj.getString("data"));
                    checkInResultPoints.setText("You now have " + obj.getString("data")+" points!");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("get_user_points",username);
    }

    public Boolean addCheckInSuccess(String output) {
        try {
            JSONObject obj = new JSONObject(output);
            return obj.getInt("success")==1;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        checkInResult.setText("");
        checkInResultPoints.setText("");
        qrScan.initiateScan();
    }

    public boolean validResult(JSONObject data) throws JSONException {
        if(data.has("event_id") && data.length() == 1 && data.get("event_id") instanceof Integer){
            return true;
        }else{
            return false;
        }
    }
}
