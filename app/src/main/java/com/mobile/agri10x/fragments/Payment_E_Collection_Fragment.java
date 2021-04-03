package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;


public class Payment_E_Collection_Fragment extends Fragment {

    ImageView mBackImage,copy_account_title,copy_account_beneficiary,copy_account_ifsc,copy_account_ibankname;
    Button ihavemadepayment;
    String amountstr,getuserid;
    TextView benificary,account_title,ifsc_txt,txt_bank_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment__e__collection_, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            amountstr = bundle.getString("amount");
        }
        getuserid = SessionManager.getKeyTokenUser(getActivity());
        Log.d("getamount",amountstr);


        txt_bank_name= view.findViewById(R.id.txt_bank_name);
        copy_account_ibankname = view.findViewById(R.id.copy_account_ibankname);
        ifsc_txt = view.findViewById(R.id.ifsc_txt);
        copy_account_ifsc = view.findViewById(R.id.copy_account_ifsc);
        account_title = view.findViewById(R.id.account_title);
        copy_account_beneficiary= view.findViewById(R.id.copy_account_beneficiary);
        copy_account_title= view.findViewById(R.id.copy_account_title);
        ihavemadepayment = view.findViewById(R.id.ihavemadepayment);
        mBackImage=view.findViewById(R.id.but_back);
        benificary = view.findViewById(R.id.benificary);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new Payment_E_Collection_Fragment());
            }
        });
        
        ihavemadepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountstr != null || !amountstr.isEmpty()){
                    double amtdouble = Double.parseDouble(amountstr);
                    String userid = SessionManager.getKeyTokenUser(getActivity());

                    callapi(amtdouble,userid);
                }

                
            }
        });
if(getuserid != null && !getuserid.isEmpty()){
    benificary.setText("AGRI10"+getuserid);
}

        copy_account_title.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ClipboardManager clipboard = (ClipboardManager)getActivity(). getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(account_title.getText().toString());

        Toast.makeText(getActivity(),"Account Title Copied",Toast.LENGTH_SHORT).show();
    }
});
        copy_account_beneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getActivity(). getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(benificary.getText().toString());

                Toast.makeText(getActivity(),"Beneficiary Account Number Copied",Toast.LENGTH_SHORT).show();
            }
        });

        copy_account_ifsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getActivity(). getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(ifsc_txt.getText().toString());

                Toast.makeText(getActivity(),"IFSC Code Copied",Toast.LENGTH_SHORT).show();
            }
        });
        copy_account_ibankname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getActivity(). getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(txt_bank_name.getText().toString());

                Toast.makeText(getActivity(),"Bank Name Copied",Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }

    private void callapi(double amtdouble, String userid) {

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("amount",amtdouble);
        jsonParams.put("UserID",userid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetCheckCollect> loginCall = apiService.wsCheckECollect("123456",body);
        loginCall.enqueue(new Callback<GetCheckCollect>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCheckCollect> call,
                                   Response<GetCheckCollect> response) {

                Log.d("getnameapi",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage()){
                        HomePageActivity.removeFragment(new Payment_E_Collection_Fragment());
                        HomePageActivity.setFragment(new MyOrderFragment(),"youroder");
                        HomePageActivity.getProductinCart();
                        Toast.makeText(getActivity(),"Payment Successful",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(),"Payment Not Completed",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCheckCollect> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}