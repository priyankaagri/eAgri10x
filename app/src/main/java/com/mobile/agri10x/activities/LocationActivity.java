package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

import static com.mobile.agri10x.utils.ToastMessages.makeToast;

public class LocationActivity extends AppCompatActivity {
    Button loction_btn;
    private LiveNetworkMonitor mNetworkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mNetworkMonitor=new LiveNetworkMonitor(this);
        loction_btn= findViewById(R.id.loction_btn);
        loction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            makeToast(getApplicationContext(),getResources().getString(R.string.network_connected));
        }
    }
}