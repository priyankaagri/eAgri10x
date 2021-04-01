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
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.AdapterView;
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
import com.mobile.agri10x.models.GetUser;
import com.mobile.agri10x.models.GetWorkerForm;
import com.mobile.agri10x.models.QueryFeatureOnly;
import com.mobile.agri10x.models.QueryWearHouseForm;
import com.mobile.agri10x.models.QueryWearHouseFormData;
import com.mobile.agri10x.models.QueryWorkerData;
import com.mobile.agri10x.models.QueryWorkerForm;
import com.mobile.agri10x.models.QueryTopicks;
import com.mobile.agri10x.models.QueryDailyDeals;
import com.mobile.agri10x.models.QuerytransportForm;
import com.mobile.agri10x.models.QuerytransportFormData;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    RelativeLayout worker_rel, fpo_rel, trader_rel, farmer_rel,transportation_rel,wearhouse_rel,our_hero_farmer,our_hero_trader,our_hero_grain;
    Dialog dialogforwantwork, dialogForTransportContatct,dialogForWarehouse;
    ImageView cancle_btn;
    Button btn_submit_worker,btn_submit_transport,btn_submit_wearhouse;
    String firstname, lastname, phonenumber,companyName,emailId;
    String str_state = "",str_state_trasnport="",str_City = "",str_state_wearhouse="",str_stock = "",str_features = "";

    EditText edt_txt_company_name,edt_txt_fname, edt_txt_lname, edt_txt_phone,edt_txt_email,edt_txt_pricekg;
    SearchableSpinner ss_statebilling, ss_citybilling,ss_state,spinner_state_transaporatation,ss_state_wearhouse;
    Spinner spinner_tranport_type,spinner_transport_weight,spinner_features,spinner_stock,spinner_stock_type;
    List<String> allNames = new ArrayList<String>();

    List<String> allFruits = new ArrayList<String>();
    List<String> allVegitable = new ArrayList<String>();
    List<String> allFeatures = new ArrayList<String>();
    List<String> newList = new ArrayList<>();
    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();
    ArrayList<String> onlystatename = new ArrayList<>();
    ArrayList<String> onlycityname = new ArrayList<>();
    CheckBox reaper_check, loader_check, sower_check;
    String getSelectedValue,getSelectedTransType,getPrice,getFeatures,getStock;
    int positonInt,i;
    String str1 = "";
    String checkoboxdata = "";
    String fruits;
    final String weightOfTransport[] = {"850 Kgs"};
    final String weightOfTransport1[] = {"1 MT"};
    final String weightOfTransport2[] = {"1.5 MT"};
    final String weightOfTransport3[] = {"2.5 MT"};
    final String weightOfTransport4[] = {"5 MT"};
    final String weightOfTransport5[] = {"7 MT","8 MT","9 MT"};
    final String weightOfTransport6[] = {"10 MT"};
    final String weightOfTransport7[] = {"9 MT"};
    final String weightOfTransport8[] = {"16 MT"};
    final String weightOfTransport9[] = {"21 MT"};
    final String weightOfTransport10[] = {"25 MT"};

    final String onionStock[] = {"onion"};
    RecyclerView recycler_dailydeals, recycler_toppics, recycle_category, OfferShopListRecyclerView;
    ArrayList<GetCategoriesData> categorieslist;

    DiscreteScrollView only_feature_rv;
    TextView txt_ViewAll, txt_Viewsee, txt_signups, txt_i_amworker;
    AlertDialog dialog, formdialog;
    Context context;
    OnlyFeaturedAdapter onlyFeaturedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    private LinearLayoutManager linearLayoutManager3;
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
          callapigetuername();

        }else{
            txt_signups.setText("Sign In");
            txt_signups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    getActivity().startActivity(myIntent);
                }
            });
        }
        // GetDailyDealProducts();
        // FetauredproductApi();
        categorieslist=new ArrayList<>();
        recycle_category=view.findViewById(R.id.recycle_category);
        recycle_category.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        GetCatogerylist();




        our_hero_grain.setOnClickListener(new View.OnClickListener() {
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
        our_hero_trader.setOnClickListener(new View.OnClickListener() {
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
        our_hero_farmer.setOnClickListener(new View.OnClickListener() {
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
        farmer_rel.setOnClickListener(new View.OnClickListener() {
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
        trader_rel.setOnClickListener(new View.OnClickListener() {
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
                btn_submit_worker = dialogforwantwork.findViewById(R.id.btn_submit);

                edt_txt_fname = dialogforwantwork.findViewById(R.id.edt_txt_fname);
                edt_txt_lname = dialogforwantwork.findViewById(R.id.edt_txt_lname);
                edt_txt_phone = dialogforwantwork.findViewById(R.id.edt_txt_phone);
                reaper_check = dialogforwantwork.findViewById(R.id.reaper_check);
                sower_check = dialogforwantwork.findViewById(R.id.sower_check);
                loader_check = dialogforwantwork.findViewById(R.id.loader_check);
                callApiGetState();





                btn_submit_worker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        firstname = edt_txt_fname.getText().toString();
                        lastname = edt_txt_lname.getText().toString();
                        phonenumber = edt_txt_phone.getText().toString();

                        if(validatefirstName(firstname)&&validatelastName(lastname)&&validatephonenumber(phonenumber)&&validatestate(str_state)&&validatecity(str_City)
                                &&validatecheckbox(reaper_check,sower_check,loader_check)){
                            formdialog = new Alert().pleaseWait();
                            CallSworkerformapi(firstname,lastname,phonenumber,str_state,str_City);

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


        transportation_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForTransportContatct = new Dialog(context);
                dialogForTransportContatct.setContentView(R.layout.transportation_connect_us);

                dialogForTransportContatct.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogForTransportContatct.setCancelable(true);
                dialogForTransportContatct.setCanceledOnTouchOutside(true);
                dialogForTransportContatct.getWindow().getAttributes().windowAnimations = R.style.animation;
                cancle_btn = dialogForTransportContatct.findViewById(R.id.cancle_btn);
                btn_submit_transport = dialogForTransportContatct.findViewById(R.id.btn_submit);

                edt_txt_company_name = dialogForTransportContatct.findViewById(R.id.edt_txt_cname);
                edt_txt_fname = dialogForTransportContatct.findViewById(R.id.edt_txt_fname);
                edt_txt_lname = dialogForTransportContatct.findViewById(R.id.edt_txt_lname);
                edt_txt_phone = dialogForTransportContatct.findViewById(R.id.edt_txt_phone);
                edt_txt_email = dialogForTransportContatct.findViewById(R.id.edt_email_id);
                spinner_tranport_type =  dialogForTransportContatct.findViewById(R.id.spinner_type_transport);
                spinner_transport_weight = dialogForTransportContatct.findViewById(R.id.spinner_weight_transport);
                edt_txt_pricekg = dialogForTransportContatct.findViewById(R.id.edt_txt_price);
                spinner_state_transaporatation = dialogForTransportContatct.findViewById(R.id.spinner_state_transaporatation);
                callApiGetStatefortransporatation();

                try {
                    JSONObject object = new JSONObject(readJSON());
                    JSONArray array = object.getJSONArray("data");
                    for ( i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        Log.v("miId",id);
                        String type = jsonObject.getString("type");
                        Log.v("vichleType",type);
                        String weight = jsonObject.getString("weight");
                        Log.v("vihcleWeight",weight);
                        allNames.add(type);

                        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.custom_spinner,allNames);
                        adapter.setDropDownViewResource(R.layout.custom_spinner);
                        //Setting the ArrayAdapter data on the Spinner
                        spinner_tranport_type.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner_tranport_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner_transport_weight.setAdapter(null);
                        if(position==0) {
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.custom_spinner, weightOfTransport);
                            adapter.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);

                        }
                        else if(position==1) {
                            ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), R.layout.custom_spinner, weightOfTransport1);
                            adapter1.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter1);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);
                        }
                        else if(position==2) {
                            ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport2);
                            adapter2.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter2);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);
                        }
                        else if(position==3) {
                            ArrayAdapter adapter3 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport3);
                            adapter3.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter3);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);

                        }
                        else if(position==4) {
                            ArrayAdapter adapter4 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport4);
                            adapter4.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter4);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);


                        } else if(position==5) {
                            ArrayAdapter adapter5 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport5);
                            adapter5.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter5);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);

                        } else if(position==6) {
                            ArrayAdapter adapter6 = new ArrayAdapter(getActivity(),  R.layout.spinner_item, weightOfTransport6);
                            adapter6.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter6);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedTransType);

                        } else if(position==7) {
                            ArrayAdapter adapter7 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport7);
                            adapter7.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter7);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedValue);

                        }
                        else if(position==8) {
                            ArrayAdapter adapter8 = new ArrayAdapter(getActivity(),  R.layout.spinner_item, weightOfTransport8);
                            adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter8);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedValue);

                        }
                        else if(position==9) {
                            ArrayAdapter adapter9 = new ArrayAdapter(getActivity(), R.layout.custom_spinner, weightOfTransport9);
                            adapter9.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter9);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedValue);

                        }
                        else if(position==10) {
                            ArrayAdapter adapter10 = new ArrayAdapter(getActivity(),  R.layout.custom_spinner, weightOfTransport10);
                            adapter10.setDropDownViewResource(R.layout.custom_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_transport_weight.setAdapter(adapter10);
                            getSelectedValue = spinner_transport_weight.getSelectedItem().toString();
                            Log.v("getSpinnerValue",getSelectedValue);
                            getSelectedTransType = spinner_tranport_type.getSelectedItem().toString();
                            Log.v("getSelectedTransType",getSelectedValue);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner_transport_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner_state_transaporatation.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position, long id) {
                        String pos = spinner_state_transaporatation.getSelectedItem().toString();
                        str_state_trasnport = pos;
                        Log.d("selectedstatebill", pos);
                        for (int i = 0; i < getstateArrayList.size(); i++) {
                            String addstr = getstateArrayList.get(i).getState();
                            if (pos.equals(addstr)) {
                                String stateId = getstateArrayList.get(i).getId();
                                Log.d("stateId", stateId);


                            }
                        }
                    }


                    @Override
                    public void onNothingSelected() {

                    }
                });

                btn_submit_transport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        companyName = edt_txt_company_name.getText().toString();
                        firstname = edt_txt_fname.getText().toString();
                        lastname = edt_txt_lname.getText().toString();
                        phonenumber = edt_txt_phone.getText().toString();
                        emailId = edt_txt_email.getText().toString();
                        getPrice = edt_txt_pricekg.getText().toString();



                        if (validateCompanyName(companyName) && validatefirstName(firstname) && validatelastName(lastname) && validatephonenumber(phonenumber)  && validatePrice(getPrice)&& validatestate(str_state_trasnport)) {
                            formdialog = new Alert().pleaseWait();
                            CallSubmitTransportationApi(companyName,firstname, lastname, phonenumber, emailId, str_state,getSelectedValue,getSelectedTransType,getPrice);

                        }

                    }


                });



                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForTransportContatct.dismiss();
                    }
                });


                dialogForTransportContatct.show();


            }

            private void callApiGetStatefortransporatation() {


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

                        Log.d("GetStatelistfortans", response.toString());

                        if (response.isSuccessful()) {


                            getstateArrayList.addAll(response.body().getData());


                            onlystatename.clear();
                            for (int i = 0; i < getstateArrayList.size(); i++) {
                                String state = getstateArrayList.get(i).getState();
                                onlystatename.add(state);

                            }
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlystatename);
                            spinner_state_transaporatation.setAdapter(adapter1);
                        } else {

                            Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetStates> call, Throwable t) {

                    }
                });

            }

        });

        wearhouse_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForWarehouse = new Dialog(context);
                dialogForWarehouse.setContentView(R.layout.warehouse_contact_us);

                dialogForWarehouse.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogForWarehouse.setCancelable(true);
                dialogForWarehouse.setCanceledOnTouchOutside(true);
                dialogForWarehouse.getWindow().getAttributes().windowAnimations = R.style.animation;
                cancle_btn = dialogForWarehouse.findViewById(R.id.cancle_btn);

                btn_submit_wearhouse = dialogForWarehouse.findViewById(R.id.btn_submit);
                edt_txt_fname = dialogForWarehouse.findViewById(R.id.edt_txt_fname);
                edt_txt_lname = dialogForWarehouse.findViewById(R.id.edt_txt_lname);
                edt_txt_phone = dialogForWarehouse.findViewById(R.id.edt_txt_phone);
                edt_txt_email = dialogForWarehouse.findViewById(R.id.edt_email_id);

                spinner_features = dialogForWarehouse.findViewById(R.id.spinner_feature_type);
                spinner_stock = dialogForWarehouse.findViewById(R.id.spinner_stock_type);
                /* ss_stockTypeOfOnion = dialogForWarehouse.findViewById(R.id.spinner_stock_typeonion);*/
                ss_state_wearhouse = dialogForWarehouse.findViewById(R.id.ss_state_wearhouse);
                callApiGetStateforwearhous();
                allFeatures.clear();
                allFruits.clear();
                allVegitable.clear();
                try {
                    JSONObject object = new JSONObject(readJSONForStock());
                    JSONArray array = object.getJSONArray("data");
                    for (i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String feature = jsonObject.getString("feature");
                        Log.v("mfeature", feature);
                        allFeatures.add(feature);
                        if(allFeatures.get(0).equals("Cold Storage")) {
                            JSONArray jsonarrayFruits = jsonObject.getJSONArray("Fruits");
                            for (int j = 0; j < jsonarrayFruits.length(); j++) {
                                fruits = jsonarrayFruits.getString(j);
                                Log.v("jsonfruits", fruits);
                                allFruits.add(fruits);
                            }
                            JSONArray jsonarrayVegitable = jsonObject.getJSONArray("Vegetables");
                            for (int k = 0; k < jsonarrayVegitable.length(); k++) {
                                String vegitable = jsonarrayVegitable.getString(k);
                                Log.v("jsonVegitable", vegitable);
                                allVegitable.add(vegitable);
                            }
                        }
                        /*......Setting a Adapter for All Features*/

                        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.custom_spinner,allFeatures);
                        adapter.setDropDownViewResource(R.layout.custom_spinner);
                        //Setting the ArrayAdapter data on the Spinner
                        spinner_features.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newList.clear();
                spinner_features.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String pos = spinner_features.getSelectedItem().toString();
                        str_features = pos;

                        Log.v("getFeatures", str_features);
                        if (position == 0) {
                            newList.clear();
                            newList = new ArrayList<>(allFruits.size() + allVegitable.size());
                            newList.addAll(allFruits);
                            newList.addAll(allVegitable);
                            ArrayAdapter fruitsAdapter = new ArrayAdapter(getActivity(), R.layout.custom_spinner, newList);
                            fruitsAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            spinner_stock.setAdapter(fruitsAdapter);
                        }
                        if (position == 1) {
                            Log.v("old Storage", "old Storage");
                            ArrayAdapter fruitsAdapter = new ArrayAdapter(getActivity(), R.layout.custom_spinner, onionStock);
                            fruitsAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            spinner_stock.setAdapter(fruitsAdapter);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinner_stock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String pos = spinner_stock.getSelectedItem().toString();
                        str_stock = pos;
                        Log.v( "getStock",str_stock);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ss_state_wearhouse.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position, long id) {
                        String pos = ss_state_wearhouse.getSelectedItem().toString();
                        str_state_wearhouse = pos;
                        Log.d("selectedstatebill", pos);
                        for (int i = 0; i < getstateArrayList.size(); i++) {
                            String addstr = getstateArrayList.get(i).getState();
                            if (pos.equals(addstr)) {
                                String stateId = getstateArrayList.get(i).getId();
                                Log.d("stateId", stateId);

                            }
                        }
                    }
                    @Override
                    public void onNothingSelected() {

                    }
                });
                btn_submit_wearhouse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstname = edt_txt_fname.getText().toString();
                        phonenumber = edt_txt_phone.getText().toString();
                        lastname = edt_txt_lname.getText().toString();
                        emailId = edt_txt_email.getText().toString();
                        if (validatefirstName(firstname) && validatelastName(lastname) && validatephonenumber(phonenumber) &&  validatestate(str_state_wearhouse) && validateFeatures(str_features) && validateStockType(str_stock)) {
                            formdialog = new Alert().pleaseWait();
                            CallSubmitWarehouseApi(firstname, lastname,emailId, phonenumber, str_state,str_features,str_stock);

                        }
                    }
                });
                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForWarehouse.dismiss();
                    }
                });


                dialogForWarehouse.show();
            }
        });
        return view;
    }

    private void callapigetuername() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetUser> loginCall = apiService.wsGetUserById("123456", body);
        loginCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUser> call,
                                   Response<GetUser> response) {

                Log.d("getnameapi", response.toString());
                dialog.dismiss();
                if (response.isSuccessful()) {
                    txt_signups.setText(response.body().getData().getFirstname()+" "+response.body().getData().getLastname());

                    txt_signups.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MenuFragment fragment = new MenuFragment(); // replace your custom fragment class
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                } else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUser> call,
                                  Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiGetStateforwearhous() {


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
                    ss_state_wearhouse.setAdapter(adapter1);
                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


    }



    public void CallSubmitWarehouseApi(String firstname, String lastname, String phonenumber, String emailId, String str_state, String str_features, String str_stock) {

        QueryWearHouseFormData querySubmitData = new QueryWearHouseFormData();
        querySubmitData.setWfName(firstname);
        querySubmitData.setWlName(lastname);
        querySubmitData.setWContactNumber(phonenumber);
        querySubmitData.setWemail(emailId);
        querySubmitData.setWstate(str_state);
        querySubmitData.setWfeature(str_features);
        querySubmitData.setWstock(str_stock);
        querySubmitData.setTemplate("warehouseForm");

        QueryWearHouseForm queryTSubmitForm = new QueryWearHouseForm();
        queryTSubmitForm.setData(querySubmitData);
        Log.v("warehouseForm", queryTSubmitForm.toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetWorkerForm> call = apiService.wsWearHouseForm("123456", queryTSubmitForm);
        call.enqueue(new Callback<GetWorkerForm>() {
            @Override
            public void onResponse(Call<GetWorkerForm> call, Response<GetWorkerForm> response) {
                formdialog.dismiss();
                Log.d("sworkerform", response.toString());
                Toast.makeText(context, "Thank you for submitting the form. We will get back to you soon", Toast.LENGTH_LONG).show();
                dialogForWarehouse.dismiss();
            }

            @Override
            public void onFailure(Call<GetWorkerForm> call, Throwable t) {
                formdialog.dismiss();
                dialogForWarehouse.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = context.getAssets().open("transportVehicles.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        Log.e("mydata", json);
        return json;
    }
    public String readJSONForStock() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = context.getAssets().open("stock.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        Log.e("MyJson", json);
        return json;
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
                    if(featuredonly.size()> 0)
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
        our_hero_grain = view.findViewById(R.id.our_hero_grain);
        our_hero_trader = view.findViewById(R.id.our_hero_trader);
        our_hero_farmer = view.findViewById(R.id.our_hero_farmer);
        transportation_rel = view.findViewById(R.id.transportation_rel);
        wearhouse_rel = view.findViewById(R.id.wearhouse_rel);
        farmer_rel = view.findViewById(R.id.farmer_rel);
        trader_rel = view.findViewById(R.id.trader_rel);
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

    public void CallSubmitTransportationApi(String companyName,String firstname, String lastname, String phonenumber,String emailId, String str_state, String str_transType,String str_tWeight,String str_Price) {

        QuerytransportFormData querySubmitData = new QuerytransportFormData();
        querySubmitData.setTcName(companyName);
        querySubmitData.setTfName(firstname);
        querySubmitData.setTlName(lastname);
        querySubmitData.setTContactNumber(phonenumber);
        querySubmitData.setTemail(emailId);
        querySubmitData.setTstate(str_state);
        querySubmitData.setTtype(str_transType);
        querySubmitData.setTweight(str_tWeight);
        querySubmitData.setTprice(str_Price);
        querySubmitData.setTemplate("transportForm");

        QuerytransportForm queryTSubmitForm = new QuerytransportForm();
        queryTSubmitForm.setData(querySubmitData);
        Log.v("queryTSubmitForm",queryTSubmitForm.toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetWorkerForm> call = apiService.wsTransportForm("123456", queryTSubmitForm);
        call.enqueue(new Callback<GetWorkerForm>() {
            @Override
            public void onResponse(Call<GetWorkerForm> call, Response<GetWorkerForm> response) {
                formdialog.dismiss();
                Log.d("sworkerform", response.toString());
                Toast.makeText(context, "Thank you for submitting the form. We will get back to you soon", Toast.LENGTH_LONG).show();
                dialogForTransportContatct.dismiss();
            }

            @Override
            public void onFailure(Call<GetWorkerForm> call, Throwable t) {
                formdialog.dismiss();
                dialogForTransportContatct.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void CallSworkerformapi(String firstname, String lastname, String phonenumber, String str_state, String str_City) {
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
        QueryWorkerData querySubmitData = new QueryWorkerData();
        querySubmitData.setFirstName(firstname);
        querySubmitData.setLastName(lastname);
        querySubmitData.setWorkerphone(phonenumber);
        querySubmitData.setState(str_state);
        querySubmitData.setCity(str_City);
        querySubmitData.setReaper(reper_check_boolean);
        querySubmitData.setSower(sower_check_boolean);
        querySubmitData.setLoader(loader_check_boolean);
        querySubmitData.setTemplate("workerForm");




        QueryWorkerForm querySubmitForm = new QueryWorkerForm();
        querySubmitForm.setData(querySubmitData);

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetWorkerForm> call = apiService.wsGetWorkerForm("123456", querySubmitForm);
        call.enqueue(new Callback<GetWorkerForm>() {
            @Override
            public void onResponse(Call<GetWorkerForm> call, Response<GetWorkerForm> response) {
                formdialog.dismiss();
                Log.d("sworkerform", response.toString());
                Toast.makeText(context,"Thank you for submitting the form. We will get back to you soon",Toast.LENGTH_LONG).show();
                dialogforwantwork.dismiss();
            }

            @Override
            public void onFailure(Call<GetWorkerForm> call, Throwable t) {
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
    public boolean validateCompanyName(String CompanyName) {
        if (CompanyName.isEmpty() || CompanyName == null) {
            Toast.makeText(getActivity(),
                    "Company Name Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        if (PhoneNumber.isEmpty() || PhoneNumber.length() < 10 ) {
            Toast.makeText(getActivity(),
                    "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
    private boolean validateEmail(String email_fromdialog) {
        if (email_fromdialog.isEmpty() || email_fromdialog == null  ) {
            Toast.makeText(context,
                    "Email Required", Toast.LENGTH_SHORT).show();
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
    public boolean validateFeatures(String str_features) {
        if (str_features.isEmpty() || str_features == null) {
            Toast.makeText(getActivity(),
                    "Features is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validateStockType(String str_stock) {
        if (str_stock.isEmpty() || str_stock == null) {
            Toast.makeText(getActivity(),
                    "Stock Type is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validatePrice(String str_price) {
        if (str_price.isEmpty() || str_price == null) {
            Toast.makeText(getActivity(),
                    "Price is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}