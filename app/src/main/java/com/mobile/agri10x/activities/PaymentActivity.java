package com.mobile.agri10x.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.mobile.agri10x.models.GetAddMoney;
import com.mobile.agri10x.models.GetCheckOutHandle;
import com.mobile.agri10x.models.GetUserBalance;
import com.mobile.agri10x.models.UserId;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  PaymentActivity extends  AppCompatActivity implements PaymentResultWithDataListener {


    TextView benificary,account_title,ifsc_txt,txt_bank_name,withdrawableBalance;
    UserId userIdo;
    Button addmoney ;
    TextInputEditText add_money_towallet;
    String userid,orderidfromres,amountfromres,order_id, payment_id, signature;
    AlertDialog dialog,dialog2;
    ImageView img_arrow,arrow_back,ic_refresh,copy_account_title,copy_account_beneficiary,copy_account_ifsc,copy_account_ibankname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        txt_bank_name= findViewById(R.id.txt_bank_name);
        ifsc_txt = findViewById(R.id.ifsc_txt);
        benificary= findViewById(R.id.benificary);
        account_title = findViewById(R.id.account_title);

        copy_account_ibankname = findViewById(R.id.copy_account_ibankname);
        copy_account_ifsc = findViewById(R.id.copy_account_ifsc);
        copy_account_beneficiary= findViewById(R.id.copy_account_beneficiary);
        copy_account_title= findViewById(R.id.copy_account_title);

        ic_refresh= findViewById(R.id.ic_refresh);
        arrow_back = findViewById(R.id.arrow_back);
        withdrawableBalance = findViewById(R.id.wallet_balance_withdrawable);
        img_arrow = findViewById(R.id.img_arrow);
        addmoney = findViewById(R.id.addmoney);
        add_money_towallet = findViewById(R.id.add_money_towallet);


        userid=SessionManager.getKeyTokenUser(PaymentActivity.this);
        userIdo = new UserId();
        userIdo.setUserid(userid);

        callapigetBalance(userIdo);

        if(userid != null && !userid.isEmpty()){
            benificary.setText("AGRI10"+userid);
        }

        copy_account_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(account_title.getText().toString());

                Toast.makeText(PaymentActivity.this,"Account Title Copied",Toast.LENGTH_SHORT).show();
            }
        });
        copy_account_beneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(benificary.getText().toString());

                Toast.makeText(PaymentActivity.this,"Beneficiary Account Number Copied",Toast.LENGTH_SHORT).show();
            }
        });

        copy_account_ifsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(ifsc_txt.getText().toString());

                Toast.makeText(PaymentActivity.this,"IFSC Code Copied",Toast.LENGTH_SHORT).show();
            }
        });
        copy_account_ibankname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(txt_bank_name.getText().toString());

                Toast.makeText(PaymentActivity.this,"Bank Name Copied",Toast.LENGTH_SHORT).show();
            }
        });






        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ic_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
