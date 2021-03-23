package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.SimpleListAdapter;
import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetCreateBooking;
import com.mobile.agri10x.models.GetCreateOrder;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.models.QueryCreatebooking;
import com.mobile.agri10x.models.QueryCreatebookingCartData;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


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


public class BookOrderFragment extends Fragment {
    Activity activity;
    Context context;
    SimpleListAdapter mSimpleListAdapter;
    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();
    ArrayList<String> statecategory = new ArrayList<>();
    ArrayList<String> billingadrressstringlist = new ArrayList<>();
    ArrayList<String> deliveryadrressstringlist = new ArrayList<>();
    ArrayList<String> citycategory = new ArrayList<>();
    ArrayList<getAddressData> billingadd = new ArrayList<>();
    Dialog dialogbilling,deliveryAddressDialog;
    EditText fname_edt_txt,lname_edt_txt,deliverynote,packagingdatail,addressline1,addressline2,pincode_txt,addressline1delivery,
            addressline2delivery,pincode_txtdelivery,contactNumber,contactpersonname;


        Spinner addressspinner_billing,addressspinner_delivery,addressspinner_booking_amount,spinner_address_type_billing_id,spinner_address_type_deliv_id;

        TextView totalamt,checkout_btne,paywithecollect,bookingamt,pendingamt;


                ImageView but_back;

    String amt="",billingaddressID="",shippingaddressId="",strdelnote="",strpercentval="",strbookingamtval="",strpendingamtval="",strpackagingdetails="",
            strmobileno="",struserid="",strdelcontactperosn="",str_state,address1_billing_dialog,address2_billing_dialog,pincode_billing_dialog,userid_billing_dialog,
            city_billing_dialog,state_billing_dialog,addrressType_billing_dialog,selectedaddresstype,bookingid="",str_City,stateId,delivery_Str_City,
            delivery_str_state,SelectAddressType;




    double damt;
    double pendingamount;
    boolean isbooking = true;
    private static final String[] bookamount = new String[]{
           "Select Booking Amount","25%","50%","75%"
    };

    private static final String[] addresstypelist = new String[]{
            "Select Address Type", "Warehouse Address", "Collection Center", "Delivery Center", "Gala"
    };

