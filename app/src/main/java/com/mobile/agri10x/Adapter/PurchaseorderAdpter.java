package com.mobile.agri10x.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetCreateCheckoutCartProductCheckout;
import com.mobile.agri10x.models.GetCreateCheckoutDetails;
import com.mobile.agri10x.models.GetOrderListDatumCheckout;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

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

public class PurchaseorderAdpter extends RecyclerView.Adapter<PurchaseorderAdpter.ViewHolers> {
Date purchasedate;
    Context context;
    String billingid="",shippingid="",ordernotes="",billingaddressstr="",shippingaddressstr="",purchasedatestr="";
    List<GetOrderListDatumCheckout> ProductsInPurchaseorderlist = new ArrayList<>();


    ArrayList<getAddressData> getAddressDataArrayList = new ArrayList<>();
    Dialog purchasedetaildialog;
    ImageView cancle_btn;
    TextView txt_delivery_notes, txt_shipping_address, txt_billing_address, txt_product_name,
            txt_product_price, txt_grade, txt_price_per_kg, txt_quantity, txt_packaging_size, txt_total_weight, txt_total_amount;

    public PurchaseorderAdpter(List<GetOrderListDatumCheckout> productsInOrderlist, Context context) {
        this.context = context;
        this.ProductsInPurchaseorderlist = productsInOrderlist;
        this.billingid = billingid;
        this.shippingid = shippingid;
        this.ordernotes = ordernotes;

    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_order_list_layout, parent, false);
        ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {
        String billingid =  ProductsInPurchaseorderlist.get(0).getBillingAddressID();
        String shippingid = ProductsInPurchaseorderlist.get(0).getShippingAddressID();
    ordernotes =  ProductsInPurchaseorderlist.get(0).getOrderNotes();
        callapigetAddress(billingid,shippingid);

        if(ProductsInPurchaseorderlist.get(position).getPaymentDate() != null){
            String orderdate = ProductsInPurchaseorderlist.get(position).getPaymentDate();
            String[] separated = orderdate.split("T");
            String fromdate=separated[0];
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                purchasedate = format1.parse(fromdate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            purchasedatestr = dateFormat.format(purchasedate);
        }



        holder.txt_booking_id.setText("Booking ID : " + ProductsInPurchaseorderlist.get(position).getId());
        holder.txt_order_date.setText("Order Date : " + purchasedatestr);


        double number = Double.parseDouble(String.valueOf(ProductsInPurchaseorderlist.get(position).getOrderAmount()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency = format.format(number);
        //   System.out.println("Currency in INDIA : " + currency);
        // totalamt.setText(currency);


        holder.txt_price.setText(currency);

        holder.layout_for_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderid= ProductsInPurchaseorderlist.get(position).getId();

                //   callcreatecheckoutdeatils(orderid);

                purchasedetaildialog = new Dialog(context);
                purchasedetaildialog.setContentView(R.layout.layout_detailof_purchaseorder);
                purchasedetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                purchasedetaildialog.setCancelable(true);
                purchasedetaildialog.setCanceledOnTouchOutside(true);
                purchasedetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                cancle_btn = purchasedetaildialog.findViewById(R.id.cancle_btn);

                txt_product_name = purchasedetaildialog.findViewById(R.id.txt_product_name);
                txt_product_price = purchasedetaildialog.findViewById(R.id.txt_product_price);
                txt_grade = purchasedetaildialog.findViewById(R.id.txt_grade);
                txt_price_per_kg = purchasedetaildialog.findViewById(R.id.txt_price_per_kg);
                txt_quantity = purchasedetaildialog.findViewById(R.id.txt_quantity);
                txt_packaging_size = purchasedetaildialog.findViewById(R.id.txt_packaging_size);
                txt_total_weight = purchasedetaildialog.findViewById(R.id.txt_total_weight);
                txt_total_amount = purchasedetaildialog.findViewById(R.id.txt_total_amount);
                txt_billing_address = purchasedetaildialog.findViewById(R.id.txt_billing_address);
                txt_shipping_address = purchasedetaildialog.findViewById(R.id.txt_shipping_address);
                txt_delivery_notes = purchasedetaildialog.findViewById(R.id.txt_delivery_notes);
                txt_product_name.setText(ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getName());

                double number = Double.parseDouble(String.valueOf(ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getPrice()));
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String currency = format.format(number);

                txt_product_price.setText(currency);
                txt_price_per_kg.setText("Price/KG : "+currency);
                txt_quantity.setText("Qunatity : "+(ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getQuantity()));

                txt_grade.setText("Grade : "+ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getGrade());
                txt_packaging_size.setText("Packaging Size : ");

                String test = String.valueOf(ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getQuantity());

                txt_total_weight.setText("Total Weight : "+Double.parseDouble(test) * ProductsInPurchaseorderlist.get(0).getCartData().getProducts().get(0).getWeight());
                //  txt_quantity.setText((int) ProductsInPurchaseorderlist.get(position).getCartData().getProducts().get(position).getQuantity());


                double number1 = Double.parseDouble(String.valueOf(ProductsInPurchaseorderlist.get(0).getCartData().getSubTotal()));
                NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String currency1 = format1.format(number1);
                txt_total_amount.setText(currency1);


                txt_billing_address.setText(billingaddressstr);
                txt_shipping_address.setText(shippingaddressstr);
                if(ordernotes.equals("")){
                    txt_delivery_notes.setText("Standard Delivery Schedule");
                }else{
                    txt_delivery_notes.setText(ordernotes);
                }


                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        purchasedetaildialog.dismiss();
                    }
                });

                purchasedetaildialog.show();




            }
        });


    }

    private void callapigetAddress(String billingid, String shippingid) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<getAddress> addressCall = apiService.wsGetAddress("123456", body);
        addressCall.enqueue(new Callback<getAddress>() {
            @Override
            public void onResponse(Call<getAddress> call, Response<getAddress> response) {
                Log.d("getapiaddress", response.toString());


                if (response.isSuccessful()){
                    getAddressDataArrayList.addAll(response.body().getData());

                    if(getAddressDataArrayList.size() > 0){

                        for(int i= 0 ; i < getAddressDataArrayList.size();i++)
                        {
                           String getbillingaddressid = getAddressDataArrayList.get(i).getId();
                           String getdeladdressid = getAddressDataArrayList.get(i).getId();

                           if(getbillingaddressid.equals(billingid)){
                               billingaddressstr = getAddressDataArrayList.get(i).getAddressLine1()+" "+
                                       getAddressDataArrayList.get(i).getAddressLine2()+", "+
                                       getAddressDataArrayList.get(i).getCity()+", "+
                                       getAddressDataArrayList.get(i).getState();
                           }
                            if(getdeladdressid.equals(shippingid)){
                                shippingaddressstr = getAddressDataArrayList.get(i).getAddressLine1()+" "+
                                        getAddressDataArrayList.get(i).getAddressLine2()+", "+
                                        getAddressDataArrayList.get(i).getCity()+", "+
                                        getAddressDataArrayList.get(i).getState();
                            }

                        }

                    }else{

                    }


                }else{
                 //   Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddress> call, Throwable t) {

                //Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void callcreatecheckoutdeatils(String orderid) {

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("checkoutID",orderid);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();

        final Call<GetCreateCheckoutDetails> loginCall = apiService.wsGetCheckoutDeatils("123456",body);
        loginCall.enqueue(new Callback<GetCreateCheckoutDetails>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetCreateCheckoutDetails> call,
                                   Response<GetCreateCheckoutDetails> response) {

                Log.d("bookdeatils",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Success")){






                    }else{

                    }

                }
                else {

                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCreateCheckoutDetails> call,
                                  Throwable t) {
                Toast.makeText(context,"Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductsInPurchaseorderlist.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        LinearLayout layout_for_purchase;
        TextView  txt_order_date, txt_booking_id;

        TextView txt_price;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            layout_for_purchase = itemView.findViewById(R.id.layout_for_purchase);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_order_date = itemView.findViewById(R.id.txt_order_date);
            txt_booking_id = itemView.findViewById(R.id.txt_booking_id);


        }
    }
}
