package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.LiveTradeAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetLiveTrades;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllLiveTradingFragment extends Fragment {
    List<GetLiveTrades> livetradelist = new ArrayList<>();
    RecyclerView recyle_livetrade;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all_live_trading, container, false);
        recyle_livetrade=view.findViewById(R.id.recyle_livetrade);
        recyle_livetrade.setLayoutManager(new GridLayoutManager(getActivity(),2));
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
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetLiveTrades> loginCall = apiService.wsgetlivetrades("123456",
                body);
        loginCall.enqueue(new Callback<GetLiveTrades>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetLiveTrades> call,
                                   Response<GetLiveTrades> response) {

                Log.d("livetrade",response.toString());
                if (response.isSuccessful()) {
                    // livetradelist.addAll(response.body());
                    if(livetradelist.size()>0)
                    {
                        LiveTradeAdapter liveTradeAdapter = new LiveTradeAdapter(livetradelist, getActivity(),true);
                        recyle_livetrade.setAdapter(liveTradeAdapter);
                    }
                }
                else {
                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetLiveTrades> call,
                                  Throwable t) {

                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}