    SearchableSpinner spinner_state_billing_id,spinner_city_billing_id,spinner_state_deliv_id,spinner_city_deliv_id;
                TextView addDeliveryaddress,add_billingAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_order, container, false);
        context = view.getContext();
        activity = (HomePageActivity)getActivity();
        pendingamt = view.findViewById(R.id.pendingamt);
        bookingamt = view.findViewById(R.id.bookingamt);
        fname_edt_txt=view.findViewById(R.id.fname_edt_txt);
        lname_edt_txt=view.findViewById(R.id.lname_edt_txt);
        contactNumber= view.findViewById(R.id.contactNumber);
        contactpersonname = view.findViewById(R.id.contactpersonname);
                addressspinner_billing=view.findViewById(R.id.addressspinner_billing);
        addressspinner_delivery=view.findViewById(R.id.addressspinner_delivery);
        addressspinner_booking_amount=view.findViewById(R.id.addressspinner_booking_amount);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, bookamount);
        addressspinner_booking_amount.setAdapter(adapter1);
        deliverynote=view.findViewById(R.id.deliverynote);
        packagingdatail=view.findViewById(R.id.packagingdatail);
        totalamt=view.findViewById(R.id.totalamt);
        checkout_btne=view.findViewById(R.id.checkout_btne);

        paywithecollect=view.findViewById(R.id.paywithecollect);
        addDeliveryaddress=view.findViewById(R.id.addDeliveryaddress);
        add_billingAddress=view.findViewById(R.id.add_billingAddress);
        but_back=view.findViewById(R.id.but_back);
        amt = getArguments().getString("value");
        damt = Double.parseDouble(amt);

        String pricepeoduct = String.format("%.2f", Double.parseDouble(amt));

        totalamt.setText("₹ " + pricepeoduct);
        Log.d("getamt", String.valueOf(damt));

        CallapigetAddress();


        addressspinner_delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String pos = addressspinner_billing.getSelectedItem().toString();

                Log.d("selectedaddship",pos);
                for(int i= 0 ;i < billingadd.size() ; i++)
                {
                    String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
                    if(pos.equals(addstr)){
                        shippingaddressId = billingadd.get(i).getId();

                    }
                }

             //   Log.d("shiip_add_id",shippingaddressId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

   addressspinner_billing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String pos = addressspinner_billing.getSelectedItem().toString();

        Log.d("selectedaddbilling",pos);
        for(int i= 0 ;i < billingadd.size() ; i++)
        {
            String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
            if(pos.equals(addstr)){
                billingaddressID = billingadd.get(i).getId();

            }
        }

       // Log.d("billing_add_id",billingaddressID);
       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {

       }
   });

        checkout_btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struserid = SessionManager.getKeyTokenUser(getActivity());
                strdelcontactperosn = contactpersonname.getText().toString();
                strmobileno = contactNumber.getText().toString();
                strdelnote = deliverynote.getText().toString();

                strbookingamtval = bookingamt.getText().toString();
                strbookingamtval = strbookingamtval.replaceAll("₹", "");

                strpendingamtval = pendingamt.getText().toString();
                strpendingamtval = strpendingamtval.replaceAll("₹", "");

                strpackagingdetails = packagingdatail.getText().toString();

        Log.d("getbookingvalue",strpercentval);

                if(validateUserId(struserid) && validatebillingAdressID(billingaddressID) && validateshippingadd(shippingaddressId)
                        && validateMobileNo(strmobileno) && validateContactPerson(strdelcontactperosn)
                && validateperval(strpercentval)  && validateBookingamt(strbookingamtval) &&  validatePendingAmt(strpendingamtval)
           )

                {
                    callapicreatebooking(struserid, billingaddressID, shippingaddressId, strdelnote, strpercentval, strbookingamtval, strpendingamtval, strdelcontactperosn, strmobileno, strpackagingdetails);
                }

            }
        });
        add_billingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingAddressDialog();
            }
        });
        addDeliveryaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deliveryAddressDialog();
            }
        });
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new BookOrderFragment());
            }
        });
        paywithecollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strbookingamtval = bookingamt.getText().toString();
                strbookingamtval = strbookingamtval.replaceAll("₹", "");
              if(validateBookingamt(strbookingamtval))  {
                  Bundle bundle = new Bundle();
                  bundle.putString("amount", strbookingamtval);
                    HomePageActivity.setFragment(new Payment_E_Collection_Fragment(),"parmentecollect");
                }

            }
        });
        addressspinner_booking_amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if(item.equals("25%")){
                        strpercentval = "0.25";
                        callpercentageofamt(25,damt);
                    }else if(item.equals("50%"))
                    {
                        strpercentval = "0.50";
                        callpercentageofamt(50,damt);
                    }else if(item.equals("75%"))
                    {
                        strpercentval = "0.75";
                        callpercentageofamt(75,damt);
                    }else if(item.equals("Select Booking Amount")){
                        strpercentval = "Select Booking Amount";

                        bookingamt.setText("");
                        pendingamt.setText("");
                    }

                  //  Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                         strpercentval = "Select Booking Amount";
            }
        });
        return view;
    }
    //calladdress
    private void CallapigetAddress() {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<getAddress> loginCall = apiService.wsGetAddress("123456",body);
        loginCall.enqueue(new Callback<getAddress>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<getAddress> call,
                                   Response<getAddress> response) {

                Log.d("getapiaddress",response.toString());


                if (response.isSuccessful()) {
                    billingadrressstringlist.add("Select Address");
                    deliveryadrressstringlist.add("Select Address");
                    billingadd.addAll(response.body().getData());
                    Log.d("getaddressbilling", String.valueOf(billingadd.size()));


                    for(int i=0; i < billingadd.size();i++){
                        String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
                        billingadrressstringlist.add(addstr);
                        deliveryadrressstringlist.add(addstr);
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, billingadrressstringlist);
                    addressspinner_billing.setAdapter(adapter1);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, deliveryadrressstringlist);
                    addressspinner_delivery.setAdapter(adapter2);

                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddress> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callpercentageofamt(int percentvalue, double damt) {
        if(percentvalue ==25){
            double withtwetyfive = (damt / 100.0f) *25;
            bookingamt.setText("₹ "+withtwetyfive);
            pendingamount = damt - withtwetyfive;
            pendingamt.setText("₹ "+pendingamount);
        }
        else if(percentvalue == 50){
            double withfifty = (damt / 100.0f) *50;
            bookingamt.setText("₹ "+withfifty);
            pendingamount = damt - withfifty;
            pendingamt.setText("₹ "+pendingamount);
        }

        else if (percentvalue == 75){

            double withseventyfive = (damt / 100.0f) *75;
            bookingamt.setText("₹ "+withseventyfive);
            pendingamount = damt - withseventyfive;
            pendingamt.setText("₹ "+pendingamount);
        }
    }

    private void deliveryAddressDialog() {
        deliveryAddressDialog = new Dialog(context);
//dialog.setContentView(R.layout.quate_for_price);
        deliveryAddressDialog.setContentView(R.layout.dialog_delivery_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        deliveryAddressDialog.getWindow().setLayout(width, height);
//dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        deliveryAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deliveryAddressDialog.setCancelable(true);
        deliveryAddressDialog.setCanceledOnTouchOutside(true);
        deliveryAddressDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn = deliveryAddressDialog.findViewById(R.id.cancle_btn);
        Button btn_save_delivery_add_id = deliveryAddressDialog.findViewById(R.id.btn_save_delivery_add_id);
        addressline1delivery = deliveryAddressDialog.findViewById(R.id.addressline1);
        addressline2delivery = deliveryAddressDialog.findViewById(R.id.addressline2);
        pincode_txtdelivery = deliveryAddressDialog.findViewById(R.id.pincode_txt);
        spinner_state_deliv_id = deliveryAddressDialog.findViewById(R.id.spinner_state_deliv_id);
        spinner_city_deliv_id = deliveryAddressDialog.findViewById(R.id.spinner_city_deliv_id);
        spinner_address_type_deliv_id = deliveryAddressDialog.findViewById(R.id.spinner_address_type_deliv_id);
        callApiGetStateDelivery();
        ArrayAdapter<String> addresstypelistAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, addresstypelist);
        spinner_address_type_deliv_id.setAdapter(addresstypelistAdpter);
        btn_save_delivery_add_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1_billing_dialog = addressline1delivery.getText().toString();
                address2_billing_dialog = addressline2delivery.getText().toString();
                pincode_billing_dialog = pincode_txtdelivery.getText().toString();
                userid_billing_dialog = SessionManager.getKeyTokenUser(getActivity());
                city_billing_dialog = delivery_Str_City;
                state_billing_dialog = delivery_str_state;
                addrressType_billing_dialog = SelectAddressType;
                if (valiadadeAddress1(address1_billing_dialog) && validateAddress2(address2_billing_dialog) && ValidatePincode(pincode_billing_dialog) && validatecity(city_billing_dialog) && validatestate(state_billing_dialog) && validateaddresstype(addrressType_billing_dialog))
                {
                    savediliveryaddress(address1_billing_dialog,address2_billing_dialog,pincode_billing_dialog,userid_billing_dialog,city_billing_dialog,state_billing_dialog,addrressType_billing_dialog);
                }

            }


        });
        spinner_address_type_deliv_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                SelectAddressType = spinner_address_type_deliv_id.getSelectedItem().toString();

