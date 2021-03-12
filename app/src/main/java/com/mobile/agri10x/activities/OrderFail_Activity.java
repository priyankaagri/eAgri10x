package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobile.agri10x.R;
import com.mobile.agri10x.utils.LiveNetworkMonitor;


public class OrderFail_Activity extends AppCompatActivity {
     Button backhome_btn;
    private LiveNetworkMonitor mNetworkMonitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_fail_);
        mNetworkMonitor=new LiveNetworkMonitor(this);

        backhome_btn= findViewById(R.id.backhome_btn);
        backhome_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(OrderFail_Activity.this,R.style.AppBottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.bottom_sheet_layout,(LinearLayout)findViewById(R.id.BottomSheetcontainer));


                bottomSheetView.findViewById(R.id.course_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),
                                "Clicked Yes", Toast.LENGTH_SHORT)
                                .show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.algo_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),
                                "Clicked No", Toast.LENGTH_SHORT)
                                .show();
                        bottomSheetDialog.dismiss();
                    }
                });

              /*  BottomSheetDialog bottomSheet = new BottomSheetDialog(OrderFail_Activity.this,R.style.AppBottomSheetDialogTheme);
                bottomSheet.show(getSupportFragmentManager(),
                        "");
*/

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
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