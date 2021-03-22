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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.Adapter.SimpleListAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCreateBooking;
import com.mobile.agri10x.models.GetCreateCheckout;
import com.mobile.agri10x.models.GetCreateCheckoutDetails;
import com.mobile.agri10x.models.GetCreateOrder;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.models.QueryCreateCheckOut;
import com.mobile.agri10x.models.QueryCreateCheckOutData;
import com.mobile.agri10x.models.QueryCreatebooking;
import com.mobile.agri10x.models.QueryCreatebookingCartData;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseOrderFargment extends Fragment implements PaymentResultWithDataListener {
    Context context;
    SimpleListAdapter mSimpleListAdapter;
    List<GetCities> getstateArrayList = new ArrayList<>();
    ArrayList<String> statecategory = new ArrayList<>();
    ArrayList<String> billingadrressstringlist = new ArrayList<>();
    ArrayList<String> deliveryadrressstringlist = new ArrayList<>();
    ArrayList<getAddressData> billingadd = new ArrayList<>();
    EditText fname_edt_txt,lname_edt_txt,deliverynote,packagingdatail;
    Spinner addressspinner_billing,addressspinner_delivery,addressspinner_booking_amount;
    TextView totalamt,checkout_btne,paywithecollect,bookingamt,pendingamt,add_billingAddress,addDeliveryaddress;
    ImageView but_back;
    String strbookingamt="",amt="",billingaddressID="",shippingaddressId="",strdelnote="",strpercentval="",strbookingamtval="",strpendingamtval="",strpackagingdetails="",
            strmobileno="",struserid="",strdelcontactperosn="";
    String order_id="", payment_id= "",signature="";
    double damt;
    double pendingamount;
    private static final String[] bookamount = new String[]{
            "Select Booking Amount","25%","50%","75%"
    };
    SearchableSpinner spinner_state_billing_id,spinner_city_billing_id,spinner_state_deliv_id,spinner_city_deliv_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_order_fargment, container, false);
        context = view.getContext();
        pendingamt = view.findViewById(R.id.pendingamt);
        add_billingAddress = view.findViewById(R.id.add_billingAddress);
        addDeliveryaddress = view.findViewById(R.id.addDeliveryaddress);
        bookingamt = view.findViewById(R.id.bookingamt);
        fname_edt_txt=view.findViewById(R.id.fname_edt_txt);
        lname_edt_txt=view.findViewById(R.id.lname_edt_txt);
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
        but_back=view.findViewById(R.id.but_back);
        amt = getArguments().getString("value");
        damt = Double.parseDouble(amt);
        totalamt.setText("₹ "+damt);
        Log.d("getamt",amt);
        Callapiforname();
        CallapigetAddress();
        //callcities();
        checkout_btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struserid = SessionManager.getKeyTokenUser(getActivity());
                strmobileno = SessionManager.getmobilePref(getActivity());
                strdelnote = deliverynote.getText().toString();

               strbookingamt = totalamt.getText().toString();

                strpackagingdetails = packagingdatail.getText().toString();
                strdelcontactperosn = "";
                Log.d("getbookingvalue",strpercentval);
                // && validateDelNot(strdelnote)
                //&& validateContactPerson(strdelcontactperosn)
                //&& validatePackageDetail(strpackagingdetails)
                if(validateUserId(struserid) && validatebillingAdressID(billingaddressID) && validateshippingadd(shippingaddressId)
                        && validateperval(strpercentval)  && validateBookingamt(strbookingamtval) &&  validatePendingAmt(strpendingamtval)
                        && validateMobileNo(strmobileno) )

                {
                    callapicreatebooking(struserid, billingaddressID, shippingaddressId, strdelnote, strbookingamt, strdelcontactperosn, strmobileno, strpackagingdetails);
                }

            }
        });
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
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new PurchaseOrderFargment());
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

    private void callapicreatebooking(String struserid, String billingaddressID, String shippingaddressId,String strdelnote,String strbookingamt
                                     ,String strdelcontactperosn,String strmobileno,String strpackagingdetails) {
        QueryCreateCheckOutData queryCreatebookingCartData = new QueryCreateCheckOutData();
        queryCreatebookingCartData.setMessage("Success");
        queryCreatebookingCartData.setProducts(TradeValueAddCart.ProductsInCartlist);
        queryCreatebookingCartData.setSubTotal(TradeValueAddCart.subTotal);
        queryCreatebookingCartData.setTotalKgs(TradeValueAddCart.totalkgs);


        QueryCreateCheckOut queryCreateCheckOut = new QueryCreateCheckOut();
        queryCreateCheckOut.setUserid(struserid);
        queryCreateCheckOut.setOrderAmount(Double.parseDouble(strbookingamt));
        queryCreateCheckOut.setBillingAddressID(billingaddressID);
        queryCreateCheckOut.setShippingAddressID(shippingaddressId);
        queryCreateCheckOut.setOrderNotes(strdelnote);
        queryCreateCheckOut.setDeliveryContactPerson(strdelcontactperosn);
        queryCreateCheckOut.setDeliveryContactPerson(strmobileno);
        queryCreateCheckOut.setPackagingDetails(strpackagingdetails);
        queryCreateCheckOut.setCartData(queryCreatebookingCartData);




// RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<GetCreateCheckout> loginCall = apiService.wsGetCreateCheckOut(
                "123456", queryCreateCheckOut);
        loginCall.enqueue(new Callback<GetCreateCheckout>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateCheckout> call,
                                   Response<GetCreateCheckout> response) {

                Log.d("resendotpres", response.toString());
                if (response.isSuccessful()) {
                    String bookingid= response.body().getData().getId();
                    Log.d("getbookingid",bookingid);
                    callcreatecheckoutdeatils(bookingid);
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetCreateCheckout> call,
                                  Throwable t) {

            }
        });
    }

    private void callcreatecheckoutdeatils(String bookingid) {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("bookingID",bookingid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetCreateCheckoutDetails> loginCall = apiService.wsGetCheckoutDeatils("123456",body);
        loginCall.enqueue(new Callback<GetCreateCheckoutDetails>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateCheckoutDetails> call,
                                   Response<GetCreateCheckoutDetails> response) {

                Log.d("bookdeatils",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){
                        double  bookingamout = response.body().getData().getOrderAmount();
                        String userid = response.body().getData().getUserID();
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
            public void onFailure(Call<GetCreateCheckoutDetails> call,
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

                        startpayment(razorpay_id,amount,key);
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

    private void startpayment(String razorpay_id, double amount,String key) {

        final PurchaseOrderFargment  activity = this;

        final Checkout co = new Checkout();
        co.setKeyID(key);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Agri10x");
            options.put("description", "(ICognitive Global Pvt Ltd)");
//You can omit the image option to fetch the image from dashboard
            options.put("image", "https://data.agri10x.com/images/Icognitive%20logo2.png");
            options.put("currency", "INR");
            options.put("theme.color", "#5FA30F");
            options.put("order_id", razorpay_id);
            String payment = String.valueOf(amount);        //orderamount.getText().toString();
// amount is in paise so please multiple it by 100
//Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
//            total = total * 100;
            options.put("amount", total);

//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "kamal.bunkar07@gmail.com");
//            preFill.put("contact", "9144040888");

//            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

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
        Dialog deliveryAddressDialog;
        deliveryAddressDialog = new Dialog(context);
        //dialog.setContentView(R.layout.quate_for_price);
        deliveryAddressDialog.setContentView(R.layout.dialog_delivery_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        deliveryAddressDialog.getWindow().setLayout(width,height);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        deliveryAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deliveryAddressDialog.setCancelable(true);
        deliveryAddressDialog.setCanceledOnTouchOutside(true);
        deliveryAddressDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn= deliveryAddressDialog.findViewById(R.id.cancle_btn);
        spinner_state_deliv_id=deliveryAddressDialog.findViewById(R.id.spinner_state_deliv_id);
        spinner_city_deliv_id=deliveryAddressDialog.findViewById(R.id.spinner_city_deliv_id);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryAddressDialog.dismiss();
            }
        });
        deliveryAddressDialog.show();
    }

    private void billingAddressDialog() {
        Dialog dialog;
        dialog = new Dialog(context);
        //dialog.setContentView(R.layout.quate_for_price);
        dialog.setContentView(R.layout.dialog_billing_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn= dialog.findViewById(R.id.cancle_btn);
        spinner_state_billing_id=dialog.findViewById(R.id.spinner_state_billing_id);
        spinner_city_billing_id=dialog.findViewById(R.id.spinner_city_billing_id);

        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


//    private void callcities() {
//        AgriInvestor apiService = ApiHandler.getApiService();
//        final Call<List<GetCities>> loginCall = apiService.wsgetCities(
//                "123456");
//        loginCall.enqueue(new Callback<List<GetCities>>() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onResponse(Call<List<GetCities>> call,
//                                   Response<List<GetCities>> response) {
//
//                if (response.isSuccessful()) {
//                    getstateArrayList = response.body();
//                    Log.d("getresponse", String.valueOf(getstateArrayList.size()));
//
//
//
//
//
//                    if(!getstateArrayList.isEmpty()){
//
//
//                        //                    Commoditycategory.add("Select");
//                        for(int i=0; i < getstateArrayList.size();i++){
//                            statecategory.add(getstateArrayList.get(i).getState());
//                        }
//                        Log.d("statehold", String.valueOf(statecategory.size()));
//
//                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
//                        // commodity.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));
//
//
//                    }else{
//
//                        statecategory.add("No Data");
//                        mSimpleListAdapter = new SimpleListAdapter(context, statecategory);
//                        //commodity.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, statecategory));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<GetCities>> call,
//                                  Throwable t) {
//                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void Callapiforname() {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
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

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        order_id = paymentData.getOrderId();
        payment_id = paymentData.getPaymentId();
        signature = paymentData.getSignature();

        callcheckouthandle(order_id,payment_id,signature);

        Log.d("mainresponse",order_id+ " "+ payment_id+ " "+signature);
    }

    private void callcheckouthandle(String order_id, String payment_id, String signature) {


    }


    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}