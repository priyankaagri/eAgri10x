package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.DailyDealsFeaturedAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.QueryDailyDeals;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeeAllDailyDealsFeaturedFragment extends Fragment {
    ImageView but_back;
    List<GetHomeProductData> dealofDay = new ArrayList<>();
    ShimmerRecyclerView recyle_allDailydeals;
    Context context;
    private LiveNetworkMonitor mNetworkMonitor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all_daily_deals, container, false);
        context = view.getContext();
        mNetworkMonitor=new LiveNetworkMonitor(context);
        recyle_allDailydeals=view.findViewById(R.id.recyle_allDailydeals);
        recyle_allDailydeals.showShimmer();
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new SeeAllDailyDealsFeaturedFragment());
            }
        });
        recyle_allDailydeals .setLayoutManager(new GridLayoutManager(getActivity(),2), R.layout.item_shimmer_daily_deals);
        getAllSeeDeilydeals();




        return view;
    }

    private void getAllSeeDeilydeals() {
        dealofDay.clear();
        QueryDailyDeals querydata=new QueryDailyDeals();
        querydata.setDealOfTheDay(false);
        GetQueryDailyDeals query=new GetQueryDailyDeals();
        query.setQuery(querydata);

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetHomeProduct> loginCall = apiService.wsgetHomeProduct("123456",
                query);
        loginCall.enqueue(new Callback<GetHomeProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetHomeProduct> call,
                                   Response<GetHomeProduct> response) {
                recyle_allDailydeals.hideShimmer();
                Log.d("resdailydeals",response.toString());
                if (response.isSuccessful()) {
                    dealofDay.addAll(response.body().getData());
                    if(dealofDay.size()>0)
                    {
                        DailyDealsFeaturedAdapter dailyDealsAdapter = new DailyDealsFeaturedAdapter(dealofDay, context,true);
                        recyle_allDailydeals.setAdapter(dailyDealsAdapter);
                        dailyDealsAdapter.notifyDataSetChanged();
                    }


                } else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {
                recyle_allDailydeals.hideShimmer();
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

}