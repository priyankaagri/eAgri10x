package com.mobile.agri10x.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.DisplayQuickView;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyDealsFeaturedAdapter extends RecyclerView.Adapter<DailyDealsFeaturedAdapter.ViewHolders> {
    Context context;
    private List<GetHomeProductData> dataList;
    boolean check;



    public DailyDealsFeaturedAdapter(List<GetHomeProductData> featuredproductlist, Context context, boolean check) {
        this.dataList=featuredproductlist;
        this.context=context;
        this.check =check;
    }


    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_deals_featured_adapter, parent, false);
        ViewHolders viewHolder = new ViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {

        if(check){
            holder.fav.setVisibility(View.VISIBLE);
        }else{
            holder.fav.setVisibility(View.GONE);
        }

        String strimg =  dataList.get(position).getCommodityID()+".png";



        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("exception", String.valueOf(exception));
                    }
                })
                .build();
        picasso.load("https://data.agri10x.com/images/products/"+strimg)
                .fit()
                .into(holder.product_img);
        holder.variety.setText(dataList.get(position).getVarietyName());
        holder.city.setText(dataList.get(position).getCity());
        holder.grade.setText("Grade "+dataList.get(position).getGrade());
        holder.txt_product_name.setText(dataList.get(position).getCommodityName());
        holder.product_price.setText("Rs "+dataList.get(position).getPricePerLot());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SessionManager.isLoggedIn(context)){
                    String str_orderId=dataList.get(position).getOrderID();
                    String str_grade = dataList.get(position).getGrade();
                    callApiProductDetail(str_orderId,position,str_grade);
                }else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });


    }

    private void callApiProductDetail(String str_orderId, int position, String str_grade) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("orderID",str_orderId);
        jsonParams.put("grade",str_grade);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        //AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<DisplayQuickView> loginCall = apiService.wsgetdisplayQuickView("123456",
                body);
        loginCall.enqueue(new Callback<DisplayQuickView>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<DisplayQuickView> call,
                                   Response<DisplayQuickView> response) {

// Log.d("verifyOTP",response.toString());
                if (response.isSuccessful()) {
                    Dialog dialog;
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.quickviewdialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    TextView add_btn= dialog.findViewById(R.id.addcart);
                    ImageView shareiamge= dialog.findViewById(R.id.shareiamge);
                    shareiamge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Clicked Share Button!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    add_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Clicked Add Button!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    TextView comodity_txt = dialog.findViewById(R.id.comodity_txt);
                    comodity_txt.setText(response.body().getData().get(position).getCommodityName());

                    TextView location_txt = dialog.findViewById(R.id.location_txt);
                    location_txt.setText(response.body().getData().get(position).getCity()+" , "+response.body().getData().get(position).getState());

                    TextView packaging_txt = dialog.findViewById(R.id.packaging_txt);
                    packaging_txt.setText("Packaging Size : "+" "+response.body().getData().get(position).getLotSize()+" kg");

                    TextView avilablequantity_txt = dialog.findViewById(R.id.avilablequantity_txt);
                    avilablequantity_txt.setText("Avilable Quantity :"+" "+response.body().getData().get(position).getWeight()+" kg");

                    TextView price_txt = dialog.findViewById(R.id.price_txt);
                    price_txt.setText("Price/KG: "+""+"₹ "+response.body().getData().get(position).getPricePerLot());

                    EditText entervalue = dialog.findViewById(R.id.entervalue);


                    dialog.show();
                }
                else {

                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DisplayQuickView> call,
                                  Throwable t) {

                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView txt_product_name,product_price,variety,grade,city;
        ImageView product_img,fav;
        TextView addcart;
        CardView cardview;


        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            this.txt_product_name =itemView.findViewById(R.id.txt_product_name);
            this.product_price =itemView.findViewById(R.id.product_price);
            this.product_img =itemView.findViewById(R.id.product_img);
            this.addcart =itemView.findViewById(R.id.addcart);
            this.cardview =itemView.findViewById(R.id.cardview);
            this.fav = itemView.findViewById(R.id.fav);
            this.grade = itemView.findViewById(R.id.grade);
            this.variety = itemView.findViewById(R.id.brand);
            this.city = itemView.findViewById(R.id.city);
        }
    }
}
