package com.mobile.agri10x.Fragments;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Adapter.AddStockFeatuesAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetAddNewStock;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetCommodity;
import com.mobile.agri10x.models.GetCommodityDatum;
import com.mobile.agri10x.models.GetFeaturesbyCommodity;
import com.mobile.agri10x.models.GetFeaturesbyCommodityDatum;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.models.GetVarieties;
import com.mobile.agri10x.models.GetVarietiesDatum;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.OnItemClickListener;
import com.mobile.agri10x.utils.SessionManager;



import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddStockFragment extends Fragment implements OnItemClickListener {
    private ImageView mBackButton;
    public ArrayList<String> featuresids = new ArrayList<>();
    public ArrayList<String> emptyfeaturesid = new ArrayList<>();
    SearchableSpinner spinner_commodity, spinner_variety, spinner_state, spinner_city;
    EditText edt_quantity, edt_priceperkg, edt_address, edt_taluka, edt_pincode, edt_country, edt_discription;
    TextView txt_valid_from, txt_valid_to;
    Button btn_addstock;
    ImageView btn_back_add_stock_id;
    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();
    ArrayList<String> onlystatename = new ArrayList<>();
    ArrayList<String> onlycityname = new ArrayList<>();
    List<GetCommodityDatum> getcommodityArraylist = new ArrayList<>();
    List<String> onlycommodityname = new ArrayList<>();
    List<GetVarietiesDatum> getvarietyArraylist = new ArrayList<>();
    List<String> onlyvarietyname = new ArrayList<>();
    List<GetFeaturesbyCommodityDatum> getfeatureArraylist = new ArrayList<>();
    RecyclerView recyclerview_features;
    AddStockFeatuesAdapter addStockFeatuesAdapter;
    String str_state="" , str_city="", stateId="" , str_commodity="" , commodity_id="", strDatefrom="", strDateto="" , str_variety="" , str_quantity="" , str_priceperkg="" , str_address="" , str_taluka="", str_pincode="", str_country ="", str_discription="" , str_commodityid="", str_varietyid="" ;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) ;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    long dtDob;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_stock_menu_layout, container, false);
        spinner_commodity =  (SearchableSpinner) view.findViewById(R.id.spinner_commodity);
        spinner_variety = view.findViewById(R.id.spinner_variety);
        edt_quantity = view.findViewById(R.id.edt_quantity);
        edt_priceperkg = view.findViewById(R.id.edt_priceperkg);
        edt_discription = view.findViewById(R.id.edt_discription);
        txt_valid_from = view.findViewById(R.id.txt_valid_from);
        txt_valid_to = view.findViewById(R.id.txt_valid_to);
        edt_address = view.findViewById(R.id.edt_address);
        edt_taluka = view.findViewById(R.id.edt_taluka);
        spinner_city = view.findViewById(R.id.spinner_city);
        spinner_state = view.findViewById(R.id.spinner_state);
        edt_pincode = view.findViewById(R.id.edt_pincode);
        edt_country = view.findViewById(R.id.edt_country);
        btn_addstock = view.findViewById(R.id.btn_addstock);
        btn_back_add_stock_id = view.findViewById(R.id.btn_back_add_stock_id);


        callApiGetState();
        callapigetCommodity();

        recyclerview_features = view.findViewById(R.id.recyclerview_features);
        recyclerview_features.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        btn_back_add_stock_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                HomePageActivity.removeFragment(new AddStockFragment());
            }
        });
        txt_valid_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDlg = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, monthOfYear, year);
                        dtDob = chosenDate.toMillis(true);

                        strDatefrom = String.valueOf(DateFormat.format("yyyy/MM/dd", dtDob));

                        txt_valid_from.setText(strDatefrom);
                    }
                }, year, month, day);

                dateDlg.show();
            }
        });

        txt_valid_to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dateDlg = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, monthOfYear, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDateto = String.valueOf(DateFormat.format("yyyy/MM/dd", dtDob));

                        txt_valid_to.setText(strDateto);
                    }
                }, year, month, day);
                dateDlg.getDatePicker().setMinDate((dtDob));
                dateDlg.show();
            }
        });


        spinner_state.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_state.getSelectedItem().toString();
                str_state = pos;
                Log.d("selectedstatebill", pos);
                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        stateId = getstateArrayList.get(i).getId();
                        Log.d("stateId", stateId);
                        callapigetcities(stateId);

                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinner_city.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_city.getSelectedItem().toString();
                str_city = pos;
                Log.d("selectedaddship", pos);
                for (int i = 0; i < getCityeArrayList.size(); i++) {
                    String addstr = getCityeArrayList.get(i).getCities();
                    if (pos.equals(addstr)) {
                        stateId = getCityeArrayList.get(i).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinner_commodity.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_commodity.getSelectedItem().toString();
                str_commodity = pos;
                Log.d("selectedaddship", pos);
                for (int i = 0; i < getcommodityArraylist.size(); i++) {
                    String addstr = getcommodityArraylist.get(i).getCommodity();
                    if (pos.equals(addstr)) {
                        commodity_id = getcommodityArraylist.get(i).getId();
                        Log.d("commodity_id", commodity_id);
                        str_commodityid = commodity_id;
                        callapigetVariety(commodity_id);
                        callapigetFeatures(commodity_id);
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinner_variety.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_variety.getSelectedItem().toString();
                str_variety = pos;
                Log.d("selectedaddship", pos);
                for (int i = 0; i < getvarietyArraylist.size(); i++) {
                    String addstr = getvarietyArraylist.get(i).getVariety();

                    if (pos.equals(addstr)) {
                        str_varietyid = getvarietyArraylist.get(i).getId();


                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


        btn_addstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_quantity = edt_quantity.getText().toString();
                str_priceperkg = edt_priceperkg.getText().toString();
                str_address = edt_address.getText().toString();
                str_taluka = edt_taluka.getText().toString();
                str_pincode = edt_pincode.getText().toString();
                str_country = edt_country.getText().toString();
                str_discription = edt_discription.getText().toString();
                Log.d("getvalues",str_quantity+" "+str_priceperkg+" "+str_address+" "+str_taluka+" "+str_pincode+" "+str_country+" "+str_discription+" "+str_commodityid+" "+str_varietyid);
                if (validatecommodity(str_commodityid) && validatevariety(str_varietyid)&&validatediscription(str_discription) && validatequantity(str_quantity) && validatepriceperkg(str_priceperkg) &&
                        validatedatefrom(strDatefrom) && validatedateto(strDateto) && validateaddress(str_address) && validatetaluka(str_taluka) &&
                        validatestate(str_state) && validatecity(str_city) && validatepincode(str_pincode) && validatecountry(str_country) && validatefeatures(featuresids))
                {
                    callApiaddStock(str_commodityid, str_varietyid, str_discription, str_quantity, str_priceperkg, strDatefrom, strDateto, str_address, str_taluka, str_state, str_city, str_pincode, str_country);

                }
                else{
                    Toast.makeText(getActivity(),"not valid",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }




    private void callapigetFeatures(String commodity_id) {
        getfeatureArraylist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("commodityID", commodity_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();

        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetFeaturesbyCommodity> call = apiService.wsGetFeaturesbyCommodity("123456", body);
        call.enqueue(new Callback<GetFeaturesbyCommodity>() {
            @Override
            public void onResponse(Call<GetFeaturesbyCommodity> call, Response<GetFeaturesbyCommodity> response) {

                Log.d("getfeatureresponse", response.toString());

                if (response.isSuccessful()) {

                    getfeatureArraylist.addAll(response.body().getData());
                    Log.d("getfeatureArraylist", String.valueOf(getfeatureArraylist.size()));
                    if (getfeatureArraylist.size() > 0) {
                        recyclerview_features.setVisibility(View.VISIBLE);
                        for (int i = 0; i < getfeatureArraylist.size(); i++) {
                            addStockFeatuesAdapter = new AddStockFeatuesAdapter(getActivity(), getfeatureArraylist, AddStockFragment.this);
                            recyclerview_features.setAdapter(addStockFeatuesAdapter);
                        }
                    } else {
                        recyclerview_features.setVisibility(View.GONE);
                    }


                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFeaturesbyCommodity> call, Throwable t) {

            }
        });


    }

    private void callApiaddStock(String str_commodityid, String str_varietyid, String str_discription, String str_quantity, String str_priceperkg, String strDatefrom, String strDateto, String str_address, String str_taluka, String str_state, String str_city, String str_pincode, String str_country) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("commodityID", str_commodityid);
//        if (featuresids.size() > 0) {
//            jsonParams.put("featureID", featuresids);
//        }else{
//            jsonParams.put("featureID", emptyfeaturesid);
//        }
        jsonParams.put("featureID", featuresids);
        jsonParams.put("varietyID", str_varietyid);
        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
        jsonParams.put("stockQuantity", str_quantity);
        jsonParams.put("orderPrice", str_priceperkg);
        jsonParams.put("validFrom", strDatefrom);
        jsonParams.put("validTo", strDateto);
        jsonParams.put("description", str_discription);
        jsonParams.put("isChecked", true);
        jsonParams.put("Active", true);
        jsonParams.put("addressLine1", str_address);
        jsonParams.put("addressLine2", "");
        jsonParams.put("city", str_city);
        jsonParams.put("state", str_state);
        jsonParams.put("country", str_country);
        jsonParams.put("pincode", str_pincode);
        jsonParams.put("latitude", "");
        jsonParams.put("longitude", "");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
//
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetAddNewStock> call = apiService.wsGetAddNewStock("123456", body);
        call.enqueue(new Callback<GetAddNewStock>() {
            @Override
            public void onResponse(Call<GetAddNewStock> call, Response<GetAddNewStock> response) {

                Log.d("addstockresponse", response.toString());

                if (response.isSuccessful()) {
                    HomePageActivity.removeFragment(new AddStockFragment());
                    HomePageActivity.setFragment(new ManageStockFragment(), "stock");

                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAddNewStock> call, Throwable t) {

            }
        });


    }

    private boolean validatecountry(String str_country) {
        if (str_country.isEmpty() || str_country == null) {
            Toast.makeText(getActivity(),
                    "Country is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validatefeatures(ArrayList<String> featuresids) {
        if(recyclerview_features.getVisibility()==View.VISIBLE && featuresids.size()== 0) {
            Toast.makeText(getActivity(),
                    "Select Features", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validatepincode(String str_pincode) {
        if (str_pincode.isEmpty() || str_pincode.length() < 6) {
            Toast.makeText(getActivity(),
                    "Pincode is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatecity(String str_city) {
        if (str_city.isEmpty() || str_city == null) {
            Toast.makeText(getActivity(),
                    "City is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatestate(String str_state) {
        if (str_state.isEmpty() || str_state == null) {
            Toast.makeText(getActivity(),
                    "State is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatediscripition(String str_discription) {
        if (str_discription.isEmpty() || str_discription == null) {
            Toast.makeText(getActivity(),
                    "State is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatetaluka(String str_taluka) {
        if (str_taluka.isEmpty() || str_taluka == null) {
            Toast.makeText(getActivity(),
                    "Taluka is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateaddress(String str_address) {
        if (str_address.isEmpty() || str_address == null) {
            Toast.makeText(getActivity(),
                    "Address is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatedateto(String strDateto) {
        if (strDateto.isEmpty() || strDateto == null) {
            Toast.makeText(getActivity(),
                    "Date To is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatedatefrom(String strDatefrom) {
        if (strDatefrom.isEmpty() || strDatefrom == null) {
            Toast.makeText(getActivity(),
                    "Date From is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatepriceperkg(String str_priceperkg) {
        if (str_priceperkg.isEmpty() || str_priceperkg == null) {
            Toast.makeText(getActivity(),
                    "Price is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatequantity(String str_quantity) {
        if (str_quantity.isEmpty() || str_quantity == null) {
            Toast.makeText(getActivity(),
                    "Quantity is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatevariety(String str_varietyid) {
        if (str_varietyid.isEmpty() || str_varietyid == null) {
            Toast.makeText(getActivity(),
                    "Variety is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validatediscription(String str_discription) {
        if (str_discription.isEmpty() || str_discription == null) {
            Toast.makeText(getActivity(),
                    "Discription is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private boolean validatecommodity(String str_commodityid) {
        if (str_commodityid.isEmpty() || str_commodityid == null) {
            Toast.makeText(getActivity(),
                    "Commodity is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void callapigetVariety(String commodity_id) {
        getvarietyArraylist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("commodityID", commodity_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();

        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetVarieties> call = apiService.wsGetVarieties("123456", body);
        call.enqueue(new Callback<GetVarieties>() {
            @Override
            public void onResponse(Call<GetVarieties> call, Response<GetVarieties> response) {

                Log.d("GetCitylist", response.toString());

                if (response.isSuccessful()) {
                    onlyvarietyname.clear();
                    getvarietyArraylist.addAll(response.body().getData());
                    Log.d("getvariety", String.valueOf(getCityeArrayList.size()));
                    for (int i = 0; i < getvarietyArraylist.size(); i++) {
                        String city = getvarietyArraylist.get(i).getVariety();
                        onlyvarietyname.add(city);

                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlyvarietyname);
                    spinner_variety.setAdapter(adapter2);

                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetVarieties> call, Throwable t) {

            }
        });


    }

    private void callapigetCommodity() {
        getcommodityArraylist.clear();

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<GetCommodity> call = apiService.wsGetCommodity("123456");
        call.enqueue(new Callback<GetCommodity>() {
            @Override
            public void onResponse(Call<GetCommodity> call, Response<GetCommodity> response) {

                Log.d("GetStatelist", response.toString());

                if (response.isSuccessful()) {
// statecategory.add("Select State");

                    getcommodityArraylist.addAll(response.body().getData());
                    Log.d("getaddressbilling", String.valueOf(getstateArrayList.size()));

                    onlycommodityname.clear();
                    for (int i = 0; i < getcommodityArraylist.size(); i++) {
                        String commodity = getcommodityArraylist.get(i).getCommodity();
                        onlycommodityname.add(commodity);

                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, onlycommodityname);
                    spinner_commodity.setAdapter(adapter1);
                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCommodity> call, Throwable t) {

            }
        });


    }

    private void callapigetcities(String stateId) {
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
                    spinner_city.setAdapter(adapter2);

                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {

            }
        });


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
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, onlystatename);
                    spinner_state.setAdapter(adapter1);
                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


    }


    private void removeFragment() {
        HomePageActivity.removeFragment(new AddStockFragment());
    }


    @Override
    public void OnItemClick(ArrayList<String> featuresid) {
        featuresids = featuresid;
        Log.d("featuresidsfomfrag", String.valueOf(featuresids));
    }

}
