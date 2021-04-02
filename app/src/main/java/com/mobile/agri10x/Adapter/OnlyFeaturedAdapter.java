package com.mobile.agri10x.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.TradeValueAddCart;
import com.mobile.agri10x.Fragments.YourWishListFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.DisplayQuickView;
import com.mobile.agri10x.models.GetADDWishlist;
import com.mobile.agri10x.models.GetAddProductToCart;
import com.mobile.agri10x.models.GetHomeProductData;
import com.mobile.agri10x.models.GetProductInWishList;
import com.mobile.agri10x.models.GetProductInWishListData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlyFeaturedAdapter extends RecyclerView.Adapter<OnlyFeaturedAdapter.ViewHolders> {
    Context context;
    private List<GetHomeProductData> dataList;
    AlertDialog dialog;
    ArrayList<GetProductInWishListData> arrayListwishlist=new ArrayList<>();
    boolean present= false;
    public OnlyFeaturedAdapter(List<GetHomeProductData> featuredproductlist, Context context) {
        this.dataList=featuredproductlist;
        this.context=context;
    }


    @NonNull
    @Override
    public OnlyFeaturedAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_featured_adapter, parent, false);
        OnlyFeaturedAdapter.ViewHolders viewHolder = new OnlyFeaturedAdapter.ViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnlyFeaturedAdapter.ViewHolders holder, int position) {



        String strimg =  dataList.get(position).getCommodityID()+".png";

        boolean featuredvalue = dataList.get(position).getFeatured();

        if(featuredvalue){
            holder.featured_value.setVisibility(View.VISIBLE);
        }else{
            holder.featured_value.setVisibility(View.GONE);
        }


        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                    }
                })
                .build();
        picasso.load("https://data.agri10x.com/images/products/"+strimg)
                .fit()
                .into(holder.product_img);

        holder.txt_product_name.setText(dataList.get(position).getCommodityName());

        double number1 = dataList.get(position).getPricePerLot();
        NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency1 = format1.format(number1);
        System.out.println("Currency in INDIA : " + currency1);
        String pricepeoduct = String.format("%.2f", dataList.get(position).getPricePerLot());
        holder.product_price.setText("Price/KG : "+currency1);

        holder.txt_varity.setText(dataList.get(position).getVarietyName());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_orderId=dataList.get(position).getOrderID();
                String str_grade = dataList.get(position).getGrade();
                Double str_price = dataList.get(position).getPricePerLot();
                String str_commodityname = dataList.get(position).getCommodityName();
                String str_variety = dataList.get(position).getVarietyName();

                callApiProductDetail(str_orderId,position,str_grade,str_price,str_commodityname,str_variety);

            }
        });


    }

    private void callApiProductDetail(String str_orderId, int position, String str_grade, Double str_price,String str_commodityname,String str_variety_name) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("orderID",str_orderId);
        jsonParams.put("grade",str_grade);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
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
                    ImageView productimage = dialog.findViewById(R.id.productimage);
                    EditText entervalue = dialog.findViewById(R.id.entervalue);
                    TextView comodity_txt = dialog.findViewById(R.id.comodity_txt);
                    TextView location_txt = dialog.findViewById(R.id.location_txt);
                    TextView packaging_txt = dialog.findViewById(R.id.packaging_txt);
                    TextView avilablequantity_txt = dialog.findViewById(R.id.avilablequantity_txt);
                    TextView price_txt = dialog.findViewById(R.id.price_txt);
                    TextView variety= dialog.findViewById(R.id.variety);
                    TextView grade= dialog.findViewById(R.id.grade);
                    ImageView close_dialog = dialog.findViewById(R.id.close_dialog);

                    ImageView img_add_wishlist = dialog.findViewById(R.id.img_add_wishlist);
                    img_add_wishlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            callapiofprductsinwishlist(str_orderId,str_grade,str_price,str_commodityname,str_variety_name);

                        }
                    });

                    close_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    String strimgdetail =  response.body().getData().get(0).getCommodityID()+".png";
                    Picasso picasso = new Picasso.Builder(context)
                            .listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    Log.d("exception", String.valueOf(exception));
                                }
                            })
                            .build();
                    picasso.load("https://data.agri10x.com/images/products/"+strimgdetail)
                            .fit()
                            .into(productimage);
                    shareiamge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Clicked Share Button!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    add_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str_enterValue= entervalue.getText().toString();

                            if(TextUtils.isEmpty(str_enterValue)){
                                Toast.makeText(context, "Please quote price", Toast.LENGTH_SHORT).show();
                            }else {
                                if(SessionManager.isLoggedIn(context)){
                                    int int_enterValue= Integer.parseInt(entervalue.getText().toString());
                                    if(int_enterValue%50==0){

                                        if(int_enterValue>=500){
                                            if (response.body().getData().get(0).getWeight()>=int_enterValue){
                                                dialog.dismiss();
                                                String quantity= String.valueOf(int_enterValue/50);
                                                CallApiaddTOCard(response.body().getData().get(0).getOrderID(),response.body().getData().get(0).getGrade(),quantity,response.body().getData().get(0).getCommodityName(),response.body().getData().get(0).getPricePerLot());
                                            }else {
                                                Toast.makeText(context, "Stock is not available for this product.", Toast.LENGTH_SHORT).show();
                                            }

                                        }else {
                                            Toast.makeText(context, "Minimum Trade Quantity Required is 500 KG", Toast.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        Toast.makeText(context, "Quantity should be in multiples of 50kg only", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    context.startActivity(new Intent(context,LoginActivity.class));
                                }
                            }

                        }
                    });

                    comodity_txt.setText(response.body().getData().get(0).getCommodityName());


                    location_txt.setText(response.body().getData().get(0).getCity()+" , "+response.body().getData().get(0).getState());


                    packaging_txt.setText("Packaging Size : "+" "+response.body().getData().get(0).getLotSize()+" kg");


                    avilablequantity_txt.setText("Available Quantity :"+" "+response.body().getData().get(0).getLotSize()*response.body().getData().get(0).getTotalAvailable()+" kg");

                    double number = response.body().getData().get(0).getPricePerLot();
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                    String currency = format.format(number);
                    System.out.println("Currency in INDIA : " + currency);
                    String pricepeoduct = String.format("%.2f", response.body().getData().get(0).getPricePerLot());
                    price_txt.setText("Price/KG: "+currency);


                    variety.setText(response.body().getData().get(0).getVarietyName());

                    grade.setText("Grade "+response.body().getData().get(0).getGrade());


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
    private void callapiofprductsinwishlist(String str_orderId, String str_grade, Double str_price,String str_commodtyname,String str_varietyname) {

        arrayListwishlist.clear();
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
//AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetProductInWishList> loginCall = apiService.wsGetProductInWhishlist("123456",
                body);
        loginCall.enqueue(new Callback<GetProductInWishList>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetProductInWishList> call,
                                   Response<GetProductInWishList> response) {

                Log.d("resWishlist", response.toString());

                if (response.isSuccessful()) {
                    arrayListwishlist.addAll(response.body().getData());

                    Log.d("getsizewishlist", String.valueOf(arrayListwishlist.size()));
                    if (arrayListwishlist.size() > 0) {

                        for(int i=0; i  < arrayListwishlist.size();i++) {
                            String getorderid_from_wishlist = arrayListwishlist.get(i).getOrderID();
                            String grade_from_wishlist = arrayListwishlist.get(i).getGrade();
                            String commodityname_fromwishlist = arrayListwishlist.get(i).getName();
                            String variety_fromwishlist = arrayListwishlist.get(i).getVariety();
                            double price_fromwishlist = arrayListwishlist.get(i).getPrice();
                            Log.d("checklist", commodityname_fromwishlist + " " + str_commodtyname + " " + grade_from_wishlist + " " + str_grade+" "+variety_fromwishlist+" "+str_varietyname);

                            if (grade_from_wishlist.equals(str_grade) && commodityname_fromwishlist.equals(str_commodtyname) && variety_fromwishlist.equals(str_varietyname) && price_fromwishlist == str_price) {
                                present = true;

                            }
                        }
                        if(present){
                            Toast.makeText(context,"Already in your wishlist",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            callapiAddtoWishlist(str_orderId,str_grade,str_price);
                        }



                    }else{
                        callapiAddtoWishlist(str_orderId,str_grade,str_price);
                    }


                } else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProductInWishList> call,
                                  Throwable t) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callapiAddtoWishlist(String str_orderId, String str_grade, double str_price) {
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        jsonParams.put("m_OrderID",str_orderId);
        jsonParams.put("price",str_price );
        jsonParams.put("grade",str_grade );
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
//AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetADDWishlist> loginCall = apiService.wsAddWishlist("123456",
                body);
        loginCall.enqueue(new Callback<GetADDWishlist>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetADDWishlist> call,
                                   Response<GetADDWishlist> response) {

                Log.d("rewishlist", response.toString());

                if (response.isSuccessful()) {


                    HomePageActivity.setFragment(new YourWishListFragment(),"wishlist");


                } else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetADDWishlist> call,
                                  Throwable t) {

                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallApiaddTOCard(String orderID,String grade,String quantity,String commodityname,double price) {
        dialog=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        jsonParams.put("m_orderID",orderID);

        jsonParams.put("quantity",quantity);
        jsonParams.put("grade",grade);
        jsonParams.put("price",price);
        jsonParams.put("status","Just added to cart!");
        Log.d("userID", SessionManager.getKeyTokenUser(context)+" "+orderID+" "+quantity+" "+grade);
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
        final Call<GetAddProductToCart> loginCall = apiService.wsGetAddproducttocart("123456",body);
        loginCall.enqueue(new Callback<GetAddProductToCart>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetAddProductToCart> call,
                                   Response<GetAddProductToCart> response) {
                dialog.dismiss();
                Log.d("addtocart",response.toString());
                if (response.isSuccessful()) {
                    HomePageActivity.getProductinCart();
                    Toast.makeText(context, quantity+" Kg of "+ commodityname +" has been added to trade", Toast.LENGTH_LONG).show();
                    HomePageActivity.setFragment(new TradeValueAddCart(),"cart");
                }
                else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAddProductToCart> call,
                                  Throwable t) {
                dialog.dismiss();
                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView txt_product_name,product_price,txt_varity;
        ImageView product_img;
        TextView addcart;
        CardView cardview;
        RelativeLayout featured_value;



        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            this.txt_product_name =itemView.findViewById(R.id.txt_product_name);
            this.product_price =itemView.findViewById(R.id.product_price);
            this.product_img =itemView.findViewById(R.id.product_img);
            this.addcart =itemView.findViewById(R.id.addcart);
            this.cardview =itemView.findViewById(R.id.cardview);
            this.txt_varity = itemView.findViewById(R.id.txt_varity);
            this.featured_value = itemView.findViewById(R.id.featured_value);
        }
    }
}

