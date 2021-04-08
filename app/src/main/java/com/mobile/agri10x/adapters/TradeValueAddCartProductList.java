package com.mobile.agri10x.adapters;

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

import com.mobile.agri10x.fragments.TradeValueAddCart;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.LoginActivity;
import com.mobile.agri10x.models.GetProductsInCartProductData;
import com.mobile.agri10x.models.GetRemoveProduct;
import com.mobile.agri10x.models.UpdateCart;
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

import static com.mobile.agri10x.utils.ToastMessages.makeToast;

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
        holder.product_garde.setText("Grade : "+ProductsInCartlist.get(position).getGrade());
        holder.product_quantity.setText(String.valueOf(ProductsInCartlist.get(position).getQuantity()));
        holder.product_varity.setText(ProductsInCartlist.get(position).getVariety());

        if(ProductsInCartlist.get(position).getPrice() != null){
            double number1 = ProductsInCartlist.get(position).getPrice();
            NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currency1 = format1.format(number1);
            System.out.println("Currency in INDIA : " + currency1);
            String totl_pric = String.format("%.2f", (ProductsInCartlist.get(position).getPrice()));
            holder.total_price.setText(currency1);
        }else{

        }



        holder.avlstock.setText(String.valueOf("Avl Quantity : "+ProductsInCartlist.get(position).getTotalAvailable()*ProductsInCartlist.get(position).getWeight()+" KG"));

        String test = String.valueOf(ProductsInCartlist.get(position).getQuantity());
        holder.product_total_weight.setText(""+Double.parseDouble(test)*ProductsInCartlist.get(position).getWeight());

if(ProductsInCartlist.get(position).getPrice() != null){
    String value = holder.product_total_weight.getText().toString();

    double number = ProductsInCartlist.get(position).getPrice() * Double.parseDouble(value);
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    String currency = format.format(number);
    String pricepeoduct = String.format("%.2f", ProductsInCartlist.get(position).getPrice() * Double.parseDouble(value));
    holder.product_price.setText("Total : " + currency);
}





        String productimg =  ProductsInCartlist.get(position).getCommodityID()+".png";
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

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
            makeToast(context,context.getResources().getString(R.string.quote_your_price));
        }else {
            if(SessionManager.isLoggedIn(context)){
                double int_enterValue= Double.parseDouble(holder.product_total_weight.getText().toString());
                if(int_enterValue%50==0){

                    if(int_enterValue>=500){
                        if (totalweight>=int_enterValue){
                            String quantity= String.valueOf(int_enterValue/50);
                            CallApiUpdateCard(ProductsInCartlist.get(getWaight).getUserProductID(),quantity);

                        }else {
                            makeToast(context,context.getResources().getString(R.string.stock_not_avaliable));
                        }

                    }else {
                        makeToast(context,context.getResources().getString(R.string.min_trade_quantity));
                    }

                }else {
                    makeToast(context,context.getResources().getString(R.string.quantity_multiple));
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

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle(R.string.remove_product);
                builder.setMessage(R.string.youwant_toremove);
// add the buttons
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// do something like...
                        CallApiRemoveProduct(ProductsInCartlist.get(position).getUserProductID(), position);
                    }


                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// do something like...
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
// create and show the alert dialog
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    private void CallApiUpdateCard(String userProductID, String quantity) {
        dialog2=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("quantity", quantity);
        jsonParams.put("updateThis",userProductID);

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
        final Call<UpdateCart> loginCall = apiService.wsGetUpdateCart("123456",body);
        loginCall.enqueue(new Callback<UpdateCart>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<UpdateCart> call,
                                   Response<UpdateCart> response) {
                dialog2.dismiss();

                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
                    HomePageActivity.removeFragment(new TradeValueAddCart());
        HomePageActivity.setFragment(new TradeValueAddCart(),"cart");
                }
                else {

                    makeToast(context,context.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call,
                                  Throwable t) {
                dialog2.dismiss();
                makeToast(context,context.getResources().getString(R.string.something_went_wrong));
            }
        });
    }

    private void CallApiRemoveProduct(String userProductID,int position) {
        dialog=new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        jsonParams.put("userProductID",userProductID);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        final Call<GetRemoveProduct> loginCall = apiService.wsGetRemoveProduct("123456",body);
        loginCall.enqueue(new Callback<GetRemoveProduct>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetRemoveProduct> call,
                                   Response<GetRemoveProduct> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    HomePageActivity.getProductinCart();
                    HomePageActivity.removeFragment(new TradeValueAddCart());
                    HomePageActivity.setFragment(new TradeValueAddCart(),"cart");
                }
                else {

                    makeToast(context,context.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<GetRemoveProduct> call,
                                  Throwable t) {
                dialog.dismiss();
                makeToast(context,context.getResources().getString(R.string.something_went_wrong));
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
