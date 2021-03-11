package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.AdapterHomeCategory;
import com.mobile.agri10x.Adapter.AdapterTopPicks;
import com.mobile.agri10x.Adapter.DailyDealsAdapter;
import com.mobile.agri10x.Adapter.ImageAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetCategoriesData;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQuery;
import com.mobile.agri10x.models.GetQueryTopic;
import com.mobile.agri10x.models.QueryTopicData;
import com.mobile.agri10x.models.Querydata;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recycler_dailydeals, recycler_toppics, caltogerylist_recycle, OfferShopListRecyclerView;
    TextView txt_ViewAll, txt_Viewsee,txt_signups;
    AlertDialog dialog;
    Context context;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    Spinner CommodityUnit;
    ArrayList<String> category = new ArrayList<>();
    List<GetHomeProductData> dealofDay = new ArrayList<>();
    List<GetHomeProductData> toppicks = new ArrayList<>();

    List<GetCategoriesData> catArraylist = new ArrayList<>();
    //  List<FeaturedProduct_Model> featuredproductlist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        context = view.getContext();
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(getActivity());
        mViewPager.setAdapter(adapterView);
        Initview(view);
        clicklister();
        if(SessionManager.isLoggedIn(getActivity().getApplicationContext()))
        {
            txt_signups.setText(" ");
        }else{
            txt_signups.setText("Sign In");
        }
        // GetDailyDealProducts();
        // FetauredproductApi();

        GetCatogerylist();


        txt_signups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                getActivity().startActivity(myIntent);
            }
        });

        return view;
    }

    private void getDailyDeals() {
        Querydata querydata=new Querydata();
        querydata.setDealOfTheDay(false);
        GetQuery query=new GetQuery();
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

        Log.d("resdailydeals",response.toString());
                if (response.isSuccessful()) {
                dealofDay.addAll(response.body().getData());
                if(dealofDay.size()>0)
                {
                    DailyDealsAdapter dailyDealsAdapter = new DailyDealsAdapter(dealofDay, context);
                    recycler_dailydeals.setAdapter(dailyDealsAdapter);
                }

                gettoppicks();
                } else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {
                gettoppicks();
//                Toast.makeText(Otp_Screen_Activity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gettoppicks() {
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
                    toppicks.addAll(response.body().getData());
                    if(toppicks.size()>0)
                    {
                        AdapterTopPicks adapterTopPicks = new AdapterTopPicks(toppicks, context);
                        recycler_toppics.setAdapter(adapterTopPicks);
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

//    private void FetauredproductApi() {
//        //  dialog = new Alert().pleaseWait();
//        AgriInvestor apiService = ApiHandler.getApiService();
//        Call<List<FeaturedProduct_Model>> call = apiService.get_featuredProduct();
//        call.enqueue(new Callback<List<FeaturedProduct_Model>>() {
//            @Override
//            public void onResponse(Call<List<FeaturedProduct_Model>> call, Response<List<FeaturedProduct_Model>> response) {
//                Log.d("GetCatogerylist", response.toString());
//                if (response.isSuccessful()) {
//                    // dialog.dismiss();
//                    featuredproductlist = response.body();
//
//                    Adapterfeatured adapterfeatured = new Adapterfeatured(featuredproductlist, context);
//                    recycler_dailydeals.setAdapter(adapterfeatured);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<FeaturedProduct_Model>> call, Throwable t) {
//
//            }
//        });
//
//
//    }

    private void GetCatogerylist() {
        dialog = new Alert().pleaseWait();
        AgriInvestor apiService = ApiHandler.getApiService();
        Call<GetCategories> call = apiService.getCategories();
        call.enqueue(new Callback<GetCategories>() {
            @Override
            public void onResponse(Call<GetCategories> call, Response<GetCategories> response) {

                Log.d("GetCatogerylist", response.toString());

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    catArraylist.addAll(response.body().getData());
                    // catArraylist = response.body().getData();
                    for (int i = 0; i < catArraylist.size(); i++) {
                        category.add(response.body().getData().get(i).getCategoryName());
                    }

                    if (catArraylist.size() > 0) {
                        AdapterHomeCategory adapterShopDetails = new AdapterHomeCategory(catArraylist, context);
                        caltogerylist_recycle.setAdapter(adapterShopDetails);
                        adapterShopDetails.notifyDataSetChanged();
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item,
                                category);
                        CommodityUnit.setAdapter(spinnerArrayAdapter);
                    }

                    getDailyDeals();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategories> call, Throwable t) {
                getDailyDeals();
            }
        });


    }

 /*   private void GetDailyDealProducts() {
        //  dialog = new Alert().pleaseWait();

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("topPicks", "false");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<List<GetFeatureProduct>> Call = apiService.get_featuredProduct(body);
        Call.enqueue(new Callback<List<GetFeatureProduct>>() {
            @Override
            public void onResponse(retrofit2.Call<List<GetFeatureProduct>> call, Response<List<GetFeatureProduct>> response) {
                Log.d("GetReqTradeCommodity", response.toString());
                if (response.isSuccessful()) {
                    *//*featuredproductlist = response.body();

                    Adapterfeatured adapterfeatured = new Adapterfeatured(featuredproductlist,context);
                    recycler_dailydeals.setAdapter(adapterfeatured);*//*

                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<GetFeatureProduct>> call, Throwable t) {

            }
        });


    }*/

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

    private void clicklister() {
        txt_ViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.setFragment(new SeeAllDailyDealsFragment());
            }
        });
        txt_Viewsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.setFragment(new SeeAllTopPicksFragment());
            }
        });

    }

    private void Initview(View view) {

        CommodityUnit = view.findViewById(R.id.catagaryname_id);
        recycler_dailydeals = view.findViewById(R.id.recycler_dailydeals);
        recycler_toppics = view.findViewById(R.id.recycler_toppics);
        caltogerylist_recycle = view.findViewById(R.id.caltogerylist_recycle);
        txt_ViewAll = view.findViewById(R.id.txt_ViewAll);
        txt_Viewsee = view.findViewById(R.id.txt_Viewsee);

        txt_signups = view.findViewById(R.id.txt_signups);

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        caltogerylist_recycle.setLayoutManager(linearLayoutManager);

        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_dailydeals.setLayoutManager(linearLayoutManager2);

        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_toppics.setLayoutManager(linearLayoutManager1);


    }
}