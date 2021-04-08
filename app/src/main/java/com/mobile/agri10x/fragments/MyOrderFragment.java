package com.mobile.agri10x.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.adapters.BookingorderAdpter;
import com.mobile.agri10x.adapters.PurchaseorderAdpter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetOrderList;
import com.mobile.agri10x.models.GetOrderListDatumBooking;
import com.mobile.agri10x.models.GetOrderListDatumCheckout;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.models.GetWorkerForm;
import com.mobile.agri10x.models.QueryWearHouseForm;
import com.mobile.agri10x.models.QueryWearHouseFormData;
import com.mobile.agri10x.models.QuerytransportForm;
import com.mobile.agri10x.models.QuerytransportFormData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;

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
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderFragment extends Fragment {
Dialog dialog,dialogchoice_of_facility,dialogForWarehouse,dialogForTransportContatct;
    private ImageView mBackButton;
    TextView btn_booking,btn_purchase;
    AlertDialog  formdialog;
    ShimmerRecyclerView recycleview_purchase_list;
    RecyclerView recycleview_booking_list;
    LinearLayoutManager linearLayoutManager;
    LinearLayout booking_layout,purchase_layout,layout_for_booking,layout_for_purchase;
    EditText edt_txt_company_name,edt_txt_fname, edt_txt_lname, edt_txt_phone,edt_txt_email,edt_txt_pricekg;
    ImageView cancle_btn;
    String getSelectedValue,getSelectedTransType,getPrice,getFeatures,getStock;

    int i;
    String fruits;
    String str_state = "",str_state_trasnport="",str_City = "",str_state_wearhouse="",str_stock = "",str_features = "";
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
    String firstname, lastname, phonenumber,companyName,emailId;
    Button btn_submit_worker,btn_submit_transport,btn_submit_wearhouse;
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
    boolean getval= true;
    int getIds=0;
    public static List<GetOrderListDatumCheckout> checkoutorderlist = new ArrayList<>();
    public static List<GetOrderListDatumBooking> bookingorderlist = new ArrayList<>();
    PurchaseorderAdpter purchaseorderAdpter;
    BookingorderAdpter bookingorderAdpter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView=inflater.inflate(R.layout.fragment_your_order_menu_layout,container,false);
        init(addStockView);
        getlistbookingapi();
        getlistorderapi();
        if(getArguments()!=null){
            HomePageActivity.getProductinCart();
            getval = getArguments().getBoolean("getValue");
            getIds=getArguments().getInt("getIds");

        }
        else {

        }
if(getIds==1){
    dialog = new Dialog(getActivity());
    dialog.setContentView(R.layout.layout_choose_facility);

    // int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
    //  int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.getWindow().setGravity(Gravity.CENTER);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(false);
    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
    ImageView cancle_btn=dialog.findViewById(R.id.cancle_btn);
    Button btn_yes=dialog.findViewById(R.id.btn_yes);
    Button btn_no=dialog.findViewById(R.id.btn_no);
    cancle_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});
    btn_no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});
    btn_yes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
       openChooseFacility();
    }
});
    dialog.show();

}

        return addStockView;

    }

    private void getlistbookingapi() {
        //call api here
       // getBookingList 176 fromAgriInvestor

    }

    private void openChooseFacility() {
        dialogchoice_of_facility = new Dialog(getActivity());
        dialogchoice_of_facility.setContentView(R.layout.layout_choice_of_facility);
        dialogchoice_of_facility.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogchoice_of_facility.getWindow().setGravity(Gravity.CENTER);

        dialogchoice_of_facility.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogchoice_of_facility.setCancelable(true);
        dialogchoice_of_facility.setCanceledOnTouchOutside(false);
        dialogchoice_of_facility.getWindow().getAttributes().windowAnimations = R.style.animation;


        ImageView cancle_btn=dialogchoice_of_facility.findViewById(R.id.cancle_btn);
        EditText edt_txt_phone=dialogchoice_of_facility.findViewById(R.id.edt_txt_phone);
        EditText edt_txt_name=dialogchoice_of_facility.findViewById(R.id.edt_txt_name);
        Button btn_submit=dialogchoice_of_facility.findViewById(R.id.btn_submit);
        CheckBox checkbox_warehouse=dialogchoice_of_facility.findViewById(R.id.checkbox_warehouse);
        CheckBox checkbox_transportation=dialogchoice_of_facility.findViewById(R.id.checkbox_transportation);
        checkbox_warehouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkbox_transportation.setChecked(false);
                    checkbox_warehouse.setChecked(true);
                }
            }
        });
        checkbox_transportation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkbox_warehouse.setChecked(false);
                    checkbox_transportation.setChecked(true);
                }
            }
        });
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogchoice_of_facility.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(checkbox_warehouse.isChecked()){
   // Toast.makeText(getActivity(), "checkbox_warehousecheckbox_warehouse", Toast.LENGTH_SHORT).show();
    openDialogForWarehouse();

}else if(checkbox_transportation.isChecked()) {
   openDialogForTranportation();

}else {
    Toast.makeText(getActivity(), "Please Select Warehouse Or Transportation Facility", Toast.LENGTH_SHORT).show();
}
dialogchoice_of_facility.dismiss();
            }
        });

        dialogchoice_of_facility.show();


    }

    private void openDialogForTranportation() {
        dialogForTransportContatct = new Dialog(getActivity());
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

        edt_txt_company_name.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
// TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
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

                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        String stateId = getstateArrayList.get(i).getId();



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

                Toast.makeText(getActivity(), "Thank you for submitting the form. We will get back to you soon", Toast.LENGTH_LONG).show();
                dialogForTransportContatct.dismiss();
            }

            @Override
            public void onFailure(Call<GetWorkerForm> call, Throwable t) {
                formdialog.dismiss();
                dialogForTransportContatct.dismiss();
                Toast.makeText(getActivity(), R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();

            }
        });


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

                    Toast.makeText(getActivity(), R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });

    }

    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getActivity().getAssets().open("transportVehicles.json");
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

    private void openDialogForWarehouse() {
        dialogForWarehouse = new Dialog(getActivity());
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
                if(allFeatures.get(i).equals("Cold Storage")) {
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

                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        String stateId = getstateArrayList.get(i).getId();


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

    private void CallSubmitWarehouseApi(String firstname, String lastname, String emailId, String phonenumber, String str_state, String str_features, String str_stock) {

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

                Toast.makeText(getActivity(), "Thank you for submitting the form. We will get back to you soon", Toast.LENGTH_LONG).show();
                dialogForWarehouse.dismiss();
            }

            @Override
            public void onFailure(Call<GetWorkerForm> call, Throwable t) {
                formdialog.dismiss();
                dialogForWarehouse.dismiss();
                Toast.makeText(getActivity(), R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();

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



                if (response.isSuccessful()) {
// statecategory.add("Select State");

                    getstateArrayList.addAll(response.body().getData());


                    onlystatename.clear();
                    for (int i = 0; i < getstateArrayList.size(); i++) {
                        String state = getstateArrayList.get(i).getState();
                        onlystatename.add(state);

                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlystatename);
                    ss_state_wearhouse.setAdapter(adapter1);
                } else {

                    Toast.makeText(getActivity(), R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


    }

    public String readJSONForStock() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getActivity().getAssets().open("stock.json");
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

    private void getlistorderapi() {
        checkoutorderlist.clear();
        bookingorderlist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        String getuserid =SessionManager.getKeyTokenUser(getActivity());

        getuserid = getuserid.replaceAll(" ","");


        jsonParams.put("UserID",getuserid);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetOrderList> calltoapi = apiService.wsOrdeList("123456",body);

        calltoapi.enqueue(new Callback<GetOrderList>() {
            @Override
            public void onResponse(Call<GetOrderList> call, Response<GetOrderList> response) {


                if (response.isSuccessful()){

                    recycleview_purchase_list.hideShimmer();
                    if (!response.body().getData().isEmpty()) {
                        checkoutorderlist.addAll(response.body().getData().get(0).getCheckoutList());
                        bookingorderlist.addAll(response.body().getData().get(0).getBookingList());
                        if (checkoutorderlist.size() > 0) {
                            purchaseorderAdpter = new PurchaseorderAdpter(checkoutorderlist, getActivity());
                            recycleview_purchase_list.setAdapter(purchaseorderAdpter);
                            purchaseorderAdpter.notifyDataSetChanged();
                        }

                        if (bookingorderlist.size() > 0) {

                            bookingorderAdpter = new BookingorderAdpter(bookingorderlist, getActivity());
                            recycleview_booking_list.setAdapter(bookingorderAdpter);
                            bookingorderAdpter.notifyDataSetChanged();

                        }


                        if (getval) {
                            btn_purchase.setBackgroundResource(R.drawable.hollow_back);
                            btn_booking.setBackgroundResource(R.drawable.filll_back);
                            btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                            btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                            booking_layout.setVisibility(View.VISIBLE);
                            purchase_layout.setVisibility(View.GONE);
                        }
                        if (!getval) {

                            btn_purchase.setBackgroundResource(R.drawable.click_change1);
                            btn_booking.setBackgroundResource(R.drawable.click_chnage2);
                            btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                            btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                            purchase_layout.setVisibility(View.VISIBLE);
                            booking_layout.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOrderList> call, Throwable t) {

                Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void init(View view) {
        mBackButton=view.findViewById(R.id.btn_back_your_order_id);
        btn_booking=view.findViewById(R.id.btn_booking);
        btn_purchase=view.findViewById(R.id.btn_purchase);
        purchase_layout=view.findViewById(R.id.purchase_layout);
        booking_layout=view.findViewById(R.id.booking_layout);
        recycleview_booking_list=view.findViewById(R.id.recycleview_booking_list);
        recycleview_purchase_list=view.findViewById(R.id.recycleview_purchase_list);



        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview_booking_list.setLayoutManager(linearLayoutManager);


/*recycleview_booking_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
recycleview_booking_list.showShimmer();*/

        recycleview_purchase_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_purchase_list.showShimmer();


        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_purchase.setBackgroundResource(R.drawable.click_change1);
                btn_booking.setBackgroundResource(R.drawable.click_chnage2);
                btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                purchase_layout.setVisibility(View.VISIBLE);
                booking_layout.setVisibility(View.GONE);

            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_purchase.setBackgroundResource(R.drawable.hollow_back);
                btn_booking.setBackgroundResource(R.drawable.filll_back);
                btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                booking_layout.setVisibility(View.VISIBLE);
                purchase_layout.setVisibility(View.GONE);

            }


        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new MyOrderFragment());
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
            Toast.makeText(getActivity(),
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
    public boolean validatefirstName(String FirstName) {
        if (FirstName.isEmpty() || FirstName == null) {
            Toast.makeText(getActivity(),
                    "First Name Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validateCompanyName(String CompanyName) {
        if (CompanyName.isEmpty() || CompanyName == null) {
            Toast.makeText(getActivity(),
                    "Company Name Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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