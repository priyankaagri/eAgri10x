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

import com.mobile.agri10x.adapters.LiveTradeAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetLiveTrades;
import com.mobile.agri10x.models.GetLiveTradesData;
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

public class SeeAllLiveTradingFragment extends Fragment {
    List<GetLiveTradesData> livetradelist = new ArrayList<>();
    ShimmerRecyclerView recyle_livetrade;
    ImageView but_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all_live_trading, container, false);
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new SeeAllLiveTradingFragment());
            }
        });
        recyle_livetrade=view.findViewById(R.id.recyle_livetrade);
        recyle_livetrade.showShimmer();
        recyle_livetrade.setLayoutManager(new GridLayoutManager(getActivity(),2),R.layout.item_shimmer_daily_deals);
        getLiveTrade();

        return  view;
    }

    private void getLiveTrade() {
        livetradelist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("orderID","");
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
        final Call<GetLiveTrades> loginCall = apiService.wsgetlivetrades("123456",
                body);
        loginCall.enqueue(new Callback<GetLiveTrades>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetLiveTrades> call,
                                   Response<GetLiveTrades> response) {
                recyle_livetrade.hideShimmer();

                if (response.isSuccessful()) {
                    livetradelist.addAll(response.body().getData());
                    if(livetradelist.size()>0)
                    {
                        LiveTradeAdapter liveTradeAdapter = new LiveTradeAdapter(livetradelist, getActivity(),true);
                        recyle_livetrade.setAdapter(liveTradeAdapter);
                        liveTradeAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetLiveTrades> call,
                                  Throwable t) {
                recyle_livetrade.hideShimmer();

                Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}