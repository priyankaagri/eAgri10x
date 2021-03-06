package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;

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

import static com.mobile.agri10x.utils.ToastMessages.makeToast;

public class BottomSheetForAddAddress  extends BottomSheetDialogFragment {

    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();

    ArrayList<String> onlystatename = new ArrayList<>();
    ArrayList<String> onlycityname = new ArrayList<>();

    SearchableSpinner ss_statebilling, ss_citybilling;
    Button btn_billingsaveaddress;
    EditText edt_addressline1billing, edt_addressline2billing, edt_pincodebilling;
    Spinner ss_addtypebilling;
    String selectedaddresstypefromlist = "", str_state="", stateId="", str_City="";
    String address1_billing_dialog = "", address2_billing_dialog = "", pincode_billing_dialog = "", userid_billing_dialog = "",
            city_billing_dialog = "", state_billing_dialog = "", addrressType_billing_dialog = "";


    private static final String[] addresstypelist = new String[]{
            "Select Address Type", "Warehouse Address", "Collection Center", "Delivery Center", "Galla"
    };



    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {

        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_view, null);
        dialog.setContentView(view);
        initUI(view);

    }

    private void initUI(View view) {

        btn_billingsaveaddress = view.findViewById(R.id.btn_save_billing_add_id);
        edt_addressline1billing = view.findViewById(R.id.addressline1);
        edt_addressline2billing = view.findViewById(R.id.addressline2);
        edt_pincodebilling = view.findViewById(R.id.pincode_txt);
        ss_statebilling = view.findViewById(R.id.spinner_state_billing_id);
        ss_citybilling = view.findViewById(R.id.spinner_city_billing_id);
        ss_addtypebilling = view.findViewById(R.id.spinner_address_type_billing_id);

        ArrayAdapter<String> addresstypelistAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, addresstypelist);
        ss_addtypebilling.setAdapter(addresstypelistAdpter);


        callApiGetState();


        btn_billingsaveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1_billing_dialog = edt_addressline1billing.getText().toString();
                address2_billing_dialog = edt_addressline2billing.getText().toString();
                pincode_billing_dialog = edt_pincodebilling.getText().toString();
                userid_billing_dialog = SessionManager.getKeyTokenUser(getActivity());
                city_billing_dialog = str_City;
                state_billing_dialog = str_state;
                addrressType_billing_dialog = selectedaddresstypefromlist;

                if (valiadadeAddress1(address1_billing_dialog) && validateAddress2(address2_billing_dialog) && validatestate(state_billing_dialog) && validatecity(city_billing_dialog) && ValidatePincode(pincode_billing_dialog) && validateaddresstype(addrressType_billing_dialog)) {
                    savebillingaddress(address1_billing_dialog, address2_billing_dialog, pincode_billing_dialog, userid_billing_dialog, city_billing_dialog, state_billing_dialog, addrressType_billing_dialog);
                }

            }


        });
        ss_addtypebilling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedaddresstypefromlist = ss_addtypebilling.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

            }
        });
        ss_statebilling.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = ss_statebilling.getSelectedItem().toString();
                str_state = pos;

                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        stateId = getstateArrayList.get(i).getId();

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



    }

    private void savebillingaddress(String address1_billing_dialog, String address2_billing_dialog, String pincode_billing_dialog, String userid_billing_dialog, String city_billing_dialog, String state_billing_dialog, String addrressType_billing_dialog) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("addrType", addrressType_billing_dialog);
        jsonParams.put("addr1", address1_billing_dialog);
        jsonParams.put("addr2", address2_billing_dialog);
        jsonParams.put("city", city_billing_dialog);
        jsonParams.put("state", state_billing_dialog);
        jsonParams.put("pincode", pincode_billing_dialog);
        jsonParams.put("userID", userid_billing_dialog);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetAddAddress> saveaddressapi = apiService.wsGetAddAddress("123456", body);
        saveaddressapi.enqueue(new Callback<GetAddAddress>() {
            @Override
            public void onResponse(Call<GetAddAddress> call, Response<GetAddAddress> response) {

                if (response.isSuccessful()) {
                    HomePageActivity.removeFragment(new MyAddressFragment());
                    HomePageActivity.setFragment(new MyAddressFragment(),"addadress");
                    makeToast(getActivity(),getResources().getString(R.string.address_added_successfully));

                    dismiss();


                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetAddAddress> call, Throwable t) {
               dismiss();


            }
        });


    }


    private void callapibillingcities(String stateId) {
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



                if (response.isSuccessful()) {
                    onlycityname.clear();
                    getCityeArrayList.addAll(response.body().getData());

                    for (int i = 0; i < getCityeArrayList.size(); i++) {
                        String city = getCityeArrayList.get(i).getCities();
                        onlycityname.add(city);

                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlycityname);
                    ss_citybilling.setAdapter(adapter2);

                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
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



                if (response.isSuccessful()) {
// statecategory.add("Select State");

                    getstateArrayList.addAll(response.body().getData());


                    onlystatename.clear();
                    for (int i = 0; i < getstateArrayList.size(); i++) {
                        String state = getstateArrayList.get(i).getState();
                        onlystatename.add(state);

                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlystatename);
                    ss_statebilling.setAdapter(adapter1);
                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


    }


    //validations
    private boolean validateaddresstype(String addrressType) {
        if (addrressType.isEmpty() || addrressType == null || addrressType.equals("Select Address Type")) {
            makeToast(getActivity(),getResources().getString(R.string.address_type_required));
            return false;
        }
        return true;
    }

    private boolean validatestate(String state) {
        if (state.isEmpty() || state == null) {
            makeToast(getActivity(),getResources().getString(R.string.state_required));
            return false;
        }
        return true;
    }

    private boolean validatecity(String city) {
        if (city.isEmpty() || city == null) {
            makeToast(getActivity(),getResources().getString(R.string.city_required));
            return false;
        }
        return true;
    }

    private boolean ValidatePincode(String pincode) {
        if (pincode.isEmpty() || pincode.length() < 6) {
            makeToast(getActivity(),getResources().getString(R.string.pincode_required));
            return false;
        }
        return true;
    }

    private boolean validateAddress2(String address2) {
        if (address2.isEmpty() || address2 == null) {
            makeToast(getActivity(),getResources().getString(R.string.address_required));
            return false;
        }
        return true;
    }

    private boolean valiadadeAddress1(String address1) {
        if (address1.isEmpty() || address1 == null) {
            makeToast(getActivity(),getResources().getString(R.string.address_required));
            return false;
        }
        return true;
    }


}