// Toast.makeText(context, selected_val , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

            }
        });
        spinner_city_deliv_id.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_city_deliv_id.getSelectedItem().toString();
                delivery_Str_City = pos;
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
        spinner_state_deliv_id.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_state_deliv_id.getSelectedItem().toString();
                delivery_str_state=pos;
                Log.d("selectedaddship", pos);
                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        stateId = getstateArrayList.get(i).getId();
                        Log.d("stateId", stateId);
                        callapiGetCitiesDelivery(stateId);

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
                deliveryAddressDialog.dismiss();
            }
        });
        deliveryAddressDialog.show();
    }

    private void callapiGetCitiesDelivery(String stateId) {
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

                    getCityeArrayList.addAll(response.body().getData());
                    Log.d("getaddressbilling", String.valueOf(getCityeArrayList.size()));
                    for (int i = 0; i < getCityeArrayList.size(); i++) {
                        String city = getCityeArrayList.get(i).getCities();
                        citycategory.add(city);

                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, citycategory);
                    spinner_city_deliv_id.setAdapter(adapter2);

                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {

            }
        });


    }

    private void callApiGetStateDelivery() {
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


                    for (int i = 0; i < getstateArrayList.size(); i++) {
                        String state = getstateArrayList.get(i).getState();
                        statecategory.add(state);

                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, statecategory);
                    spinner_state_deliv_id.setAdapter(adapter1);
                } else {

                    Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


    }

    private void savediliveryaddress(String address1_billing_dialog, String address2_billing_dialog, String pincode_billing_dialog, String userid_billing_dialog, String city_billing_dialog, String state_billing_dialog, String addrressType_billing_dialog)
    {
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
        final Call<GetAddAddress> saveaddressapi = apiService.wsGetAddAddress("123456", body);
        saveaddressapi.enqueue(new Callback<GetAddAddress>() {
            @Override
            public void onResponse(Call<GetAddAddress> call, Response<GetAddAddress> response) {
                Log.d("ADDRESSSAVING", response.toString());
                if (response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Address Added Succesfully!", Toast.LENGTH_LONG).show();
                    dialogbilling.dismiss();
                    BookOrderFragment fragment = (BookOrderFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    getActivity().getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();

                } else {

                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAddAddress> call, Throwable t) {
                dialogbilling.dismiss();
                Toast.makeText(getActivity(), "Something went wrong.Please Try Again Later!", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void billingAddressDialog() {

        dialogbilling = new Dialog(context);
//dialog.setContentView(R.layout.quate_for_price);
        dialogbilling.setContentView(R.layout.dialog_billing_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        dialogbilling.getWindow().setLayout(width, height);
//dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogbilling.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogbilling.setCancelable(true);
        dialogbilling.setCanceledOnTouchOutside(true);
        dialogbilling.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn = dialogbilling.findViewById(R.id.cancle_btn);
        Button btn_save_billing_add_id = dialogbilling.findViewById(R.id.btn_save_billing_add_id);
        addressline1 = dialogbilling.findViewById(R.id.addressline1);
        addressline2 = dialogbilling.findViewById(R.id.addressline2);
        pincode_txt = dialogbilling.findViewById(R.id.pincode_txt);
        spinner_state_billing_id = dialogbilling.findViewById(R.id.spinner_state_billing_id);
        spinner_city_billing_id = dialogbilling.findViewById(R.id.spinner_city_billing_id);
        spinner_address_type_billing_id = dialogbilling.findViewById(R.id.spinner_address_type_billing_id);
        ArrayAdapter<String> addresstypelistAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, addresstypelist);
        spinner_address_type_billing_id.setAdapter(addresstypelistAdpter);

        callApiGetState();
        btn_save_billing_add_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1_billing_dialog = addressline1.getText().toString();
                address2_billing_dialog = addressline2.getText().toString();
                pincode_billing_dialog = pincode_txt.getText().toString();
                userid_billing_dialog = SessionManager.getKeyTokenUser(getActivity());
                city_billing_dialog = str_City;
                state_billing_dialog = str_state;
                addrressType_billing_dialog = selectedaddresstype;
                if (valiadadeAddress1(address1_billing_dialog) && validateAddress2(address2_billing_dialog) && ValidatePincode(pincode_billing_dialog) && validatecity(city_billing_dialog) && validatestate(state_billing_dialog) && validateaddresstype(addrressType_billing_dialog))
                {
                    saveaddressapi(address1_billing_dialog,address2_billing_dialog,pincode_billing_dialog,userid_billing_dialog,city_billing_dialog,state_billing_dialog,addrressType_billing_dialog);
                }

            }


        });
        spinner_address_type_billing_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedaddresstype = spinner_address_type_billing_id.getSelectedItem().toString();

// Toast.makeText(context, selected_val , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

            }
        });


        spinner_state_billing_id.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_state_billing_id.getSelectedItem().toString();
                delivery_str_state = pos;
                Log.d("selectedaddship", pos);
                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        stateId = getstateArrayList.get(i).getId();
                        Log.d("stateId", stateId);
                        callapibillingcities(stateId);

                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinner_city_billing_id.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = spinner_city_billing_id.getSelectedItem().toString();
                delivery_Str_City = pos;
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

        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbilling.dismiss();
            }
        });
        dialogbilling.show();
    }

    private void saveaddressapi(String address1_billing_dialog, String address2_billing_dialog, String pincode_billing_dialog, String userid_billing_dialog, String city_billing_dialog, String state_billing_dialog, String addrressType_billing_dialog)
    {
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
        final Call<GetAddAddress> saveaddressapi = apiService.wsGetAddAddress("123456", body);
        saveaddressapi.enqueue(new Callback<GetAddAddress>() {
            @Override
            public void onResponse(Call<GetAddAddress> call, Response<GetAddAddress> response) {
                Log.d("ADDRESSSAVING", response.toString());
                if (response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Address Added Succesfully!", Toast.LENGTH_LONG).show();
                    dialogbilling.dismiss();
                    BookOrderFragment fragment = (BookOrderFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    getActivity().getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();

                } else {

                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAddAddress> call, Throwable t) {
                dialogbilling.dismiss();
                Toast.makeText(getActivity(), "Something went wrong.Please Try Again Later!", Toast.LENGTH_LONG).show();

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

                Log.d("GetCitylist", response.toString());

                if (response.isSuccessful()) {

                    getCityeArrayList.addAll(response.body().getData());
                    Log.d("getaddressbilling", String.valueOf(getCityeArrayList.size()));
                    for (int i = 0; i < getCityeArrayList.size(); i++) {
                        String city = getCityeArrayList.get(i).getCities();
                        citycategory.add(city);

                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, citycategory);
                    spinner_city_billing_id.setAdapter(adapter2);

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


                        for (int i = 0; i < getstateArrayList.size(); i++) {
                            String state = getstateArrayList.get(i).getState();
                            statecategory.add(state);

                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, statecategory);
                        spinner_state_billing_id.setAdapter(adapter1);
                    } else {

                        Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetStates> call, Throwable t) {

                }
            });



    }

    private void Callapiforname() {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetUserByID> loginCall = apiService.wsGetUserById("123456",body);
        loginCall.enqueue(new Callback<GetUserByID>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUserByID> call,
                                   Response<GetUserByID> response) {

                Log.d("getnameapi",response.toString());
                if (response.isSuccessful()) {

                    fname_edt_txt.setText(response.body().getData().getFirstname());
                    lname_edt_txt.setText(response.body().getData().getLastname());
                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserByID> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //validations
    private boolean validateMobileNo(String strmobileno) {
        if (strmobileno.isEmpty() || strmobileno.length() < 10 ) {
            Toast.makeText(getActivity(),
                    "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateContactPerson(String strdelcontactperosn) {
        if (strdelcontactperosn.isEmpty() || strdelcontactperosn == null  ) {
            Toast.makeText(getActivity(),
                    "Pending Amount Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePendingAmt(String strpendingamtval) {
        if (strpendingamtval.isEmpty() || strpendingamtval == null  ) {
            Toast.makeText(getActivity(),
                    "Pending Amount Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateBookingamt(String strbookingamtval) {
        if (strbookingamtval.isEmpty() || strbookingamtval == null  ) {
            Toast.makeText(getActivity(),
                    "Booking Amount Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateperval(String strpercentval) {
        if (strpercentval.isEmpty() || strpercentval == null  || strpercentval.equals("Select Booking Amount")) {
            Toast.makeText(getActivity(),
                    "Select Booking Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateshippingadd(String shippingaddressId) {
        if (shippingaddressId.isEmpty() || shippingaddressId == null ) {
            Toast.makeText(getActivity(),
                    "Delivery  Address Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatebillingAdressID(String billingaddressID) {
        if (billingaddressID.isEmpty() || billingaddressID == null ) {
            Toast.makeText(getActivity(),
                    "Billing Address Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateUserId(String struserid) {
        if (struserid.isEmpty() || struserid == null ) {
            Toast.makeText(getActivity(),
                    "Userid required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateaddresstype(String addrressType) {
        if (addrressType.isEmpty() || addrressType == null) {
            Toast.makeText(getActivity(),
                    "Address Type is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatestate(String state) {
        if (state.isEmpty() || state == null) {
            Toast.makeText(getActivity(),
                    "State is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatecity(String city) {
        if (city.isEmpty() || city == null) {
            Toast.makeText(getActivity(),
                    "city is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean ValidatePincode(String pincode) {
        if (pincode.isEmpty() || pincode.length() < 6) {
            Toast.makeText(getActivity(),
                    "Pincode is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateAddress2(String address2) {
        if (address2.isEmpty() || address2 == null) {
            Toast.makeText(getActivity(),
                    "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean valiadadeAddress1(String address1) {
        if (address1.isEmpty() || address1 == null) {
            Toast.makeText(getActivity(),
                    "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



















    private void callapicreatebooking(String struserid, String billingaddressID, String shippingaddressId,String strdelnote,String strpercentval,
    String strbookingamtval,String strpendingamtval,String strdelcontactperosn,String strmobileno,String strpackagingdetails) {
        QueryCreatebookingCartData  queryCreatebookingCartData = new QueryCreatebookingCartData();
        queryCreatebookingCartData.setMessage("Success");
        queryCreatebookingCartData.setProducts(TradeValueAddCart.ProductsInCartlist);
        queryCreatebookingCartData.setSubTotal(TradeValueAddCart.subTotal);
        queryCreatebookingCartData.setTotalKgs(TradeValueAddCart.totalkgs);


        QueryCreatebooking queryCreatebooking = new QueryCreatebooking();
        queryCreatebooking.setUserid(struserid);
        queryCreatebooking.setBillingAddressID(billingaddressID);
        queryCreatebooking.setShippingAddressID(shippingaddressId);
        queryCreatebooking.setOrderNotes(strdelnote);
        queryCreatebooking.setPercentage(Double.parseDouble(strpercentval));
        queryCreatebooking.setBookingAmount(Double.parseDouble(strbookingamtval));
        queryCreatebooking.setPendingAmount(Double.parseDouble(strpendingamtval));
        queryCreatebooking.setDeliveryContactPerson(strdelcontactperosn);
        queryCreatebooking.setDeliveryContactPerson(strmobileno);
        queryCreatebooking.setPackagingDetails(strpackagingdetails);
        queryCreatebooking.setCartData(queryCreatebookingCartData);




// RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<GetCreateBooking> loginCall = apiService.wsCreateBooking(
                "123456", queryCreatebooking);
        loginCall.enqueue(new Callback<GetCreateBooking>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateBooking> call,
                                   Response<GetCreateBooking> response) {

                Log.d("resendotpres", response.toString());
                if (response.isSuccessful()) {
 bookingid= response.body().getData().getId();
Log.d("getbookingid",bookingid);

               callcreatebookingdeatils(bookingid);
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetCreateBooking> call,
                                  Throwable t) {

            }
        });
    }

    private void callcreatebookingdeatils(String bookingid) {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("bookingID",bookingid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetBookingDeatils> loginCall = apiService.wsGetBookingDeatils("123456",body);
        loginCall.enqueue(new Callback<GetBookingDeatils>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetBookingDeatils> call,
                                   Response<GetBookingDeatils> response) {

                Log.d("bookdeatils",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){

                        double  bookingamout = response.body().getData().get(0).getBookingAmount();
                        String userid = response.body().getData().get(0).getUserID();
                        Log.d("param",bookingamout+ " "+ userid);
                        callCreateOder(bookingamout,userid);
                    }else{

                    }

                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBookingDeatils> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callCreateOder(double bookingamout, String userid) {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("Userid",userid);
        jsonParams.put("amount",bookingamout);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetCreateOrder> loginCall = apiService.wsCheckOrder("123456",body);
        loginCall.enqueue(new Callback<GetCreateOrder>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateOrder> call,
                                   Response<GetCreateOrder> response) {

                Log.d("bookdeatils",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){

String razorpay_id = response.body().getData().getRazorpayOrderid();
double amount = response.body().getData().getAmount();
String key = response.body().getData().getKey();
boolean initialpayment = response.body().getData().getInitiatePayment();

                        ((HomePageActivity)getActivity()).startpayment(razorpay_id,amount,key,bookingid,isbooking);
                    }else{
Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCreateOrder> call,
                                  Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void startpayment(String razorpay_id, double amount, String key) {
//
//        //final ((HomePageActivity)getActivity()) = this;
//
//        final Checkout co = new Checkout();
//        co.setKeyID(key);
//
//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", "Agri10x");
//            options.put("description", "(ICognitive Global Pvt Ltd)");
////You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://data.agri10x.com/images/Icognitive%20logo2.png");
//            options.put("currency", "INR");
//            options.put("theme.color", "#5FA30F");
//            options.put("order_id", razorpay_id);
//            String payment = String.valueOf(amount);        //orderamount.getText().toString();
//// amount is in paise so please multiple it by 100
////Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
//            double total = Double.parseDouble(payment);
////            total = total * 100;
//            options.put("amount", 1);//total
//
////            JSONObject preFill = new JSONObject();
////            preFill.put("email", "kamal.bunkar07@gmail.com");
////            preFill.put("contact", "9144040888");
//
////            options.put("prefill", preFill);
//
//            co.open(activity, options);
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }











    public void callcheckouthandle(String order_id, String payment_id, String signature,String bookingid) {
Toast.makeText(getActivity(),"im there",Toast.LENGTH_SHORT).show();

    }

}