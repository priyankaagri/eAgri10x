package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.text.Spanned;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetBookOrder;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCitiesDatum;
import com.mobile.agri10x.models.GetCreateBooking;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStatesDatum;
import com.mobile.agri10x.models.GetUser;
import com.mobile.agri10x.models.QueryCreatebooking;
import com.mobile.agri10x.models.QueryCreatebookingCartData;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;


import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
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

import static com.mobile.agri10x.utils.ToastMessages.makeToast;


public class BookOrderFragment extends Fragment {
    Activity activity;
    Context context;
    AlertDialog dialog;

    List<GetStatesDatum> getstateArrayList = new ArrayList<>();
    List<GetCitiesDatum> getCityeArrayList = new ArrayList<>();

    ArrayList<String> onlystatename = new ArrayList<>();
    ArrayList<String> onlycityname = new ArrayList<>();

    ArrayList<getAddressData> billingadd = new ArrayList<>();

    ArrayList<String> onlybillingaddressname = new ArrayList<>();
    ArrayList<String> onlydeladdressname = new ArrayList<>();




    Dialog dialogbilling,dialogdelivery;

    EditText fname_edt_txt,lname_edt_txt,edt_deliverynote,edt_packagingdatail,edt_contactNumber,edt_contactpersonname,
            edt_addressline1billing,edt_addressline2billing,edt_pincodebilling,
            edt_addressline1delivery, edt_addressline2delivery,edt_pincode_txtdelivery;


    Spinner spin_billingaddress,spin_deladdress,ss_selectamoutper,ss_addtypebilling,ss_addtypedel;

    TextView totalamt,paywithgateway,paywithecollect,bookingamt,pendingamt,addDeliveryaddress,add_billingAddress;

    SearchableSpinner ss_statebilling,ss_citybilling,ss_statedel,ss_citydel;

    ImageView but_back;

    String amt="",billingaddressID="",shippingaddressId="",strdelnote="",strpercentinpoint="",strbookingamtasparam="",strpendingamtasparam="",strpackagingdetails="",
            strmobileno="",struserid="",strdelcontactperosn="",
            address1_billing_dialog="",address2_billing_dialog="",pincode_billing_dialog="",userid_billing_dialog="",city_billing_dialog="",state_billing_dialog="",addrressType_billing_dialog="",
            billing_str_state="",billing_str_City="",stateId="",delivery_Str_City="", delivery_str_state="",selectedaddresstypefromlist="",
            bookingid="";



   Button btn_billingsaveaddress, btn_delsaveaddress;

    double damt;
    double pendingamount;
    boolean isbooking = true;
    private static final String[] bookamountlist = new String[]{
           "Select Booking Amount","25%","50%","75%"
    };

