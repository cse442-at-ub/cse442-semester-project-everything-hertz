package com.cse_442.ceccarelli.ubeventmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckInActivity extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
    }

}
