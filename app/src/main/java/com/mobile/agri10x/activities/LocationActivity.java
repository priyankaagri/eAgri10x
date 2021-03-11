package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.agri10x.R;

public class LocationActivity extends AppCompatActivity {
    Button loction_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        loction_btn= findViewById(R.id.loction_btn);
        loction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}