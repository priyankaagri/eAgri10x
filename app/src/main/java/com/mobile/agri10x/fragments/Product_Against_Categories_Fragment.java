package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.agri10x.adapters.Product_Against_Categories_Adapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.getCommAccToCat;
import com.mobile.agri10x.models.getCommAccToCatDatum;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_Against_Categories_Fragment extends Fragment {
    List<getCommAccToCatDatum> productincategorylist = new ArrayList<>();
    ShimmerRecyclerView recyle_productCategories;
    ImageView but_back;
    String catid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product__against__categories_, container, false);
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new SeeAllLiveTradingFragment());
            }
        });
        recyle_productCategories=view.findViewById(R.id.recyle_productCategories);
        recyle_productCategories.showShimmer();
        recyle_productCategories.setLayoutManager(new GridLayoutManager(getActivity(),2),R.layout.item_shimmer_daily_deals);
        catid = getArguments().getString("value");

        getProductIncategories(catid);
        return  view;

    }

    private void getProductIncategories(String catid){
        productincategorylist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("categoryID",catid);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<getCommAccToCat> loginCall = apiService.wsGetCommAccToCat("123456",
                body);
        loginCall.enqueue(new Callback<getCommAccToCat>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<getCommAccToCat> call,
                                   Response<getCommAccToCat> response) {
                recyle_productCategories.hideShimmer();

                if (response.isSuccessful()) {
                    productincategorylist.addAll(response.body().getData());
                    if(productincategorylist.size()>0)
                    {
                        Product_Against_Categories_Adapter liveTradeAdapter = new Product_Against_Categories_Adapter(productincategorylist, getActivity(),true);
                        recyle_productCategories.setAdapter(liveTradeAdapter);
                        liveTradeAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(),"Showing 0 products", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getCommAccToCat> call,
                                  Throwable t) {
                recyle_productCategories.hideShimmer();
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}