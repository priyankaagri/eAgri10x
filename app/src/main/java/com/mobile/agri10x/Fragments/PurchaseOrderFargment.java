package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.SimpleListAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseOrderFargment extends Fragment {
    Context context;
    SimpleListAdapter mSimpleListAdapter;
    List<GetCities> getstateArrayList = new ArrayList<>();
    ArrayList<String> statecategory = new ArrayList<>();
    EditText fname_edt_txt,lname_edt_txt,deliverynote,packagingdatail;
    Spinner addressspinner_billing,addressspinner_delivery,addressspinner_booking_amount;
    TextView totalamt,checkout_btne,paywithecollect,bookingamt,pendingamt;
    ImageView but_back;
    String amt;
    double damt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_order_fargment, container, false);
        context = view.getContext();
        pendingamt = view.findViewById(R.id.pendingamt);
        bookingamt = view.findViewById(R.id.bookingamt);
        fname_edt_txt=view.findViewById(R.id.fname_edt_txt);
        lname_edt_txt=view.findViewById(R.id.lname_edt_txt);
        addressspinner_billing=view.findViewById(R.id.addressspinner_billing);
        addressspinner_delivery=view.findViewById(R.id.addressspinner_delivery);
        addressspinner_booking_amount=view.findViewById(R.id.addressspinner_booking_amount);
        deliverynote=view.findViewById(R.id.deliverynote);
        packagingdatail=view.findViewById(R.id.packagingdatail);
        totalamt=view.findViewById(R.id.totalamt);
        checkout_btne=view.findViewById(R.id.checkout_btne);
        paywithecollect=view.findViewById(R.id.paywithecollect);
        but_back=view.findViewById(R.id.but_back);
        amt = getArguments().getString("value");
        damt = Double.parseDouble(amt);
        totalamt.setText("₹ "+amt);
        Log.d("getamt",amt);
        Callapiforname();
        CallapigetAddress();
        //callcities();

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new PurchaseOrderFargment());
            }
        });

        return view;
    }

//    private void callcities() {
//        AgriInvestor apiService = ApiHandler.getApiService();
//        final Call<List<GetCities>> loginCall = apiService.wsgetCities(
//                "123456");
//        loginCall.enqueue(new Callback<List<GetCities>>() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onResponse(Call<List<GetCities>> call,
//                                   Response<List<GetCities>> response) {
//
//                if (response.isSuccessful()) {
//                    getstateArrayList = response.body();
//                    Log.d("getresponse", String.valueOf(getstateArrayList.size()));
//
//
//
//
//
//                    if(!getstateArrayList.isEmpty()){
//
//
//                        //                    Commoditycategory.add("Select");
//                        for(int i=0; i < getstateArrayList.size();i++){
//                            statecategory.add(getstateArrayList.get(i).getState());
//                        }
//                        Log.d("statehold", String.valueOf(statecategory.size()));
//
//                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
//                        // commodity.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));
//
//
//                    }else{
//
//                        statecategory.add("No Data");
//                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
//                        //commodity.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<GetCities>> call,
//                                  Throwable t) {
//                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void Callapiforname() {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetUserByID> loginCall = apiService.wsGetUserById("123456",body);
        loginCall.enqueue(new Callback<GetUserByID>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUserByID> call,
                                   Response<GetUserByID> response) {

                Log.d("getnameapi",response.toString());
                if (response.isSuccessful()) {

                    fname_edt_txt.setText(response.body().getData().getFirstname());
                    lname_edt_txt.setText(response.body().getData().getLastname());
                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserByID> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallapigetAddress() {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<getAddress> loginCall = apiService.wsGetAddress("123456",body);
        loginCall.enqueue(new Callback<getAddress>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<getAddress> call,
                                   Response<getAddress> response) {

                Log.d("getapiaddress",response.toString());
                if (response.isSuccessful()) {

                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddress> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}