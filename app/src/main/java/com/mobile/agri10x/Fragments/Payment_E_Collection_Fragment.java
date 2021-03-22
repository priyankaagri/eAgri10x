package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Payment_E_Collection_Fragment extends Fragment {

    ImageView mBackImage;
    Button ihavemadepayment;
    String amountstr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment__e__collection_, container, false);

        amountstr = getArguments().getString("message");
        Log.d("getamount",amountstr);
        ihavemadepayment = view.findViewById(R.id.ihavemadepayment);
        mBackImage=view.findViewById(R.id.but_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new MenuFragment());
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
        return  view;
    }

    private void callapi(double amtdouble, String userid) {

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("amount",amtdouble);
        jsonParams.put("UserID",userid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
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
                        HomePageActivity.setFragment(new YourOrderFragment(),"youroder");
                        Toast.makeText(getActivity(),"Payment Successful",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(),"Payment Failed",Toast.LENGTH_SHORT).show();
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