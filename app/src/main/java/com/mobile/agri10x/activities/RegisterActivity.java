package com.mobile.agri10x.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btn_next,login;
    TextInputEditText mobilenumber;
    String strmobilenumber,strrole ="";
    AlertDialog dialog;
    ImageView call,backarrow;
    TextView trader_btn,farmer_btn;
    private LiveNetworkMonitor mNetworkMonitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNetworkMonitor=new LiveNetworkMonitor(this);

        login =  findViewById(R.id.login);
        backarrow = findViewById(R.id.backarrow);
        call = findViewById(R.id.call);
        btn_next = findViewById(R.id.btn_next);
        trader_btn= findViewById(R.id.trader_btn);
        farmer_btn = findViewById(R.id.farmer_btn);
        mobilenumber = findViewById(R.id.mobilenumber);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmobilenumber=mobilenumber.getText().toString();
                if(validateMobileNo()  && validaterole())
                {
                    GetOtp(strmobilenumber);
                }


            }
        });


        trader_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                trader_btn.setBackgroundResource(R.drawable.click_change1);
                farmer_btn.setBackgroundResource(R.drawable.click_chnage2);
                trader_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                farmer_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                strrole = "PTrader";
                Log.d("takerole",strrole);
            }
        });


        farmer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trader_btn.setBackgroundResource(R.drawable.hollow_back);
                farmer_btn.setBackgroundResource(R.drawable.filll_back);
                trader_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                farmer_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                strrole = "PFarmer";
                Log.d("takerole",strrole);
            }
        });
    }

    private boolean validaterole() {
        if (strrole.isEmpty() || strrole == null ) {
            Toast.makeText(RegisterActivity.this,
                    "Please Select Role", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void GetOtp(String strmobilenumber) {
        dialog=new RegisterActivity.Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", "91"+strmobilenumber);
        jsonParams.put("flag","0");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
       // AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetOTP> loginCall = apiService.wsgetOTP(body);
        loginCall.enqueue(new Callback<GetOTP>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetOTP> call,
                                   Response<GetOTP> response) {
                //dialog.dismiss();
                Log.d("getotp",response.toString());
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    if(response.body().getOut() == 5)
                    {
                        Intent i = new Intent(RegisterActivity.this,Otp_Screen_Activity.class);
                        i.putExtra("mobilenumber",strmobilenumber);
                        i.putExtra("role",strrole);
                        i.putExtra("flag","0");
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Something get Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOTP> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(RegisterActivity.this);
            Alert.setCancelable(false)
                    .setTitle(title)
                    .setMessage(body);
            Alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            Alert.create().show();
        }


        public AlertDialog pleaseWait() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }


    }
    private boolean validateMobileNo() {
String strmob = mobilenumber.getText().toString();
        if (strmob.isEmpty() || strmob.length() < 10 ) {
            Toast.makeText(RegisterActivity.this,
                    "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}


