package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.DailyDealsAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.QueryDailyDeals;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeeAllDailyDealsFragment extends Fragment {
    AlertDialog dialog;
    ImageView but_back;
    List<GetHomeProductData> dealofDay = new ArrayList<>();
    RecyclerView recyle_allDailydeals;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all_daily_deals, container, false);
        context = view.getContext();


        recyle_allDailydeals=view.findViewById(R.id.recyle_allDailydeals);
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new SeeAllDailyDealsFragment());
            }
        });
        recyle_allDailydeals.setLayoutManager(new GridLayoutManager(getActivity(),2));

   getAllSeeDeilydeals();




   return view;
    }

    private void getAllSeeDeilydeals() {
        dialog= new Alert().pleaseWait();
        QueryDailyDeals querydata=new QueryDailyDeals();
        querydata.setDealOfTheDay(false);
        GetQueryDailyDeals query=new GetQueryDailyDeals();
        query.setQuery(querydata);

        AgriInvestor apiService = ApiHandler.getApiService();
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetHomeProduct> loginCall = apiService.wsgetHomeProduct("123456",
                query);
        loginCall.enqueue(new Callback<GetHomeProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetHomeProduct> call,
                                   Response<GetHomeProduct> response) {
                dialog.dismiss();
                Log.d("resdailydeals",response.toString());
                if (response.isSuccessful()) {
                    dealofDay.addAll(response.body().getData());
                    if(dealofDay.size()>0)
                    {
                        DailyDealsAdapter dailyDealsAdapter = new DailyDealsAdapter(dealofDay, context,true);
                        recyle_allDailydeals.setAdapter(dailyDealsAdapter);
                    }


                } else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {
dialog.dismiss();
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(getActivity());
            Alert.setCancelable(false)
                    .setTitle(title)
                    .setMessage(body);
            Alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            Alert.create().show();
        }


        public AlertDialog pleaseWait() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }


    }


}