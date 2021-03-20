package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.SimpleListAdapter;
import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookOrderFragment extends Fragment {
    Context context;
    SimpleListAdapter mSimpleListAdapter;
    List<GetCities> getstateArrayList = new ArrayList<>();
    ArrayList<String> statecategory = new ArrayList<>();
    ArrayList<String> billingadrressstringlist = new ArrayList<>();
    ArrayList<String> deliveryadrressstringlist = new ArrayList<>();
    ArrayList<getAddressData> billingadd = new ArrayList<>();
    EditText fname_edt_txt,lname_edt_txt,deliverynote,packagingdatail;
        Spinner addressspinner_billing,addressspinner_delivery,addressspinner_booking_amount;
                TextView totalamt,checkout_btne,paywithecollect,bookingamt,pendingamt;
                ImageView but_back;
    String amt;
    double damt;
    double pendingamount;
    private static final String[] bookamount = new String[]{
           "25%","50%","75%"
    };
    SearchableSpinner spinner_state_billing_id,spinner_city_billing_id;
                TextView addDeliveryaddress,add_billingAddress,spinner_state_deliv_id,spinner_city_deliv_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_order, container, false);
        context = view.getContext();
        pendingamt = view.findViewById(R.id.pendingamt);
        bookingamt = view.findViewById(R.id.bookingamt);
        fname_edt_txt=view.findViewById(R.id.fname_edt_txt);
        lname_edt_txt=view.findViewById(R.id.lname_edt_txt);
        addressspinner_billing=view.findViewById(R.id.addressspinner_billing);
        addressspinner_delivery=view.findViewById(R.id.addressspinner_delivery);
        addressspinner_booking_amount=view.findViewById(R.id.addressspinner_booking_amount);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, bookamount);
        addressspinner_booking_amount.setAdapter(adapter1);
        deliverynote=view.findViewById(R.id.deliverynote);
        packagingdatail=view.findViewById(R.id.packagingdatail);
        totalamt=view.findViewById(R.id.totalamt);
        checkout_btne=view.findViewById(R.id.checkout_btne);
        paywithecollect=view.findViewById(R.id.paywithecollect);
        addDeliveryaddress=view.findViewById(R.id.addDeliveryaddress);
        add_billingAddress=view.findViewById(R.id.add_billingAddress);
        but_back=view.findViewById(R.id.but_back);
        amt = getArguments().getString("value");
        damt = Double.parseDouble(amt);
        totalamt.setText("₹ "+damt);
        Log.d("getamt", String.valueOf(damt));
        Callapiforname();
        CallapigetAddress();
        callcities();
        callpercentageofamt(25,damt);
        add_billingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingAddressDialog();
            }
        });
        addDeliveryaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deliveryAddressDialog();
            }
        });
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new BookOrderFragment());
            }
        });
        paywithecollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.setFragment(new Payment_E_Collection_Fragment(),"parmentecollect");
            }
        });
        addressspinner_booking_amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if(item.equals("25%")){
                        callpercentageofamt(25,damt);
                    }else if(item.equals("50%"))
                    {
                        callpercentageofamt(50,damt);
                    }else if(item.equals("75%"))
                    {
                        callpercentageofamt(75,damt);
                    }
                  //  Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });
        return view;
    }

    private void callpercentageofamt(int percentvalue, double damt) {
        if(percentvalue ==25){
            double withtwetyfive = (damt / 100.0f) *25;
            bookingamt.setText("₹ "+withtwetyfive);
            pendingamount = damt - withtwetyfive;
            pendingamt.setText("₹ "+pendingamount);
        }
        else if(percentvalue == 50){
            double withfifty = (damt / 100.0f) *50;
            bookingamt.setText("₹ "+withfifty);
            pendingamount = damt - withfifty;
            pendingamt.setText("₹ "+pendingamount);
        }

        else if (percentvalue == 75){

            double withseventyfive = (damt / 100.0f) *75;
            bookingamt.setText("₹ "+withseventyfive);
            pendingamount = damt - withseventyfive;
            pendingamt.setText("₹ "+pendingamount);
        }
    }

    private void deliveryAddressDialog() {
        Dialog deliveryAddressDialog;
        deliveryAddressDialog = new Dialog(context);
        //dialog.setContentView(R.layout.quate_for_price);
        deliveryAddressDialog.setContentView(R.layout.dialog_delivery_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        deliveryAddressDialog.getWindow().setLayout(width,height);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        deliveryAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deliveryAddressDialog.setCancelable(true);
        deliveryAddressDialog.setCanceledOnTouchOutside(true);
        deliveryAddressDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn= deliveryAddressDialog.findViewById(R.id.cancle_btn);
         spinner_state_deliv_id=deliveryAddressDialog.findViewById(R.id.spinner_state_deliv_id);
         spinner_city_deliv_id=deliveryAddressDialog.findViewById(R.id.spinner_city_deliv_id);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryAddressDialog.dismiss();
            }
        });
        deliveryAddressDialog.show();
    }

    private void billingAddressDialog() {
        Dialog dialog;
        dialog = new Dialog(context);
        //dialog.setContentView(R.layout.quate_for_price);
        dialog.setContentView(R.layout.dialog_billing_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn= dialog.findViewById(R.id.cancle_btn);
         spinner_state_billing_id=dialog.findViewById(R.id.spinner_state_billing_id);
         spinner_city_billing_id=dialog.findViewById(R.id.spinner_city_billing_id);

        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void Callapiforname() {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
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
                    billingadrressstringlist.add("Select Address");
                    deliveryadrressstringlist.add("Select Address");
                    billingadd.addAll(response.body().getData());
                    Log.d("getaddressbilling", String.valueOf(billingadd.size()));
                    for(int i=0; i < billingadd.size();i++){
                        String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
                        billingadrressstringlist.add(addstr);
                        deliveryadrressstringlist.add(addstr);
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, billingadrressstringlist);
                    addressspinner_billing.setAdapter(adapter1);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, deliveryadrressstringlist);
                    addressspinner_delivery.setAdapter(adapter2);

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

    private void callcities() {

        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<List<GetCities>> loginCall = apiService.wsgetCities(
                "123456");
        loginCall.enqueue(new Callback<List<GetCities>>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<List<GetCities>> call,
                                   Response<List<GetCities>> response) {

                if (response.isSuccessful()) {
                    getstateArrayList = response.body();
                    Log.d("getresponse", String.valueOf(getstateArrayList.size()));



                    if(!getstateArrayList.isEmpty()){


                        //                    Commoditycategory.add("Select");
                        for(int i=0; i < getstateArrayList.size();i++){
                            statecategory.add(getstateArrayList.get(i).getState());
                        }
                        Log.d("statehold", String.valueOf(statecategory.size()));

                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
                        spinner_state_billing_id.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));


                    }else{

                        statecategory.add("No Data");
                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
                        //commodity.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetCities>> call,
                                  Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}