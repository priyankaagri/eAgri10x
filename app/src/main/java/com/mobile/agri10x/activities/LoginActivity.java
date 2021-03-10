package com.mobile.agri10x.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  Button btn_next,register;
  TextInputEditText mobilenumber;
  String strmobilenumber;
    AlertDialog dialog;
    ImageView call,backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(SessionManager.isLoggedIn(LoginActivity.this))
        {
            startActivity(new Intent(LoginActivity.this,HomePageActivity.class));
            finish();
        }
        register= findViewById(R.id.register);
        btn_next= findViewById(R.id.btn_next);
        mobilenumber= findViewById(R.id.mobilenumber);
        call = findViewById(R.id.call);
        backarrow = findViewById(R.id.backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmobilenumber=mobilenumber.getText().toString();
                if(TextUtils.isEmpty(strmobilenumber))
                {
                    mobilenumber.setError("Please Enter Mobile number");
                    mobilenumber.requestFocus();
                    return;
                } else if(strmobilenumber.length()!=10){

                    mobilenumber.setError("Please Enter 10 digit mobile number");
                    mobilenumber.requestFocus();
                    return;
                }else {
                    InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm.isAcceptingText()) {
                        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } else {
                        // writeToLog("Software Keyboard was not shown");
                    }
                    GetOtp(strmobilenumber);
                }

            }
        });
    }

    private void GetOtp(final  String mobilenumber) {{
        dialog=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", "91"+mobilenumber);
        jsonParams.put("flag","1");

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
                    Log.d("getout", String.valueOf(response.body().getOut()));
                    if(response.body().getOut() == 5)
                    {
                        Intent i = new Intent(LoginActivity.this,Otp_Screen_Activity.class);
                        i.putExtra("mobilenumber",mobilenumber);
                        i.putExtra("role","");
                        i.putExtra("flag","1");
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Mobile Number is not registerd.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOTP> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    }

    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(LoginActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }


    }
}