    private static final String[] addresstypelist = new String[]{
            "Select Address Type", "Warehouse Address", "Collection Center", "Delivery Center", "Galla"
    };



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
        edt_contactNumber= view.findViewById(R.id.edt_contactNumber);
        edt_contactpersonname = view.findViewById(R.id.edt_contactpersonname);
        spin_billingaddress=view.findViewById(R.id.addressspinner_billing);
        spin_deladdress=view.findViewById(R.id.addressspinner_delivery);
        ss_selectamoutper=view.findViewById(R.id.addressspinner_booking_amount);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, bookamountlist);
        ss_selectamoutper.setAdapter(adapter1);
        edt_deliverynote=view.findViewById(R.id.deliverynote);
        edt_packagingdatail=view.findViewById(R.id.packagingdatail);
        totalamt=view.findViewById(R.id.totalamt);
        paywithgateway=view.findViewById(R.id.checkout_btne);
        paywithecollect=view.findViewById(R.id.paywithecollect);
        addDeliveryaddress=view.findViewById(R.id.addDeliveryaddress);
        add_billingAddress=view.findViewById(R.id.add_billingAddress);
        but_back=view.findViewById(R.id.but_back);
        edt_contactpersonname.setFilters(new InputFilter[] {
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

        edt_packagingdatail.setFilters(new InputFilter[] {
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

        edt_deliverynote.setFilters(new InputFilter[] {
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

        amt = getArguments().getString("value");
        damt = Double.parseDouble(amt);

        double number = Double.parseDouble(amt);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency = format.format(number);
        System.out.println("Currency in INDIA : " + currency);
        String pricepeoduct = String.format("%.2f", Double.parseDouble(amt));
        totalamt.setText(currency);


        callapigetAddress();


        spin_deladdress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String pos = spin_deladdress.getSelectedItem().toString();


                for(int i= 0 ;i < billingadd.size() ; i++)
                {

                    String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
                    if(pos.equals(addstr)){
                        shippingaddressId = billingadd.get(i).getId();

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_billingaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String pos = spin_billingaddress.getSelectedItem().toString();


        for(int i= 0 ;i < billingadd.size() ; i++)
        {

               String  addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";


            if(pos.equals(addstr)){
                billingaddressID = billingadd.get(i).getId();

            }
        }


       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {

       }
   });

        ss_selectamoutper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if(item.equals("25%")){
                        strpercentinpoint = "0.25";
                        callpercentageofamt(25,damt);
                    }else if(item.equals("50%"))
                    {
                        strpercentinpoint = "0.50";
                        callpercentageofamt(50,damt);
                    }else if(item.equals("75%"))
                    {
                        strpercentinpoint = "0.75";
                        callpercentageofamt(75,damt);
                    }else if(item.equals("Select Booking Amount")){
                        strpercentinpoint = "Select Booking Amount";

                        bookingamt.setText("");
                        pendingamt.setText("");
                    }

                    //  Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                strpercentinpoint = "Select Booking Amount";
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

        paywithgateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struserid = SessionManager.getKeyTokenUser(getActivity());
                strdelcontactperosn = edt_contactpersonname.getText().toString();
                strmobileno = edt_contactNumber.getText().toString();
                strdelnote = edt_deliverynote.getText().toString();

                strbookingamtasparam = bookingamt.getText().toString();
                strbookingamtasparam = strbookingamtasparam.replaceAll("₹", "");
                strbookingamtasparam = strbookingamtasparam.replaceAll(",","");
                strbookingamtasparam = strbookingamtasparam.replaceAll("\\s","");


                strpendingamtasparam = pendingamt.getText().toString();
                strpendingamtasparam = strpendingamtasparam.replaceAll("₹", "");
                strpendingamtasparam = strpendingamtasparam.replaceAll(",","");
                strpendingamtasparam = strpendingamtasparam.replaceAll("\\s","");

                strpackagingdetails = edt_packagingdatail.getText().toString();



                if(validateUserId(struserid) && validatebillingAdressID(billingaddressID) && validateshippingadd(shippingaddressId)
                        && validateContactPerson(strdelcontactperosn) && validateMobileNo(strmobileno)
                        && validateperval(strpercentinpoint)  && validateBookingamt(strbookingamtasparam) &&  validatePendingAmt(strpendingamtasparam))

                {
                    callapicreatebooking(struserid, billingaddressID, shippingaddressId, strdelcontactperosn, strmobileno, strpercentinpoint, strbookingamtasparam, strpendingamtasparam, strdelnote, strpackagingdetails);
                }

            }
        });
        paywithecollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struserid = SessionManager.getKeyTokenUser(getActivity());
                strdelcontactperosn = edt_contactpersonname.getText().toString();
                strmobileno = edt_contactNumber.getText().toString();
                strdelnote = edt_deliverynote.getText().toString();

                strbookingamtasparam = bookingamt.getText().toString();
                strbookingamtasparam = strbookingamtasparam.replaceAll("₹", "");
                strbookingamtasparam = strbookingamtasparam.replaceAll(",","");
                strbookingamtasparam = strbookingamtasparam.replaceAll("\\s","");


                strpendingamtasparam = pendingamt.getText().toString();
                strpendingamtasparam = strpendingamtasparam.replaceAll("₹", "");
                strpendingamtasparam = strpendingamtasparam.replaceAll(",","");
                strpendingamtasparam = strpendingamtasparam.replaceAll("\\s","");

                strpackagingdetails = edt_packagingdatail.getText().toString();



              if(validateUserId(struserid) && validatebillingAdressID(billingaddressID) && validateshippingadd(shippingaddressId)
                      && validateContactPerson(strdelcontactperosn) && validateMobileNo(strmobileno)
                      && validateperval(strpercentinpoint)  && validateBookingamt(strbookingamtasparam) &&  validatePendingAmt(strpendingamtasparam))  {



                  Payment_E_Collection_Fragment fragment = new Payment_E_Collection_Fragment(); // replace your custom fragment class
                  Bundle bundle = new Bundle();
                  FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                  bundle.putString("amount",strbookingamtasparam); // use as per your need
                  fragment.setArguments(bundle);
                  fragmentTransaction.add(R.id.nav_host_fragment,fragment);
                  fragmentTransaction.addToBackStack(null);
                  fragmentTransaction.commit();
                }

            }
        });



        return view;
    }

    private void Callapiforname() {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetUser> loginCall = apiService.wsGetUserById("123456",body);
        loginCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUser> call,
                                   Response<GetUser> response) {


                if (response.isSuccessful()) {

                    fname_edt_txt.setText(response.body().getData().getFirstname());
                    lname_edt_txt.setText(response.body().getData().getLastname());
                }
                else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetUser> call,
                                  Throwable t) {
                makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));            }
        });
    }
    private void callapigetAddress() {


        onlybillingaddressname.clear();
        onlydeladdressname.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID",SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<getAddress> loginCall = apiService.wsGetAddress("123456",body);
        loginCall.enqueue(new Callback<getAddress>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<getAddress> call,
                                   Response<getAddress> response) {



                if (response.isSuccessful()) {
                    billingadd.clear();
                    onlybillingaddressname.add("Select Address");
                    onlydeladdressname.add("Select Address");
                    billingadd.addAll(response.body().getData());



                    for(int i=0; i < billingadd.size();i++){
                        String addstr = billingadd.get(i).getAddressLine1()+" , "+billingadd.get(i).getAddressLine2()+" , "+billingadd.get(i).getCity()+" , "+billingadd.get(i).getState()+" , "+billingadd.get(i).getPincode()+" , India";
                        onlybillingaddressname.add(addstr);
                        onlydeladdressname.add(addstr);
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlybillingaddressname);
                    spin_billingaddress.setAdapter(adapter1);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlydeladdressname);
                    spin_deladdress.setAdapter(adapter2);

                }
                else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<getAddress> call,
                                  Throwable t) {
                makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));            }
        });
    }

    private void callpercentageofamt(int percentvalue, double damt) {
        if(percentvalue ==25){
            double withtwetyfive = (damt / 100.0f) *25;



            double number = (damt / 100.0f) *25;
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currency = format.format(number);
            bookingamt.setText(currency);


            pendingamount = damt - withtwetyfive;
            double N1 = pendingamount;
            NumberFormat formatP = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currencyprice = formatP.format(N1);
            pendingamt.setText(currencyprice);

        }
        else if(percentvalue == 50){
            double withfifty = (damt / 100.0f) *50;


            double number = (damt / 100.0f) *50;
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currency = format.format(number);
            bookingamt.setText(currency);


//bookingamt.setText("₹ "+withfifty);
            pendingamount = damt - withfifty;
// pendingamt.setText("₹ "+pendingamount);
            double N1 = pendingamount;
            NumberFormat formatP = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currencyprice = formatP.format(N1);
            pendingamt.setText(currencyprice);
        }

        else if (percentvalue == 75){

            double withseventyfive = (damt / 100.0f) *75;

            double number = (damt / 100.0f) *75;
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currency = format.format(number);
            bookingamt.setText(currency);


// bookingamt.setText("₹ "+withseventyfive);
            pendingamount = damt - withseventyfive;
// pendingamt .setText("₹ "+pendingamount);

            double N1 = pendingamount;
            NumberFormat formatP = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currencyprice = formatP.format(N1);
            pendingamt.setText(currencyprice);
        }
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


                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

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


                if (response.isSuccessful()) {
// statecategory.add("Select State");

                    getstateArrayList.addAll(response.body().getData());

                    onlystatename.clear();

                    for (int i = 0; i < getstateArrayList.size(); i++) {
                        String state = getstateArrayList.get(i).getState();
                        onlystatename.add(state);

                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlystatename);
                    ss_statedel.setAdapter(adapter1);
                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetStates> call, Throwable t) {

            }
        });


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



                if (response.isSuccessful()) {
                    onlycityname.clear();
                    getCityeArrayList.addAll(response.body().getData());

                    for (int i = 0; i < getCityeArrayList.size(); i++) {
                        String city = getCityeArrayList.get(i).getCities();
                        onlycityname.add(city);

                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_expandable_list_item_1, onlycityname);
                    ss_citydel.setAdapter(adapter2);

                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetCities> call, Throwable t) {

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
        btn_billingsaveaddress = dialogbilling.findViewById(R.id.btn_save_billing_add_id);
        edt_addressline1billing = dialogbilling.findViewById(R.id.addressline1);
        edt_addressline2billing = dialogbilling.findViewById(R.id.addressline2);
        edt_pincodebilling = dialogbilling.findViewById(R.id.pincode_txt);
        ss_statebilling = dialogbilling.findViewById(R.id.spinner_state_billing_id);
        ss_citybilling = dialogbilling.findViewById(R.id.spinner_city_billing_id);
        ss_addtypebilling = dialogbilling.findViewById(R.id.spinner_address_type_billing_id);
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
                city_billing_dialog = billing_str_City;
                state_billing_dialog = billing_str_state;
                addrressType_billing_dialog = selectedaddresstypefromlist;

                if (valiadadeAddress1(address1_billing_dialog) && validateAddress2(address2_billing_dialog) && validatestate(state_billing_dialog) && validatecity(city_billing_dialog) && ValidatePincode(pincode_billing_dialog) && validateaddresstype(addrressType_billing_dialog))
                {
                    savebillingaddress(address1_billing_dialog,address2_billing_dialog,pincode_billing_dialog,userid_billing_dialog,city_billing_dialog,state_billing_dialog,addrressType_billing_dialog);
                }

            }


        });
        ss_addtypebilling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedaddresstypefromlist = ss_addtypebilling.getSelectedItem().toString();

