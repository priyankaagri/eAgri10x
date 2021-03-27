package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.HomeCategoryAdapter;
import com.mobile.agri10x.Adapter.TopPicksNegotiableAdapter;
import com.mobile.agri10x.Adapter.DailyDealsFeaturedAdapter;
import com.mobile.agri10x.Adapter.ImageAdapter;
import com.mobile.agri10x.Adapter.OnlyFeaturedAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetCategoriesData;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.models.GetSubmitContactForm;
import com.mobile.agri10x.models.QueryFeatureOnly;
import com.mobile.agri10x.models.QuerySubmitData;
import com.mobile.agri10x.models.QuerySubmitForm;
import com.mobile.agri10x.models.QueryTopicks;
import com.mobile.agri10x.models.QueryDailyDeals;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RelativeLayout worker_rel,fpo_rel;
    Dialog dialogforwantwork;
    ImageView cancle_btn;
    Button btn_submit;
    String firstname,lastname,phonenumber;
    String str_state="";
    String  str_City="";
    EditText edt_txt_fname,edt_txt_lname,edt_txt_phone;
    SearchableSpinner ss_statebilling, ss_citybilling;

    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();
    ArrayList<String> onlystatename = new ArrayList<>();
    ArrayList<String> onlycityname = new ArrayList<>();
    CheckBox reaper_check,loader_check,sower_check;
    String str1="";
    String checkoboxdata="";

    RecyclerView recycler_dailydeals, recycler_toppics, recycle_category, OfferShopListRecyclerView;
    ArrayList<GetCategoriesData>categorieslist;

    DiscreteScrollView only_feature_rv;
    TextView txt_ViewAll, txt_Viewsee,txt_signups,txt_i_amworker;
    AlertDialog dialog,formdialog;
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
            //txt_signups.setText(SessionManager.getmobilePref(getActivity().getApplicationContext()));
            txt_signups.setText(" ");
        }else{
            txt_signups.setText("Sign In");
        }
        // GetDailyDealProducts();
        // FetauredproductApi();
        categorieslist=new ArrayList<>();
        recycle_category=view.findViewById(R.id.recycle_category);
        recycle_category.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        GetCatogerylist();



        txt_signups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
        fpo_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SessionManager.isLoggedIn(getContext()))
                {

                    HomePageActivity.setFragment(new MenuFragment(),"menu");
                }else{
                    Intent myIntent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    getActivity().startActivity(myIntent);


                }
            }
        });
        worker_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogforwantwork = new Dialog(context);
                dialogforwantwork.setContentView(R.layout.layout_iwant_towork);
                dialogforwantwork.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogforwantwork.setCancelable(true);
                dialogforwantwork.setCanceledOnTouchOutside(true);
                dialogforwantwork.getWindow().getAttributes().windowAnimations = R.style.animation;
                cancle_btn = dialogforwantwork.findViewById(R.id.cancle_btn);
                ss_statebilling = dialogforwantwork.findViewById(R.id.spinner_state_billing_id);
                ss_citybilling = dialogforwantwork.findViewById(R.id.spinner_city_billing_id);
                btn_submit = dialogforwantwork.findViewById(R.id.btn_submit);

                edt_txt_fname = dialogforwantwork.findViewById(R.id.edt_txt_fname);
                edt_txt_lname = dialogforwantwork.findViewById(R.id.edt_txt_lname);
                edt_txt_phone = dialogforwantwork.findViewById(R.id.edt_txt_phone);
                reaper_check = dialogforwantwork.findViewById(R.id.reaper_check);
                sower_check = dialogforwantwork.findViewById(R.id.sower_check);
                loader_check = dialogforwantwork.findViewById(R.id.loader_check);
                callApiGetState();





                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        firstname = edt_txt_fname.getText().toString();
                        lastname = edt_txt_lname.getText().toString();
                        phonenumber = edt_txt_phone.getText().toString();

                        if(validatefirstName(firstname)&&validatelastName(lastname)&&validatephonenumber(phonenumber)&&validatestate(str_state)&&validatecity(str_City)
                        &&validatecheckbox(reaper_check,sower_check,loader_check)){
                            formdialog = new Alert().pleaseWait();
                            CallSubmitApi(firstname,lastname,phonenumber,str_state,str_City);

                        }

                    }




                });


                ss_statebilling.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position, long id) {
                        String pos = ss_statebilling.getSelectedItem().toString();
                        str_state = pos;
                        Log.d("selectedstatebill", pos);
                        for (int i = 0; i < getstateArrayList.size(); i++) {
                            String addstr = getstateArrayList.get(i).getState();
                            if (pos.equals(addstr)) {
                                String   stateId = getstateArrayList.get(i).getId();
                                Log.d("stateId", stateId);
                                callapibillingcities(stateId);

                            }
                        }
                    }




                    @Override
                    public void onNothingSelected() {

                    }
                });

                ss_citybilling.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position, long id) {
                        String pos = ss_citybilling.getSelectedItem().toString();
                        str_City = pos;
                        Log.d("selectedaddship", pos);
                        for (int i = 0; i < getCityeArrayList.size(); i++) {
                            String addstr = getCityeArrayList.get(i).getCities();
                            if (pos.equals(addstr)) {
                                String   stateId = getCityeArrayList.get(i).getId();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogforwantwork.dismiss();
                    }
                });


                dialogforwantwork.show();




            }
            private void callApiGetState() {


                getstateArrayList.clear();

                AgriInvestor apiService = ApiHandler.getApiService();
                try {
                    SSLCertificateManagment.trustAllHosts();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                Call<GetStates> call = apiService.wsGetStates("123456");
                call.enqueue(new Callback<GetStates>() {
                    @Override
                    public void onResponse(Call<GetStates> call, Response<GetStates> response) {

                        Log.d("GetStatelist", response.toString());

                        if (response.isSuccessful()) {
// statecategory.add("Select State");

                            getstateArrayList.addAll(response.body().getData());
                            Log.d("getaddressbilling", String.valueOf(getstateArrayList.size()));

                            onlystatename.clear();
                            for (int i = 0; i < getstateArrayList.size(); i++) {
                                String state = getstateArrayList.get(i).getState();
                                onlystatename.add(state);

                            }
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlystatename);
                            ss_statebilling.setAdapter(adapter1);
                        } else {

                            Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetStates> call, Throwable t) {

                    }
                });



            }
            private  void callapibillingcities(String stateId) {
                getCityeArrayList.clear();
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("_id", stateId);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
                AgriInvestor apiService = ApiHandler.getApiService();

                try {
                    SSLCertificateManagment.trustAllHosts();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                Call<GetCities> call = apiService.wsGetCities("123456", body);
                call.enqueue(new Callback<GetCities>() {
                    @Override
                    public void onResponse(Call<GetCities> call, Response<GetCities> response) {

                        Log.d("GetCitylist", response.toString());

                        if (response.isSuccessful()) {
                            onlycityname.clear();
                            getCityeArrayList.addAll(response.body().getData());
                            Log.d("getaddressbilling", String.valueOf(getCityeArrayList.size()));
                            for (int i = 0; i < getCityeArrayList.size(); i++) {
                                String city = getCityeArrayList.get(i).getCities();
                                onlycityname.add(city);

                            }
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlycityname);
                            ss_citybilling.setAdapter(adapter2);

                        } else {

                            Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCities> call, Throwable t) {

                    }
                });


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

        Log.d("resdailydeals",response.toString());
                if (response.isSuccessful()) {
                dealofDay.addAll(response.body().getData());
                if(dealofDay.size()>0)
                {
                    DailyDealsFeaturedAdapter dailyDealsAdapter = new DailyDealsFeaturedAdapter(dealofDay, context, false);
                    recycler_dailydeals.setAdapter(dailyDealsAdapter);
                }

                gettoppicks();
                } else {
                    gettoppicks();
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

                Log.d("gettoppicks",response.toString());
                if (response.isSuccessful()) {
                    toppicks.addAll(response.body().getData());

                    if(toppicks.size()>0)
                    {
                        TopPicksNegotiableAdapter adapterTopPicks = new TopPicksNegotiableAdapter(toppicks, context,false);
                        recycler_toppics.setAdapter(adapterTopPicks);
                    }
                    getonlyFeature();
                } else {
                    getonlyFeature();
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
        queryFeatureOnly.setFeatured(true);
        GetQueryFeaturedOnly getQueryFeaturedOnly=new GetQueryFeaturedOnly();
        getQueryFeaturedOnly.setQuery(queryFeatureOnly);

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
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
                    Log.d("getsizefeat", String.valueOf(featuredonly.size()));
                    if(featuredonly.size()>=0)
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
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetCategories> call = apiService.getCategories("123456");
        call.enqueue(new Callback<GetCategories>() {
            @Override
            public void onResponse(Call<GetCategories> call, Response<GetCategories> response) {

                Log.d("GetCatogerylist", response.toString());

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    catArraylist.addAll(response.body().getData());
                    categorieslist.addAll(response.body().getData());
                    // catArraylist = response.body().getData();
               // category.add("Category");
                for (int i = 0; i < catArraylist.size(); i++) {

                        category.add(response.body().getData().get(i).getCategoryName());
                    }

                    if (catArraylist.size() > 0) {


                        HomeCategoryAdapter adapterShopDetails = new HomeCategoryAdapter(catArraylist, context);
                        recycle_category.setAdapter(adapterShopDetails);
                        adapterShopDetails.notifyDataSetChanged();
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                                R.layout.simple_spinner_dropdown_item,
                                category);

                        CommodityUnit.setAdapter(spinnerArrayAdapter);
                    }

                    getDailyDeals();
                } else {
                    dialog.dismiss();
                    getDailyDeals();
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
                HomePageActivity.setFragment(new SeeAllDailyDealsFeaturedFragment(),"dailyDeals");
            }
        });
        txt_Viewsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.setFragment(new SeeAllTopPicksNegotialbeFragment(),"seetoppicks");
            }
        });

    }

    private void Initview(View view) {
        fpo_rel = view.findViewById(R.id.fpo_rel);
        worker_rel = view.findViewById(R.id.worker_rel);
        CommodityUnit = view.findViewById(R.id.catagaryname_id);
        recycler_dailydeals = view.findViewById(R.id.recycler_dailydeals);
        recycler_toppics = view.findViewById(R.id.recycler_toppics);
     //   caltogerylist_recycle = view.findViewById(R.id.caltogerylist_recycle);
        txt_ViewAll = view.findViewById(R.id.txt_ViewAll);
        txt_Viewsee = view.findViewById(R.id.txt_Viewsee);
        only_feature_rv = view.findViewById(R.id.only_feature_rv);
        txt_signups = view.findViewById(R.id.txt_signups);
        txt_i_amworker = view.findViewById(R.id.txt_i_amworker);

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager3 = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //caltogerylist_recycle.setLayoutManager(linearLayoutManager);

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


    public void CallSubmitApi(String firstname, String lastname, String phonenumber, String str_state, String str_City) {
boolean reper_check_boolean,sower_check_boolean,loader_check_boolean;
if(reaper_check.isChecked()){
    reper_check_boolean = true;
}else{
    reper_check_boolean = false;
}

if(sower_check.isChecked()){
    sower_check_boolean = true;
}else{
    sower_check_boolean = false;
}

if(loader_check.isChecked()){
    loader_check_boolean = true;
}else{
    loader_check_boolean = false;
}
        QuerySubmitData querySubmitData = new QuerySubmitData();
        querySubmitData.setFirstName(firstname);
        querySubmitData.setLastName(lastname);
        querySubmitData.setWorkerphone(phonenumber);
        querySubmitData.setState(str_state);
        querySubmitData.setCity(str_City);
        querySubmitData.setReaper(reper_check_boolean);
        querySubmitData.setSower(sower_check_boolean);
        querySubmitData.setLoader(loader_check_boolean);
        querySubmitData.setTemplate("workerForm");


        QuerySubmitForm querySubmitForm = new QuerySubmitForm();
        querySubmitForm.setData(querySubmitData);

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetSubmitContactForm> call = apiService.wsGetSumitContactForm("123456", querySubmitForm);
        call.enqueue(new Callback<GetSubmitContactForm>() {
            @Override
            public void onResponse(Call<GetSubmitContactForm> call, Response<GetSubmitContactForm> response) {
                formdialog.dismiss();
                Log.d("submitform", response.toString());
                Toast.makeText(context,"Thank you for submitting the form. We will get back to you soon",Toast.LENGTH_LONG).show();
                dialogforwantwork.dismiss();
            }

            @Override
            public void onFailure(Call<GetSubmitContactForm> call, Throwable t) {
                formdialog.dismiss();
                dialogforwantwork.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }
    public boolean validatecheckbox(CheckBox reaper_check, CheckBox sower_check, CheckBox loader_check) {
        if (!reaper_check.isChecked() && !sower_check.isChecked() && !loader_check.isChecked() ) {

            Toast.makeText(getActivity(),
                    "Select atleast one checkbox", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return  true;
    }

    public boolean validatefirstName(String FirstName) {
        if (FirstName.isEmpty() || FirstName == null) {
            Toast.makeText(getActivity(),
                    "First Name Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validatelastName(String LastName) {
        if (LastName.isEmpty() || LastName == null) {
            Toast.makeText(getActivity(),
                    "Last Name Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validatecity(String City) {
        if (City.isEmpty() || City == null) {
            Toast.makeText(getActivity(),
                    "City is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validatephonenumber(String PhoneNumber) {
        if (PhoneNumber.isEmpty() || PhoneNumber == null) {
            Toast.makeText(getActivity(),
                    "Mobile Number Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validatestate(String str_state) {
        if (str_state.isEmpty() || str_state == null) {
            Toast.makeText(getActivity(),
                    "State is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}