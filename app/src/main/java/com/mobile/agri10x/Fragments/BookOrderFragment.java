package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
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

import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
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


public class BookOrderFragment extends Fragment {
EditText fname_edt_txt,lname_edt_txt,deliverynote,packagingdatail;
        Spinner addressspinner_billing,addressspinner_delivery,addressspinner_booking_amount;
                TextView totalamt,checkout_btne,paywithecollect;
                ImageView but_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_order, container, false);
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
        CallapigetAddress();
        return view;
    }

    private void CallapigetAddress() {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<getAddressData> loginCall = apiService.wsGetAddress("123456",body);
        loginCall.enqueue(new Callback<getAddressData>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<getAddressData> call,
                                   Response<getAddressData> response) {

                Log.d("removecart",response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "getSucc", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddressData> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}