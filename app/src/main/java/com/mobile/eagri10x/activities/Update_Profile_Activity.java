package com.mobile.eagri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.eagri10x.R;

public class Update_Profile_Activity extends AppCompatActivity {
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}