// Toast.makeText(context, selected_val , Toast.LENGTH_SHORT).show();
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
                billing_str_state = pos;

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
                billing_str_City = pos;

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

    private void deliveryAddressDialog() {
        dialogdelivery = new Dialog(context);
//dialog.setContentView(R.layout.quate_for_price);
        dialogdelivery.setContentView(R.layout.dialog_delivery_address_layout);
// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        dialogdelivery.getWindow().setLayout(width, height);
//dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogdelivery.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogdelivery.setCancelable(true);
        dialogdelivery.setCanceledOnTouchOutside(true);
        dialogdelivery.getWindow().getAttributes().windowAnimations = R.style.animation;
        ImageView cancle_btn = dialogdelivery.findViewById(R.id.cancle_btn);
        btn_delsaveaddress = dialogdelivery.findViewById(R.id.btn_save_delivery_add_id);
        edt_addressline1delivery = dialogdelivery.findViewById(R.id.addressline1);
        edt_addressline2delivery = dialogdelivery.findViewById(R.id.addressline2);
        edt_pincode_txtdelivery = dialogdelivery.findViewById(R.id.pincode_txt);
        ss_statedel = dialogdelivery.findViewById(R.id.spinner_state_deliv_id);
        ss_citydel = dialogdelivery.findViewById(R.id.spinner_city_deliv_id);
        ss_addtypedel = dialogdelivery.findViewById(R.id.spinner_address_type_deliv_id);
        callApiGetStateDelivery();
        ArrayAdapter<String> addresstypelistAdpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, addresstypelist);
        ss_addtypedel.setAdapter(addresstypelistAdpter);
        btn_delsaveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1_billing_dialog = edt_addressline1delivery.getText().toString();
                address2_billing_dialog = edt_addressline2delivery.getText().toString();
                pincode_billing_dialog = edt_pincode_txtdelivery.getText().toString();
                userid_billing_dialog = SessionManager.getKeyTokenUser(getActivity());
                city_billing_dialog = delivery_Str_City;
                state_billing_dialog = delivery_str_state;
                addrressType_billing_dialog = selectedaddresstypefromlist;

                if (valiadadeAddress1(address1_billing_dialog) && validateAddress2(address2_billing_dialog) && ValidatePincode(pincode_billing_dialog)  && validatestate(state_billing_dialog)&& validatecity(city_billing_dialog) && validateaddresstype(addrressType_billing_dialog))
                {
                    savedeladdress(address1_billing_dialog,address2_billing_dialog,pincode_billing_dialog,userid_billing_dialog,city_billing_dialog,state_billing_dialog,addrressType_billing_dialog);
                }

            }


        });
        ss_addtypedel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectedaddresstypefromlist = ss_addtypedel.getSelectedItem().toString();

