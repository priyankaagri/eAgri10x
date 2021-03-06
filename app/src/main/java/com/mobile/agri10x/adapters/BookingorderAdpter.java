package com.mobile.agri10x.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.fragments.MyOrderFragment;
import com.mobile.agri10x.fragments.PurchaseOrderFargment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetBookingDeatilCartData;
import com.mobile.agri10x.models.GetBookingDeatilProduct;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetBookingDeatilsDatum;
import com.mobile.agri10x.models.GetBookingListData;
import com.mobile.agri10x.models.GetOrderListDatumBooking;
import com.mobile.agri10x.models.GetOrderListDatumBookingCartDataProduct;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.agri10x.utils.ToastMessages.makeToast;

public class BookingorderAdpter extends RecyclerView.Adapter<BookingorderAdpter.ViewHolers> {
    Date purchasedate,currnetDate,NewDate;
    public  int dayDifference=0;
    String fromdate;
    Context context;
    String  ordernotes = "", billingaddressstr = "", shippingaddressstr = "", purchasedatestr = "";
    List<GetBookingListData> productsInbookingorderlist = new ArrayList<>();

    List<GetBookingDeatilsDatum> productslistData = new ArrayList<>();
    List<GetBookingDeatilProduct>bookingDeatilProductlist =new ArrayList<>();

    ArrayList<getAddressData> getAddressDataArrayList = new ArrayList<>();
    Dialog bookingdetaildialog;
    ImageView cancle_btn;
    Button btn_purchase;

    RecyclerView productlistdata;
    LinearLayoutManager linearLayoutManager;
    TextView txt_delivery_notes, txt_shipping_address, txt_billing_address, txt_product_name,
            txt_product_price, txt_grade, txt_price_per_kg, txt_quantity, txt_packaging_size, txt_total_weight, txt_total_amount,txt_pending_amount,txt_paid_amount;

