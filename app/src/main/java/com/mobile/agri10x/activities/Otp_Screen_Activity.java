package com.mobile.agri10x.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobile.agri10x.R;

import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.VerifyOTP;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;
import com.mobile.agri10x.utils.SmsBroadcastReceiver;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
    TextView txt_verification,timer;
    CountDownTimer cTimer = null;
    String mobilenumber, strrole, strflag;
    String str_otp = "",strotpfrmmsg;
    ImageView img_arrow;
    AlertDialog dialog, dialogresend;
    private LiveNetworkMonitor mNetworkMonitor;
    String DATEOFBIRTH, FirstName = "", LastName = "";
        SmsBroadcastReceiver smsBroadcastReceiver;
    private static final int REQ_USER_CONSENT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__screen_);
        mNetworkMonitor = new LiveNetworkMonitor(this);


        timer = findViewById(R.id.timer);
        img_arrow = findViewById(R.id.img_arrow);
        btn_varify_otp = findViewById(R.id.btn_varify_otp);
        otp_view = findViewById(R.id.otp_view);
        txt_verification = findViewById(R.id.txt_verification);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobilenumber = bundle.getString("mobilenumber");
            txt_verification.setText("Please enter verification code sent to " + mobilenumber);

            strflag = bundle.getString("flag");
            strrole = bundle.getString("role");

            FirstName = bundle.getString("FirstName");
            LastName = bundle.getString("LastName");
            DATEOFBIRTH = bundle.getString("DOB");

        }
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startTimer();
        startSmsUserConsent();

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } else {
                    // writeToLog("Software Keyboard was not shown");
                }

                if (mobilenumber != null || !mobilenumber.isEmpty()) {
                    dialogresend = new Otp_Screen_Activity.Alert().resendingotp();
                    callResendOtp(mobilenumber);
                }
                //   verifyotp.setVisibility(View.VISIBLE);


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

    void startTimer() {
        cTimer = new CountDownTimer(20000, 1000)
        {
            public void onTick(long millisUntilFinished) {
                timer.setText( "00:"+String.valueOf(millisUntilFinished / 1000) +"  seconds remaining..." );
            }
            public void onFinish() {
                timer.setClickable(true);
                timer.setFocusable(true);
                timer.setText("Didn`t receive the code ? Resend again");
                btn_varify_otp.setVisibility(View.GONE);
            }
        };
        cTimer.start();
    }


    private void callResendOtp(String mobilenumber) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", "91" + mobilenumber);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetResendOTP> loginCall = apiService.wsgetresendOTP("123456",
                body);
        loginCall.enqueue(new Callback<GetResendOTP>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetResendOTP> call,
                                   Response<GetResendOTP> response) {
                dialogresend.dismiss();

                if (response.isSuccessful()) {

                    startTimer();
                    btn_varify_otp.setVisibility(View.VISIBLE);
                    Toast.makeText(Otp_Screen_Activity.this, "Verification code resend successfully.", Toast.LENGTH_SHORT).show();
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
    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
//We can add sender phone number or leave it blank
// I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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

        jsonParams.put("Firstname", FirstName);
        jsonParams.put("Lastname", LastName);
        jsonParams.put("DOB", DATEOFBIRTH);

//Firstname Lastname DOB
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<VerifyOTP> loginCall = apiService.wsgetVerifyOTP("123456",
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
                    if (response.body().getOut() == 5) {
                        Log.e("token", "" + response.body().getToken());
                      //  new SessionManager(Otp_Screen_Activity.this).createLoginSession(response.body().getToken());

                        try {
                            new JWTUtils().decoded(response.body().getToken());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Otp_Screen_Activity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();


                    } else  {

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
        if (mNetworkMonitor.isConnected()) {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public class JWTUtils {

        public void decoded(String JWTEncoded) throws Exception {
            try {
                String[] split = JWTEncoded.split("\\.");
//                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
//                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

                JSONObject obj = new JSONObject(getJson(split[1]));
                String mobile = obj.getString("mobile");
                String token = obj.getString("token");
                String iat = obj.getString("iat");
                String role = obj.getString("role");
                String exp = obj.getString("exp");
                SessionManager.addUserDetails(mobile, iat, role, exp,token);
            } catch (UnsupportedEncodingException e) {
                //Error
            }
        }

        private String getJson(String strEncoded) throws UnsupportedEncodingException {
            byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
            return new String(decodedBytes, "UTF-8");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
//That gives all message to us.
// We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                if(message.contains("Agri10x E-Marketplace is")) {
                    String numberOnly = message.replaceAll("[^0-9]", "");
//numberOnly 10886110

                    String a = numberOnly.substring(2);

                    strotpfrmmsg = a.substring(0, a.length() - 2);
                    if (strotpfrmmsg != null) {
                        otp_view.setOTP(strotpfrmmsg);
                    }

                }

            }
        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure(String s) {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver,intentFilter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

}