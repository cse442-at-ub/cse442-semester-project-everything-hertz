package com.cse_442.ceccarelli.ubeventmanager;

import android.annotation.SuppressLint;
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
            if(LogInActivity.logged_in){
                username = LogInActivity.global_username;
            }
            else {
                username = "user1";
            }
            BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
                @Override
                public void processFinish(final String output) {
                    if(addCheckInSuccess(output)) {

                        setSuccessInUI(event_id,username);
                    }else{
                        setUnsuccessfullUI(output);
                    }
                }
            }).execute("event_check_in",username,event_id);
    }

    private void setUnsuccessfullUI(String output) {
    }

    private void setSuccessInUI(String event_id, String username) {
        String eventName = getEventName(event_id);
        String getTotalPoints = getTotalPoints(username);
        checkInResult.setText("checked into event number:" + event_id);
        checkInResultPoints.setText(eventName);
    }

    public String getEventName(String event_id) {
        final String[] name = {""};
        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse(){
            @Override
            public void processFinish(String output) {
                try {
                    System.out.println("trying to get request");
                    System.out.println(output);
                    JSONObject obj = new JSONObject(output);
                    System.out.println(obj.getString("data"));
                    name[0] = obj.getString("data");
                    System.out.println("----->"+name[0]);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("get_event_name",event_id);
        System.out.println("returning"+name[0]);
        
        return name[0];
    }

    public String getTotalPoints(String username) {
        return "";
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
        qrScan.initiateScan();
    }
//valid result from QR code
    public boolean validResult(JSONObject data) throws JSONException {
        if(data.has("event_id") && data.length() == 1 && data.get("event_id") instanceof Integer){
            return true;
        }else{
            return false;
        }
    }
}