    public BookingorderAdpter(List<GetBookingListData> bookingorderlist, Context context) {
        this.context = context;
        this.productsInbookingorderlist = bookingorderlist;

    }


    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_order_list_layout, parent, false);
        ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {

        if (productsInbookingorderlist.get(position).getPaymentDate() != null) {

//            fromdate = String.valueOf(productsInbookingorderlist.get(position).getPaymentDate());
//            // fromdate = "2021-04-03T17:26:42.266Z";
//
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            try {
//                purchasedate = format1.parse(fromdate);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            purchasedatestr = dateFormat.format(purchasedate);
//            holder.txt_order_date.setText("Order Date : " + purchasedatestr.substring(0,10));
//
//
//            String crrentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//            Log.d("cccccdate",crrentdate);
//            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                currnetDate = format0.parse(crrentdate);
//                System.out.println(currnetDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(purchasedate);
//            calendar.add(Calendar.DAY_OF_YEAR, 3);
//            DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String newDate=dateFormat3.format(calendar.getTime());
//            Log.d("newDate",newDate);
//            SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                NewDate = formatt.parse(newDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            long difference = (NewDate.getTime() - currnetDate.getTime());
//            long differenceDates = difference / (24 * 60 * 60 * 1000);
//            long seconds = difference / 1000;
//            long minutes = seconds / 60;
//            long hours = minutes / 60;
//            long days = hours / 24;
//            dayDifference = (int) differenceDates;
//            Log.d("dayDifference", String.valueOf(dayDifference));
//            if (dayDifference < 0) {
//                holder.relative_stock.setVisibility(View.GONE);
//                holder.red_background.setBackgroundResource(R.drawable.featured_red_trn_change);
//
//            } else if (dayDifference == 0) {
//                if (hours > 0) {
//                    holder.relative_stock.setVisibility(View.VISIBLE);
//                    holder.red_background.setBackgroundResource(R.drawable.featured_red_trn);
//                    holder.txt_numberofdays.setText("" +hours+" hrs");
//                } else {
//                    holder.relative_stock.setVisibility(View.GONE);
//                    holder.red_background.setBackgroundResource(R.drawable.featured_red_trn_change);
//                }
//
//            } else {
//                holder.relative_stock.setVisibility(View.VISIBLE);
//                holder.red_background.setBackgroundResource(R.drawable.featured_red_trn);
//                holder.txt_numberofdays.setText("" +dayDifference+" days");
//            }
        } else {

            holder.txt_order_date.setText("Payment Not Completed");
        }
        if(productsInbookingorderlist.get(position).getDeadline()!=null)
        {
            holder.red_background.setBackgroundResource(R.drawable.featured_red_trn);
            holder.relative_stock.setVisibility(View.VISIBLE);
            String s = productsInbookingorderlist.get(position).getDeadline();
            String[] arrayString = s.split("T");

            String deadline = arrayString[0];
            holder.txt_numberofdays.setText(deadline);
        }else {
            holder.red_background.setBackgroundResource(R.drawable.featured_red_trn_change);
            holder.relative_stock.setVisibility(View.GONE);
        }
        holder.txt_booking_id.setText("Booking ID: " + productsInbookingorderlist.get(position).getBookingID());

        double number = Double.parseDouble(String.valueOf(productsInbookingorderlist.get(position).getPendingAmount()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency = format.format(number);
        holder.txt_price.setText(currency);

        holder.layout_for_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String bookingId=productsInbookingorderlist.get(position).getBookingID();
                Log.d("bookingId",bookingId);
                callapiforBookingDetail(bookingId);
            }

        });

    }

    private void callapiforBookingDetail(String bookingId) {
      AlertDialog  dialog=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("bookingID",bookingId);
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

                        productslistData.clear();
                        bookingdetaildialog = new Dialog(context);
                        bookingdetaildialog.setContentView(R.layout.layout_detailof_bookingorder);
                        bookingdetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        bookingdetaildialog.setCancelable(true);
                        bookingdetaildialog.setCanceledOnTouchOutside(true);
                        bookingdetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                        cancle_btn = bookingdetaildialog.findViewById(R.id.cancle_btn);
                        btn_purchase = bookingdetaildialog.findViewById(R.id.btn_purchase);


                        productslistData.addAll(response.body().getData());
                        bookingDeatilProductlist.addAll(response.body().getData().get(0).getCartData().getProducts());
                        DetailofProductOrder_booking adapter = new DetailofProductOrder_booking(bookingDeatilProductlist, context);
                        linearLayoutManager = new LinearLayoutManager(context);
                        productlistdata = bookingdetaildialog.findViewById(R.id.productlistdata_recycleview);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        productlistdata.setLayoutManager(linearLayoutManager);
                        productlistdata.setAdapter(adapter);

                        txt_product_name = bookingdetaildialog.findViewById(R.id.txt_product_name);
                        txt_product_price = bookingdetaildialog.findViewById(R.id.txt_product_price);
                        txt_grade = bookingdetaildialog.findViewById(R.id.txt_grade);
                        txt_paid_amount = bookingdetaildialog.findViewById(R.id.txt_paid_amount);
                        txt_pending_amount = bookingdetaildialog.findViewById(R.id.txt_pending_amount);
                        txt_price_per_kg = bookingdetaildialog.findViewById(R.id.txt_price_per_kg);
                        txt_quantity = bookingdetaildialog.findViewById(R.id.txt_quantity);
                        txt_packaging_size = bookingdetaildialog.findViewById(R.id.txt_packaging_size);
                        txt_total_weight = bookingdetaildialog.findViewById(R.id.txt_total_weight);
                        txt_total_amount = bookingdetaildialog.findViewById(R.id.txt_total_amount);
                        txt_billing_address = bookingdetaildialog.findViewById(R.id.txt_billing_address);
                        txt_shipping_address = bookingdetaildialog.findViewById(R.id.txt_shipping_address);
                        txt_delivery_notes = bookingdetaildialog.findViewById(R.id.txt_delivery_notes);


                        double number1 = Double.parseDouble(String.valueOf(productslistData.get(0).getBookingAmount()));
                        NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                        String currency1 = format1.format(number1);

                        double number2 = Double.parseDouble(String.valueOf(productslistData.get(0).getPendingAmount()));
                        NumberFormat format2 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                        String currency2 = format2.format(number2);

                        txt_total_amount.setText(currency2);
                        txt_pending_amount.setText(currency2);
                        txt_paid_amount.setText(currency1);

                        String billingid = productslistData.get(0).getBillingAddressID();
                        String shippingid = productslistData.get(0).getShippingAddressID();
                        ordernotes = productslistData.get(0).getOrderNotes();

                        callapigetAddress(billingid, shippingid);

                        if (ordernotes.equals("")) {
                            txt_delivery_notes.setText("Standard Delivery Schedule");
                        } else {
                            txt_delivery_notes.setText(ordernotes);
                        }

/* txt_pending_amount = bookingdetaildialog.findViewById(R.id.txt_pending_amount);
txt_paid_amount = bookingdetaildialog.findViewById(R.id.txt_paid_amount);
btn_purchase = bookingdetaildialog.findViewById(R.id.btn_purchase);
*/
                        cancle_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookingdetaildialog.dismiss();
                            }
                        });
                        btn_purchase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                PurchaseOrderFargment fragment = new PurchaseOrderFargment(); // replace your custom fragment class
                                Bundle bundle = new Bundle();
                                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                                bundle.putString("value", String.valueOf(productslistData.get(0).getPendingAmount())); // use as per your need
                                fragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                bookingdetaildialog.dismiss();
                            }
                        });
                        bookingdetaildialog.show();

                    }else{

                    }

                }
                else {
dialog.dismiss();
                    makeToast(context,context.getResources().getString(R.string.something_went_wrong));                }
            }

            @Override
            public void onFailure(Call<GetBookingDeatils> call,
                                  Throwable t) {
                   dialog.dismiss();
                makeToast(context,context.getResources().getString(R.string.slownetworkdeted));
            }
        });
    }


    private void callapigetAddress(String billingid, String shippingid) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<getAddress> addressCall = apiService.wsGetAddress("123456", body);
        addressCall.enqueue(new Callback<getAddress>() {
            @Override
            public void onResponse(Call<getAddress> call, Response<getAddress> response) {
                Log.d("getapiaddress", response.toString());


                if (response.isSuccessful()) {
                    getAddressDataArrayList.addAll(response.body().getData());

                    if (getAddressDataArrayList.size() > 0) {

                        for (int i = 0; i < getAddressDataArrayList.size(); i++) {
                            String getbillingaddressid = getAddressDataArrayList.get(i).getId();
                            String getdeladdressid = getAddressDataArrayList.get(i).getId();

                            if (getbillingaddressid.equals(billingid)) {
                                billingaddressstr = getAddressDataArrayList.get(i).getAddressLine1() + " " +
                                        getAddressDataArrayList.get(i).getAddressLine2() + ", " +
                                        getAddressDataArrayList.get(i).getCity() + ", " +
                                        getAddressDataArrayList.get(i).getState();
                                txt_billing_address.setText(billingaddressstr);
                            }
                            if (getdeladdressid.equals(shippingid)) {
                                shippingaddressstr = getAddressDataArrayList.get(i).getAddressLine1() + " " +
                                        getAddressDataArrayList.get(i).getAddressLine2() + ", " +
                                        getAddressDataArrayList.get(i).getCity() + ", " +
                                        getAddressDataArrayList.get(i).getState();

                                txt_shipping_address.setText(shippingaddressstr);
                            }

                        }

                    } else {

                    }


                } else {
                    //   Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddress> call, Throwable t) {

                //Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return productsInbookingorderlist.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        LinearLayout layout_for_booking;
        TextView txt_order_date, txt_booking_id,txt_numberofdays;
        RelativeLayout relative_stock;
        TextView txt_price,red_background;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            layout_for_booking = itemView.findViewById(R.id.layout_for_booking);
            txt_price = itemView.findViewById(R.id.txt_price);
            red_background = itemView.findViewById(R.id.red_background);
            txt_order_date = itemView.findViewById(R.id.txt_order_date);
            txt_booking_id = itemView.findViewById(R.id.txt_booking_id);
            txt_numberofdays = itemView.findViewById(R.id.txt_numberofdays);
            relative_stock = itemView.findViewById(R.id.relative_stock);


        }
    }

    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(context);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }


    }
}
