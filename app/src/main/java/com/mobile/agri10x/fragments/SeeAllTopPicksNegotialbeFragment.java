package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.agri10x.adapters.TopPicksNegotiableAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.QueryTopicks;
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

import static com.mobile.agri10x.utils.ToastMessages.makeToast;


public class SeeAllTopPicksNegotialbeFragment extends Fragment  {

    ImageView but_back;
    ShimmerRecyclerView recyle_alltoppicks;
    List<GetHomeProductData> alltoppicks = new ArrayList<>();
    Context context;
    private LiveNetworkMonitor mNetworkMonitor;

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
        mNetworkMonitor=new LiveNetworkMonitor(context);

        recyle_alltoppicks=view.findViewById(R.id.recyle_alltoppicks);
        recyle_alltoppicks.showShimmer();
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new SeeAllTopPicksNegotialbeFragment());
            }
        });


        recyle_alltoppicks .setLayoutManager(new GridLayoutManager(getActivity(),2),
                R.layout.item_shimmer_daily_deals);
        getAllSeeTopPicks();
        return  view;

    }

    private void getAllSeeTopPicks() {
        alltoppicks.clear();
        QueryTopicks queryTopicData=new QueryTopicks();
        queryTopicData.setTopPicks(false);
        GetQueryTopicPicks query=new GetQueryTopicPicks();
        query.setQuery(queryTopicData);
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetHomeProduct> loginCall = apiService.wsgetHomeProductTopic("123456",
                query);
        loginCall.enqueue(new Callback<GetHomeProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetHomeProduct> call,
                                   Response<GetHomeProduct> response) {
                recyle_alltoppicks.hideShimmer();
;
                if (response.isSuccessful()) {
                    alltoppicks.addAll(response.body().getData());
                    if(alltoppicks.size()>0)
                    {
                        TopPicksNegotiableAdapter adapterTopPicks = new TopPicksNegotiableAdapter(alltoppicks, context,true);
                        recyle_alltoppicks.setAdapter(adapterTopPicks);
                        adapterTopPicks.notifyDataSetChanged();
                    }

                } else {

                    makeToast(getActivity(),getResources().getString(R.string.somethingwentwrong));                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {
                recyle_alltoppicks.hideShimmer();
                makeToast(getActivity(),getResources().getString(R.string.slownetworkdeted));            }
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

    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            makeToast(getActivity(),getResources().getString(R.string.please_check_internet));        }
    }
}