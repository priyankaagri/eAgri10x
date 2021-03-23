package com.mobile.agri10x.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.TradeValueAddCart;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.GetProductsInCartProductData;
import com.mobile.agri10x.models.GetRemoveProduct;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TradeValueAddCartProductList extends RecyclerView.Adapter<TradeValueAddCartProductList.ViewHolers> {
    Context context;
    AlertDialog dialog,dialog2;
    View prevselected=null;
    List<GetProductsInCartProductData> ProductsInCartlist = new ArrayList<>();
    boolean check;
    TradeValueAddCartProductList tradeValueAddCartProductList;


    public TradeValueAddCartProductList(List<GetProductsInCartProductData> productsInCartlist, Context context, boolean check) {
        this.context=context;
        this.ProductsInCartlist=productsInCartlist;
        this.check =check;
        tradeValueAddCartProductList=this;
    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_value_addcart_product_list_adpter, parent, false);
        ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolers holder, final int position) {


        holder.product_name.setText(ProductsInCartlist.get(position).getName());
        holder.product_garde.setText("Grade: "+ProductsInCartlist.get(position).getGrade());
        holder.product_quantity.setText(String.valueOf(ProductsInCartlist.get(position).getQuantity()));
        holder.product_varity.setText(ProductsInCartlist.get(position).getVariety());

        String totl_pric = String.format("%.2f", (ProductsInCartlist.get(position).getPrice()));
        holder.total_price.setText(totl_pric);


        holder.avlstock.setText(String.valueOf("Avl Quantity : "+ProductsInCartlist.get(position).getTotalAvailable()*ProductsInCartlist.get(position).getWeight()+" KG"));

        String test = String.valueOf(ProductsInCartlist.get(position).getQuantity());
        holder.product_total_weight.setText(""+Double.parseDouble(test)*ProductsInCartlist.get(position).getWeight());


        String value = holder.product_total_weight.getText().toString();
        String pricepeoduct = String.format("%.2f", ProductsInCartlist.get(position).getPrice()*Double.parseDouble(value));
// double totol_price=ProductsInCartlist.get(position).getPrice()*Double.parseDouble(value);
        holder.product_price.setText("Total : ₹ "+pricepeoduct);





        String productimg =  ProductsInCartlist.get(position).getCommodityID()+".png";
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("exception", String.valueOf(exception));
                    }
                })
                .build();
        holder.product_total_weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (prevselected!=null)
                {
                    prevselected.setVisibility(View.INVISIBLE);
                    prevselected=holder.txt_update;
                }else
                {
                    prevselected=holder.txt_update;
                }
                holder.txt_update.setVisibility(View.VISIBLE);
                // Address_Show.cheout.setEnabled(true);

            }
        });

holder.txt_update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
int getWaight=holder.getAdapterPosition();
        String str_enterValue = holder.product_total_weight.getText().toString();
        double totalweight = ProductsInCartlist.get(position).getTotalAvailable()*ProductsInCartlist.get(position).getWeight();
        if(TextUtils.isEmpty(str_enterValue)){
            Toast.makeText(context, "Please quote price", Toast.LENGTH_SHORT).show();
        }else {
            if(SessionManager.isLoggedIn(context)){
                double int_enterValue= Double.parseDouble(holder.product_total_weight.getText().toString());
                if(int_enterValue%50==0){

                    if(int_enterValue>=500){
                        if (totalweight>=int_enterValue){
                            String quantity= String.valueOf(int_enterValue/50);
                            CallApiUpdateCard(ProductsInCartlist.get(getWaight).getUserProductID(),quantity);

                        }else {
                            Toast.makeText(context, "Stock is not available for this product.", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(context, "Minimum Trade Quantity Required is 500 KG", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(context, "Please enter in terms of multiple of 500", Toast.LENGTH_SHORT).show();
                }
            }else {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        }

    }
});
        picasso.load("https://data.agri10x.com/images/products/"+productimg).into(holder.product_img);
holder.img_remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CallApiRemoveProduct(ProductsInCartlist.get(position).getUserProductID(),position);
    }
});
    }

    private void CallApiUpdateCard(String userProductID, String quantity) {
        dialog2=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("quantity", quantity);
        jsonParams.put("updateThis",userProductID);
        Log.d("id",userProductID+" "+SessionManager.getKeyTokenUser(context));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<UpdateCart> loginCall = apiService.wsGetUpdateCart("123456",body);
        loginCall.enqueue(new Callback<UpdateCart>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<UpdateCart> call,
                                   Response<UpdateCart> response) {
                dialog2.dismiss();
                Log.d("updatecart",response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
                    HomePageActivity.removeFragment(new TradeValueAddCart());
        HomePageActivity.setFragment(new TradeValueAddCart(),"cart");
                }
                else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call,
                                  Throwable t) {
                dialog2.dismiss();
                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallApiRemoveProduct(String userProductID,int position) {
        dialog=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        jsonParams.put("userProductID",userProductID);
Log.d("id",userProductID+" "+SessionManager.getKeyTokenUser(context));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetRemoveProduct> loginCall = apiService.wsGetRemoveProduct("123456",body);
        loginCall.enqueue(new Callback<GetRemoveProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetRemoveProduct> call,
                                   Response<GetRemoveProduct> response) {
                dialog.dismiss();
                Log.d("removecart",response.toString());
                if (response.isSuccessful()) {
//                    ProductsInCartlist.remove(position);
//                    tradeValueAddCartProductList.notifyDataSetChanged();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    HomePageActivity.getProductinCart();
                    HomePageActivity.removeFragment(new TradeValueAddCart());
                    HomePageActivity.setFragment(new TradeValueAddCart(),"cart");
                }
                else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRemoveProduct> call,
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
            View mView = ((Activity) context).getLayoutInflater().inflate(R.layout.alert_progress_deleting, null);
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
        return ProductsInCartlist.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        TextView total_price,product_garde,product_varity,product_name,product_price,product_quantity,avlstock,packingsize,txt_update;
EditText product_total_weight;
        ImageView product_img,img_remove;
        CardView cardview;
        LinearLayout lledt;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            this.product_total_weight =itemView.findViewById(R.id.product_total_weight);
            this.product_quantity =itemView.findViewById(R.id.product_quantity);
            this.total_price =itemView.findViewById(R.id.totol_price);
            this.product_garde =itemView.findViewById(R.id.product_garde);
            this.product_varity =itemView.findViewById(R.id.product_varity);
            this.product_name =itemView.findViewById(R.id.product_name);
            this.product_price =itemView.findViewById(R.id.product_price);
            this.product_img =itemView.findViewById(R.id.product_img);
            this.cardview =itemView.findViewById(R.id.cardview);
            this.avlstock =itemView.findViewById(R.id.avlstock);
            this.packingsize =itemView.findViewById(R.id.packingsize);
            this.img_remove =itemView.findViewById(R.id.img_remove);
            this.txt_update =itemView.findViewById(R.id.txt_update);
            this.lledt = itemView.findViewById(R.id.lledt);
        }
    }
}
