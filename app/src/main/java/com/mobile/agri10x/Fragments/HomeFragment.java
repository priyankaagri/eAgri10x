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
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import com.mobile.agri10x.Adapter.HomeCategoryAdapter;
import com.mobile.agri10x.Adapter.TopPicksAdapter;
import com.mobile.agri10x.Adapter.DailyDealsAdapter;
import com.mobile.agri10x.Adapter.ImageAdapter;
import com.mobile.agri10x.Adapter.OnlyFeaturedAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetCategoriesData;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.QueryFeatureOnly;
import com.mobile.agri10x.models.QueryTopicks;
import com.mobile.agri10x.models.QueryDailyDeals;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recycler_dailydeals, recycler_toppics, caltogerylist_recycle, OfferShopListRecyclerView;
    DiscreteScrollView only_feature_rv;
    TextView txt_ViewAll, txt_Viewsee,txt_signups;
    AlertDialog dialog;
    Context context;
    OnlyFeaturedAdapter onlyFeaturedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    private  LinearLayoutManager linearLayoutManager3;
    Spinner CommodityUnit;
    ArrayList<String> category = new ArrayList<>();
    List<GetHomeProductData> dealofDay = new ArrayList<>();
    List<GetHomeProductData> toppicks = new ArrayList<>();
    List<GetHomeProductData> featuredonly = new ArrayList<>();

    List<GetCategoriesData> catArraylist = new ArrayList<>();
    //  List<FeaturedProduct_Model> featuredproductlist = new ArrayList<>();
    private LiveNetworkMonitor mNetworkMonitor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        context = view.getContext();
        mNetworkMonitor=new LiveNetworkMonitor(context);

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(getActivity());
        mViewPager.setAdapter(adapterView);
        Initview(view);
        clicklister();
        if(SessionManager.isLoggedIn(getActivity().getApplicationContext()))
        {
            txt_signups.setText(SessionManager.getmobilePref(getActivity().getApplicationContext()));
            //txt_signups.setText(" ");
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
        dealofDay.clear();
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

        Log.d("resdailydeals",response.toString());
                if (response.isSuccessful()) {
                dealofDay.addAll(response.body().getData());
                if(dealofDay.size()>0)
                {
                    DailyDealsAdapter dailyDealsAdapter = new DailyDealsAdapter(dealofDay, context, false);
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
       toppicks.clear();
        QueryTopicks queryTopicData=new QueryTopicks();
        queryTopicData.setTopPicks(false);
        GetQueryTopicPicks query=new GetQueryTopicPicks();
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

                Log.d("gettoppicks",response.toString());
                if (response.isSuccessful()) {
                    toppicks.addAll(response.body().getData());
                    if(toppicks.size()>0)
                    {
                        TopPicksAdapter adapterTopPicks = new TopPicksAdapter(toppicks, context,false);
                        recycler_toppics.setAdapter(adapterTopPicks);
                    }
getonlyFeature();
                } else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {
                getonlyFeature();
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getonlyFeature() {
        QueryFeatureOnly queryFeatureOnly=new QueryFeatureOnly();
        queryFeatureOnly.setFeatured(false);
        GetQueryFeaturedOnly getQueryFeaturedOnly=new GetQueryFeaturedOnly();
        getQueryFeaturedOnly.setQuery(queryFeatureOnly);

        AgriInvestor apiService = ApiHandler.getApiService();
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetHomeProduct> loginCall = apiService.wsgetFeatureOnlyProduct("123456",
                getQueryFeaturedOnly);
        loginCall.enqueue(new Callback<GetHomeProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetHomeProduct> call,
                                   Response<GetHomeProduct> response) {

                Log.d("resFeatureonly",response.toString());
                if (response.isSuccessful()) {
                    featuredonly.addAll(response.body().getData());
                    if(featuredonly.size()>0)
                    {
                        onlyFeaturedAdapter = new OnlyFeaturedAdapter(featuredonly, context);
                        InfiniteScrollAdapter wrapper = InfiniteScrollAdapter.wrap(onlyFeaturedAdapter);
                        only_feature_rv.setAdapter(wrapper);
                    }


                } else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetHomeProduct> call,
                                  Throwable t) {

//                Toast.makeText(Otp_Screen_Activity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
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
        catArraylist.clear();
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
category.add("Category");
for (int i = 0; i < catArraylist.size(); i++) {

                        category.add(response.body().getData().get(i).getCategoryName());
                    }

                    if (catArraylist.size() > 0) {
                        HomeCategoryAdapter adapterShopDetails = new HomeCategoryAdapter(catArraylist, context);
                        caltogerylist_recycle.setAdapter(adapterShopDetails);
                        adapterShopDetails.notifyDataSetChanged();
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                                R.layout.simple_spinner_dropdown_item,
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
                dialog.dismiss();
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
        only_feature_rv = view.findViewById(R.id.only_feature_rv);
        txt_signups = view.findViewById(R.id.txt_signups);

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager3 = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        caltogerylist_recycle.setLayoutManager(linearLayoutManager);

        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_dailydeals.setLayoutManager(linearLayoutManager2);

        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_toppics.setLayoutManager(linearLayoutManager1);

        only_feature_rv.smoothScrollBy(500,500);
        only_feature_rv.setItemAnimator(new DefaultItemAnimator());



        only_feature_rv.setOrientation(DSVOrientation.HORIZONTAL);
        only_feature_rv.getCurrentItem();
        only_feature_rv.setItemTransitionTimeMillis(500);
        only_feature_rv.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());

    }


    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}