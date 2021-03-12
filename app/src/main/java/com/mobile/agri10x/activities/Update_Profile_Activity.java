package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

public class Update_Profile_Activity extends AppCompatActivity {
    Button save_btn;
    private LiveNetworkMonitor mNetworkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkMonitor=new LiveNetworkMonitor(this);

        setContentView(R.layout.activity_update__profile_);
        save_btn= findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_Profile_Activity.this, OrderFail_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}