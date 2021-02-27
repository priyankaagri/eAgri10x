package com.mobile.eagri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.eagri10x.R;

public class Otp_Screen_Activity extends AppCompatActivity {
    Button btn_varify_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__screen_);
        btn_varify_otp= findViewById(R.id.btn_varify_otp);

        btn_varify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Otp_Screen_Activity.this, Update_Profile_Activity.class);
                startActivity(intent);
            }
        });
    }
}