// Toast.makeText(context, selected_val , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

            }
        });
        ss_citydel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = ss_citydel.getSelectedItem().toString();
                delivery_Str_City = pos;

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
        ss_statedel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                String pos = ss_statedel.getSelectedItem().toString();
                delivery_str_state =pos;

                for (int i = 0; i < getstateArrayList.size(); i++) {
                    String addstr = getstateArrayList.get(i).getState();
                    if (pos.equals(addstr)) {
                        stateId = getstateArrayList.get(i).getId();

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
                dialogdelivery.dismiss();
            }
        });
        dialogdelivery.show();
    }

    private void savebillingaddress(String address1_billing_dialog, String address2_billing_dialog, String pincode_billing_dialog, String userid_billing_dialog, String city_billing_dialog, String state_billing_dialog, String addrressType_billing_dialog)
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

                    makeToast(getActivity(),getResources().getString(R.string.address_added_successfully));
                    dialogbilling.dismiss();
                    BookOrderFragment fragment = (BookOrderFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    getActivity().getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();

                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetAddAddress> call, Throwable t) {
                dialogbilling.dismiss();
                makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));
            }
        });


    }

    private void savedeladdress(String address1_billing_dialog, String address2_billing_dialog, String pincode_billing_dialog, String userid_billing_dialog, String city_billing_dialog, String state_billing_dialog, String addrressType_billing_dialog)
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

                    makeToast(getActivity(),getResources().getString(R.string.address_added_successfully));
                    dialogbilling.dismiss();
                    BookOrderFragment fragment = (BookOrderFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    getActivity().getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();

                } else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetAddAddress> call, Throwable t) {
                dialogbilling.dismiss();
                makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));
            }
        });


    }




    //validations
    private boolean validateMobileNo(String strmobileno) {
        if (strmobileno.isEmpty() || strmobileno.length() < 10 ) {
            makeToast(getActivity(),getResources().getString(R.string.invalid_mobile));
            return false;
        }
        return true;
    }

    private boolean validateContactPerson(String strdelcontactperosn) {
        if (strdelcontactperosn.isEmpty() || strdelcontactperosn == null  ) {
            makeToast(getActivity(),getResources().getString(R.string.contact_person_required));
            return false;
        }
        return true;
    }

    private boolean validatePendingAmt(String strpendingamtval) {
        if (strpendingamtval.isEmpty() || strpendingamtval == null  ) {
            makeToast(getActivity(),getResources().getString(R.string.pending_amount_required));
            return false;
        }
        return true;
    }

    private boolean validateBookingamt(String strbookingamtval) {
        if (strbookingamtval.isEmpty() || strbookingamtval == null  ) {
            makeToast(getActivity(),getResources().getString(R.string.booking_required));
            return false;
        }
        return true;
    }

    private boolean validateperval(String strpercentval) {
        if (strpercentval.isEmpty() || strpercentval == null  || strpercentval.equals("Select Booking Amount")) {
            makeToast(getActivity(),getResources().getString(R.string.select_booking_amt));
            return false;
        }
        return true;
    }

    private boolean validateshippingadd(String shippingaddressId) {
        if (shippingaddressId.isEmpty() || shippingaddressId == null ) {
            makeToast(getActivity(),getResources().getString(R.string.deliv_add_required));
            return false;
        }
        return true;
    }

    private boolean validatebillingAdressID(String billingaddressID) {
        if (billingaddressID.isEmpty() || billingaddressID == null ) {
            makeToast(getActivity(),getResources().getString(R.string.bill_add_required));
            return false;
        }
        return true;
    }

    private boolean validateUserId(String struserid) {
        if (struserid.isEmpty() || struserid == null ) {
            makeToast(getActivity(),getResources().getString(R.string.userid_required));
            return false;
        }
        return true;
    }

    private boolean validateaddresstype(String addrressType) {
        if (addrressType.isEmpty() || addrressType == null ||addrressType.equals("Select Address Type")) {
            makeToast(getActivity(),getResources().getString(R.string.add_type_required));
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



















    private void callapicreatebooking(String struserid, String billingaddressID, String shippingaddressId,String strdelcontactperosn,String strmobileno,
    String strpercentinpoint,String strbookingamtasparam,String strpendingamtasparam,String strdelnote,String strpackagingdetails) {
        dialog=new Alert().pleaseWait();

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
        queryCreatebooking.setPercentage(Double.parseDouble(strpercentinpoint));
        queryCreatebooking.setBookingAmount(Double.parseDouble(strbookingamtasparam));
        queryCreatebooking.setPendingAmount(Double.parseDouble(strpendingamtasparam));
        queryCreatebooking.setDeliveryContactPerson(strdelcontactperosn);
        queryCreatebooking.setDeliveryContactPerson(strmobileno);
        queryCreatebooking.setPackagingDetails(strpackagingdetails);
        queryCreatebooking.setCartData(queryCreatebookingCartData);




// RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetCreateBooking> loginCall = apiService.wsCreateBooking(
                "123456", queryCreatebooking);
        loginCall.enqueue(new Callback<GetCreateBooking>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateBooking> call,
                                   Response<GetCreateBooking> response) {


                if (response.isSuccessful()) {

 bookingid= response.body().getData().getId();


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
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetBookingDeatils> loginCall = apiService.wsGetBookingDeatils("123456",body);
        loginCall.enqueue(new Callback<GetBookingDeatils>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetBookingDeatils> call,
                                   Response<GetBookingDeatils> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){

                        double  bookingamout = response.body().getData().get(0).getBookingAmount();
                        String userid = response.body().getData().get(0).getUserID();

                        callCreateOder(bookingamout,userid,bookingid);
                    }else{

                    }

                }
                else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetBookingDeatils> call,
                                  Throwable t) {
                dialog.dismiss();

            }
        });
    }

    private void callCreateOder(double bookingamout, String userid,String bookingid) {

        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("Userid",userid);
        jsonParams.put("amount",bookingamout);
        jsonParams.put("Order_ID",bookingid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetBookOrder> loginCall = apiService.wsBookOrder("123456",body);
        loginCall.enqueue(new Callback<GetBookOrder>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetBookOrder> call,
                                   Response<GetBookOrder> response) {


                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){

String razorpay_id = response.body().getData().getRazorpayOrderid();
double amount = response.body().getData().getAmount();
String key = response.body().getData().getKey();
boolean initialpayment = response.body().getData().getInitiatePayment();

                        ((HomePageActivity)getActivity()).startpayment(razorpay_id,amount,key,bookingid,isbooking);
                    }else{
                        makeToast(getActivity(),getResources().getString(R.string.data_not_found));
                    }

                }
                else {

                    makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetBookOrder> call,
                                  Throwable t) {
                makeToast(getActivity(),getResources().getString(R.string.something_went_wrong));            }
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