package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.agri10x.R;

public class SignUpActivity extends AppCompatActivity {
    TextView trader_btn,farmer_btn,callphon;
    ImageView call,img_arrow;
    Button btn_next;
    EditText lname,fname;
    String first_name_string="",last_name_string="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findid();
        clicklisner();
    }

    private void clicklisner() {
        trader_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                trader_btn.setBackgroundResource(R.drawable.click_change1);
                farmer_btn.setBackgroundResource(R.drawable.click_chnage2);
                trader_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                farmer_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

            }
        });


        farmer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trader_btn.setBackgroundResource(R.drawable.hollow_back);
                farmer_btn.setBackgroundResource(R.drawable.filll_back);
                trader_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                farmer_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            }
        });

        callphon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + "18001212243"));
                startActivity(dialIntent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + "18001212243"));
                startActivity(dialIntent);
            }
        });
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name_string= fname.getText().toString().trim();
                last_name_string= lname.getText().toString().trim();

            }
        });

    }

    private void findid() {

        trader_btn= findViewById(R.id.trader_btn);
        farmer_btn= findViewById(R.id.farmer_btn);
        callphon= findViewById(R.id.callphon);
        call= findViewById(R.id.call);
        img_arrow= findViewById(R.id.img_arrow);
        btn_next= findViewById(R.id.btn_next);
        fname= findViewById(R.id.fname);
        lname= findViewById(R.id.lname);
    }
}