package com.mobile.agri10x.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.PurchaseOrderFargment;
import com.mobile.agri10x.R;
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

public class BookingorderAdpter extends RecyclerView.Adapter<BookingorderAdpter.ViewHolers> {
    Date purchasedate;
    Context context;
    String  ordernotes = "", billingaddressstr = "", shippingaddressstr = "", purchasedatestr = "";
    List<GetOrderListDatumBooking> productsInbookingorderlist = new ArrayList<>();
    List<GetOrderListDatumBookingCartDataProduct> productslistData = new ArrayList<>();


    ArrayList<getAddressData> getAddressDataArrayList = new ArrayList<>();
    Dialog bookingdetaildialog;
    ImageView cancle_btn;
    Button btn_purchase;

    RecyclerView productlistdata;
    LinearLayoutManager linearLayoutManager;
    TextView txt_delivery_notes, txt_shipping_address, txt_billing_address, txt_product_name,
            txt_product_price, txt_grade, txt_price_per_kg, txt_quantity, txt_packaging_size, txt_total_weight, txt_total_amount,txt_pending_amount,txt_paid_amount;

    public BookingorderAdpter(List<GetOrderListDatumBooking> bookingorderlist, Context context) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {



        if (productsInbookingorderlist.get(position).getCompletePaymentDate() != null) {
            String orderdate = (String) productsInbookingorderlist.get(position).getCompletePaymentDate();
            String[] separated = orderdate.split("T");
            String fromdate = separated[0];
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                purchasedate = format1.parse(fromdate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            purchasedatestr = dateFormat.format(purchasedate);
            holder.txt_order_date.setText("Order Date : " + purchasedatestr);
        }else{
            holder.txt_order_date.setText("Order Date : ");
        }


        holder.txt_booking_id.setText("Booking ID: " + productsInbookingorderlist.get(position).getId());



        double number = Double.parseDouble(String.valueOf(productsInbookingorderlist.get(position).getBookingAmount()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency = format.format(number);
        holder.txt_price.setText(currency);


        holder.layout_for_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productslistData.clear();
                bookingdetaildialog = new Dialog(context);
                bookingdetaildialog.setContentView(R.layout.layout_detailof_bookingorder);
                bookingdetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bookingdetaildialog.setCancelable(true);
                bookingdetaildialog.setCanceledOnTouchOutside(true);
                bookingdetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;


                cancle_btn = bookingdetaildialog.findViewById(R.id.cancle_btn);
                btn_purchase = bookingdetaildialog.findViewById(R.id.btn_purchase);


                productslistData.addAll(productsInbookingorderlist.get(position).getCartData().getProducts());

                DetailofProductOrder_booking adapter = new DetailofProductOrder_booking(productslistData, context);
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


                double number1 = Double.parseDouble(String.valueOf(productsInbookingorderlist.get(position).getBookingAmount()));
                NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String currency1 = format1.format(number1);

                double number2 = Double.parseDouble(String.valueOf(productsInbookingorderlist.get(position).getPendingAmount()));
                NumberFormat format2 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String currency2 = format2.format(number2);

                txt_total_amount.setText(currency2);
                txt_pending_amount.setText(currency2);
                txt_paid_amount.setText(currency1);

                String billingid = productsInbookingorderlist.get(position).getBillingAddressID();
                String shippingid = productsInbookingorderlist.get(position).getShippingAddressID();
                ordernotes = productsInbookingorderlist.get(position).getOrderNotes();

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
                        bundle.putString("value", String.valueOf(productsInbookingorderlist.get(position).getPendingAmount())); // use as per your need
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        bookingdetaildialog.dismiss();
                    }
                });
                bookingdetaildialog.show();

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
        TextView txt_order_date, txt_booking_id;

        TextView txt_price;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            layout_for_booking = itemView.findViewById(R.id.layout_for_booking);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_order_date = itemView.findViewById(R.id.txt_order_date);
            txt_booking_id = itemView.findViewById(R.id.txt_booking_id);


        }
    }
}
