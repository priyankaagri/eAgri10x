package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.AdapterTopPicks;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryTopic;
import com.mobile.agri10x.models.QueryTopicData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeeAllTopPicksFragment extends Fragment  {
    AlertDialog dialog;
    RecyclerView recyle_alltoppicks;
    List<GetHomeProductData> alltoppicks = new ArrayList<>();
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all_top_picks, container, false);

        context=view.getContext();
        recyle_alltoppicks=view.findViewById(R.id.recyle_alltoppicks);
        recyle_alltoppicks.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getAllSeeTopPicks();
    return  view;

    }

    private void getAllSeeTopPicks() {
        dialog=new Alert().pleaseWait();
        QueryTopicData queryTopicData=new QueryTopicData();
        queryTopicData.setTopPicks(false);
        GetQueryTopic query=new GetQueryTopic();
        query.setQuery(queryTopicData);
        AgriInvestor apiService = ApiHandler.getApiService();
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetHomeProduct> loginCall = apiService.wsgetHomeProductTopic("123456",
                query);
        loginCall.enqueue(new Callback<GetHomeProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetHomeProduct> call,
                                   Response<GetHomeProduct> response) {
                dialog.dismiss();
                Log.d("Toppicks",response.toString());
                if (response.isSuccessful()) {
                    alltoppicks.addAll(response.body().getData());
                    if(alltoppicks.size()>0)
                    {
                        AdapterTopPicks adapterTopPicks = new AdapterTopPicks(alltoppicks, context);
                        recyle_alltoppicks.setAdapter(adapterTopPicks);
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