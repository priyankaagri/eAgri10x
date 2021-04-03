package com.mobile.agri10x.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Button btn_next, login;
    EditText mobilenumber;
    String strmobilenumber, strrole = "",FirstName="",LastName="";
    EditText lname_edt_txt,fname_edt_txt;
    AlertDialog dialog;
    ImageView call, backarrow;
    TextView trader_btn, farmer_btn;
    private LiveNetworkMonitor mNetworkMonitor;
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    TextView dob_txt;
    String myFormat2;
    String DATEOFBIRTH = "0000-00-00";
    String gahaw;
    final android.icu.util.Calendar myCalendar = android.icu.util.Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(android.icu.util.Calendar.YEAR, year);
            myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
            myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        myFormat2 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdff = new SimpleDateFormat(myFormat2, Locale.US);
        DATEOFBIRTH = sdff.format(myCalendar.getTime());
        dob_txt.setText(DATEOFBIRTH);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNetworkMonitor = new LiveNetworkMonitor(this);

        login = findViewById(R.id.login);
        dob_txt = findViewById(R.id.dob_txt);
        backarrow = findViewById(R.id.backarrow);
        call = findViewById(R.id.call);
        btn_next = findViewById(R.id.btn_next);
        trader_btn = findViewById(R.id.trader_btn);
        farmer_btn = findViewById(R.id.farmer_btn);
        mobilenumber = findViewById(R.id.mobilenumber);
        fname_edt_txt = findViewById(R.id.fname_edt_txt);
        lname_edt_txt = findViewById(R.id.lname_edt_txt);

        dob_txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, dateListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 662695992000L);
                datePickerDialog.show();
            }
        });
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(RegisterActivity.this)
                .addConnectionCallbacks(RegisterActivity.this)
                .addOnConnectionFailedListener(RegisterActivity.this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), 1008, null, 0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            Log.e("", "Could not start hint picker Intent", e);
        }
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
                strmobilenumber = mobilenumber.getText().toString();
                FirstName = fname_edt_txt.getText().toString();
                LastName = lname_edt_txt.getText().toString();
                if (validateMobileNo() && validaterole() && validation()&& validateDOB()) {

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
           //     Log.d("takerole", strrole);
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
             //   Log.d("takerole", strrole);
            }
        });
    }

    private boolean validation() {


        if (TextUtils.isEmpty(fname_edt_txt.getText().toString())) {
            fname_edt_txt.setError(" Please Fill Your First Name!");
            fname_edt_txt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lname_edt_txt.getText().toString())) {
            lname_edt_txt.setError(" Please Fill Your Last Name!");
            lname_edt_txt.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validaterole() {
        if (strrole.isEmpty() || strrole == null) {
            Toast.makeText(RegisterActivity.this,
                    "Please Select Role", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void GetOtp(String strmobilenumber) {
        dialog = new RegisterActivity.Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("mobileNo", "91" + strmobilenumber);
        jsonParams.put("flag", "0");

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
        final Call<GetOTP> loginCall = apiService.wsgetOTP("123456",body);
        loginCall.enqueue(new Callback<GetOTP>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetOTP> call,
                                   Response<GetOTP> response) {
                //dialog.dismiss();
          //      Log.d("getotp", response.toString());
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    if (response.body().getOut() == 5) {
                        Intent i = new Intent(RegisterActivity.this, Otp_Screen_Activity.class);
                        i.putExtra("mobilenumber", strmobilenumber);
                        i.putExtra("role", strrole);

                        i.putExtra("DOB", DATEOFBIRTH);
                        i.putExtra("FirstName", FirstName);
                        i.putExtra("LastName", LastName);

                        i.putExtra("flag", "0");
                        startActivity(i);
                        finish();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Something get Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOTP> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
        if (strmob.isEmpty() || strmob.length() < 10) {
            Toast.makeText(RegisterActivity.this,
                    "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }


    private boolean validateDOB() {
        if (DATEOFBIRTH.equalsIgnoreCase("0000-00-00")) {
            Toast.makeText(RegisterActivity.this,
                    "Please Select Your DOB!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNetworkMonitor.isConnected()) {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1008:
                if (resultCode == RESULT_OK) {

                    Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
                    Credential credd = data.getParcelableExtra(Credential.EXTRA_KEY);
// cred.getId====: ====+919*******
                    // Log.e("cred.getId", cred.getId());
// userMob = cred.getId();
                    //    Toast.makeText(LoginActivity.this, ""+cred.getId(), Toast.LENGTH_SHORT).show();
                    String phoneNumber = cred.getId();
                    phoneNumber = phoneNumber.replaceAll("[()\\s-]+", "");
                    if(phoneNumber.startsWith("+"))
                    {
                        if(phoneNumber.length()==13)
                        {
                            String str_getMOBILE=phoneNumber.substring(3);
                            mobilenumber.setText(str_getMOBILE);
                        }
                        else if(phoneNumber.length()==14)
                        {
                            String str_getMOBILE=phoneNumber.substring(4);
                            mobilenumber.setText(str_getMOBILE);
                        }


                    }
                    else if(phoneNumber.startsWith("0")){
                        if(phoneNumber.length()==11)
                        {
                            String str_getMOBILE=phoneNumber.substring(1);
                            mobilenumber.setText(str_getMOBILE);
                        }
                    }
                    else
                    {
                        mobilenumber.setText(phoneNumber);
                    }


                } else {
// Sim Card not found!
                    // Log.e("cred.getId", "1008 else");

                    return;
                }


                break;
        }
    }
}


