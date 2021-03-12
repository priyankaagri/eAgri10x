package com.mobile.agri10x.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.VerifyOTP;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Otp_Screen_Activity extends AppCompatActivity {
    Button btn_varify_otp;
    OtpTextView otp_view;
    TextView txt_verification;
    LinearLayout linresend;
    String mobilenumber, strrole, strflag;
    String str_otp = "";
    ImageView img_arrow;
    AlertDialog dialog, dialogresend;
    private LiveNetworkMonitor mNetworkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__screen_);
        mNetworkMonitor=new LiveNetworkMonitor(this);

        img_arrow = findViewById(R.id.img_arrow);
        btn_varify_otp = findViewById(R.id.btn_varify_otp);
        otp_view = findViewById(R.id.otp_view);
        txt_verification = findViewById(R.id.txt_verification);
        linresend = findViewById(R.id.linresend);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobilenumber = bundle.getString("mobilenumber");
            txt_verification.setText("Please Type Verification code send to " + mobilenumber);

            strflag = bundle.getString("flag");
            strrole = bundle.getString("role");
        }
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobilenumber != null || !mobilenumber.isEmpty()) {
                    dialogresend = new Otp_Screen_Activity.Alert().resendingotp();
                    callResendOtp(mobilenumber);
                }
            }
        });
        btn_varify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateOTP()) {

                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm.isAcceptingText()) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } else {
// writeToLog("Software Keyboard was not shown");
                    }
                    dialog = new Otp_Screen_Activity.Alert().verifyingotp();
                    if (str_otp != null || !str_otp.isEmpty()) {
                        callverifyapi("91" + mobilenumber, str_otp);
                    } else {
                        Toast.makeText(Otp_Screen_Activity.this, "wrong", Toast.LENGTH_SHORT).show();
                    }


//Log.d("params","91"+strmobilenumber+" "+strotp);
                }
            }
        });
        otp_view.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {

                str_otp = otp;
            }
        });
    }

    private void callResendOtp(String mobilenumber) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", "91" + mobilenumber);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetResendOTP> loginCall = apiService.wsgetresendOTP(
                body);
        loginCall.enqueue(new Callback<GetResendOTP>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetResendOTP> call,
                                   Response<GetResendOTP> response) {
                dialogresend.dismiss();
// Log.d("verifyOTP",response.toString());
                if (response.isSuccessful()) {
// Log.d("getresponse",response.body().getType());
                    Toast.makeText(Otp_Screen_Activity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Otp_Screen_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetResendOTP> call,
                                  Throwable t) {
                dialogresend.dismiss();
                Toast.makeText(Otp_Screen_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callverifyapi(String s, String str_otp) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", s);
        jsonParams.put("otp", str_otp);

        jsonParams.put("flag", strflag);
        jsonParams.put("role", strrole);


        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        // AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<VerifyOTP> loginCall = apiService.wsgetVerifyOTP(
                body);
        loginCall.enqueue(new Callback<VerifyOTP>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<VerifyOTP> call,
                                   Response<VerifyOTP> response) {
                dialog.dismiss();
// Log.d("verifyOTP",response.toString());
                if (response.isSuccessful()) {
// Log.d("getresponse",response.body().getType());
                    if (response.body().getOut()) {
                        Log.e("token", "" + response.body().getToken());
                        new SessionManager(Otp_Screen_Activity.this).createLoginSession(response.body().getToken());

                        try {
                            new JWTUtils().decoded(response.body().getToken());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Otp_Screen_Activity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();


                    } else if (!response.body().getOut()) {

                        Toast.makeText(Otp_Screen_Activity.this, "You Entered wrong OTP.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    dialog.dismiss();
                    Toast.makeText(Otp_Screen_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTP> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(Otp_Screen_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateOTP() {
        if (str_otp.isEmpty() || str_otp.equals("")) {
            Toast.makeText(Otp_Screen_Activity.this,
                    "Invalid OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(Otp_Screen_Activity.this);
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

        public void SignUp(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(Otp_Screen_Activity.this);
            Alert.setCancelable(true)
                    .setTitle(title)
                    .setMessage(body);
            Alert.setNegativeButton("Sign Up", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Otp_Screen_Activity.this, SignUpActivity.class));
                    dialogInterface.cancel();
                }
            });
            Alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Alert.create().show();
        }

        public AlertDialog verifyingotp() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Otp_Screen_Activity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_verify_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }

        public AlertDialog resendingotp() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Otp_Screen_Activity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_resending_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public class JWTUtils {

        public  void decoded(String JWTEncoded) throws Exception {
            try {
                String[] split = JWTEncoded.split("\\.");
                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            } catch (UnsupportedEncodingException e) {
                //Error
            }
        }

        private  String getJson(String strEncoded) throws UnsupportedEncodingException{
            byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
            return new String(decodedBytes, "UTF-8");
        }
    }
}