callapigetBalance(userIdo);
            }
        });
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamt = add_money_towallet.getText().toString();
                Log.d("getamt",getamt);
                if(getamt == null || getamt.isEmpty() || getamt.equals("") ){

                    Toast.makeText(PaymentActivity.this,"Please Enter Amount",Toast.LENGTH_SHORT).show();
                }else{
                    if( userid != null || !userid.isEmpty()) {
                        dialog2 = new Alert().pleaseWait();
                        callapi(userid,getamt);
                        add_money_towallet.setText("");

                    }
                }
            }
        });
    }

    private void callapigetBalance(UserId userIdo) {
        dialog = new Alert().pleaseWait();
        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<GetUserBalance> loginCall = apiService.wsgetBalance(
                "123456",userIdo);
        loginCall.enqueue(new Callback<GetUserBalance>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUserBalance> call,
                                   Response<GetUserBalance> response) {
                Log.d("checkphone",response.toString());

                if (response.isSuccessful()) {



                    double number1 = response.body().getData().getCurrentBalance();
                    NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                    String currency1 = format1.format(number1);
                    System.out.println("Currency in INDIA : " + currency1);


                    withdrawableBalance.setText(currency1);
                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                    //    new LoginActivity.Alert().SignUp("UnRegistered User!!","First Register Yourself For Our Service");
                }
            }

            @Override
            public void onFailure(Call<GetUserBalance> call,
                                  Throwable t) {
                Toast.makeText(PaymentActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void callapi(String userid, String getamt) {

        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("Userid",userid);
        jsonParams.put("Mon",getamt);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<GetAddMoney> loginCall = apiService.wsGetAddMoney(
                "123456",body);
        loginCall.enqueue(new Callback<GetAddMoney>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetAddMoney> call,
                                   Response<GetAddMoney> response) {
                Log.d("checkphone",response.toString());

                if (response.isSuccessful()) {
                    dialog2.dismiss();
                    Log.d("responsecheck", String.valueOf(response.body()));
                    String checkresponse =String.valueOf(response.body());
                    Log.d("checkingexist",checkresponse);

                    orderidfromres = response.body().getOrderid();
                    amountfromres = String.valueOf(response.body().getAmount());

                    startpayment(orderidfromres,amountfromres);

                }
                else {
                    dialog2.dismiss();
                    //    new LoginActivity.Alert().SignUp("UnRegistered User!!","First Register Yourself For Our Service");
                }
            }

            @Override
            public void onFailure(Call<GetAddMoney> call,
                                  Throwable t) {
               Log.d("errorpayment",t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void startpayment(String orderidfromres, String amountfromres) {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Agri10x");
            options.put("description", "(ICognitive Global Pvt Ltd)");
//You can omit the image option to fetch the image from dashboard
            options.put("image", "https://data.agri10x.com/images/Icognitive%20logo2.png");
            options.put("currency", "INR");
            options.put("theme.color", "#5FA30F");
            options.put("order_id", orderidfromres);
            String payment =amountfromres;        //orderamount.getText().toString();
// amount is in paise so please multiple it by 100
//Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
//            total = total * 100;
            options.put("amount", total);

//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "kamal.bunkar07@gmail.com");
//            preFill.put("contact", "9144040888");

//            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }

    private void callcheckouthandle(String order_id, String payment_id, String signature) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("razorpay_payment_id", payment_id);
        jsonParams.put("razorpay_order_id",order_id);
        jsonParams.put("razorpay_signature",signature);
        jsonParams.put("Userid",userid);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<GetCheckOutHandle> loginCall = apiService.wsGetCheckoutHandle(
                "123456",body);
        loginCall.enqueue(new Callback<GetCheckOutHandle>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCheckOutHandle> call,
                                   Response<GetCheckOutHandle> response) {
                Log.d("checkphone",response.toString());

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("responsecheck", String.valueOf(response.body()));
                    String checkresponse =String.valueOf(response.body());
                    Log.d("checkingexist",checkresponse);

                    Toast.makeText(PaymentActivity.this,response.body().getStatus(),Toast.LENGTH_SHORT).show();

                }
                else {
                    dialog.dismiss();
                    //    new LoginActivity.Alert().SignUp("UnRegistered User!!","First Register Yourself For Our Service");
                }
            }

            @Override
            public void onFailure(Call<GetCheckOutHandle> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(PaymentActivity.this,"Payment Fail",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        order_id = paymentData.getOrderId();
        payment_id = paymentData.getPaymentId();
        signature = paymentData.getSignature();

        callcheckouthandle(order_id,payment_id,signature);

        Log.d("mainresponse",order_id+ " "+ payment_id+ " "+signature);
    }

    public class Alert {
        public void alert( String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(PaymentActivity.this);
            Alert.setCancelable(false)
                    .setTitle(title)
                    .setMessage(body);
            Alert.setNegativeButton("Okey", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            Alert.create().show();
        }
        public AlertDialog pleaseWait() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaymentActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }
    }

    @Override
    public void onBackPressed() {
finish();
    }
}