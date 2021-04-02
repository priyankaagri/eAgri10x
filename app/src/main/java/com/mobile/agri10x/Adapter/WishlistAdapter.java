package com.mobile.agri10x.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.TradeValueAddCart;
import com.mobile.agri10x.Fragments.YourWishListFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetAddProductToCart;
import com.mobile.agri10x.models.GetProductInWishListData;
import com.mobile.agri10x.models.GetRemoveFromWishlist;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
Dialog dialogfordetailpage;
    Date fromDate,currnetDate,toDate;
    private List<GetProductInWishListData> wishLists;
    Context context;
    ImageView commodity_image;
    TextView txt_commodity_name,txt_variety_name,txt_product_pack_size,txt_avilable_quantity,txt_delete;
    AlertDialog dialog;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_adapter, parent, false);
        context = parent.getContext();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txt_product_name.setText(""+wishLists.get(position).getName());
        double number1 = wishLists.get(position).getPrice();
        NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency1 = format1.format(number1);
        System.out.println("Currency in INDIA : " + currency1);
        holder.txt_product_price.setText(""+currency1);
        holder.txt_varity_name.setText(""+wishLists.get(position).getVariety());
        holder.img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wishlistid = wishLists.get(position).getWishListID();
                String userID = wishLists.get(position).getUserID();

                Log.d("getvalues",wishlistid+" "+userID);

                dialogfordetailpage = new Dialog(context);
                dialogfordetailpage.setContentView(R.layout.layout_detailof_wishlist);
                dialogfordetailpage.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogfordetailpage.setCancelable(true);
                dialogfordetailpage.setCanceledOnTouchOutside(true);
                dialogfordetailpage.getWindow().getAttributes().windowAnimations = R.style.animation;
                commodity_image = dialogfordetailpage.findViewById(R.id.commodity_image);
                txt_commodity_name = dialogfordetailpage.findViewById(R.id.txt_commodity_name);
                txt_variety_name = dialogfordetailpage.findViewById(R.id.txt_variety_name);
                txt_product_pack_size = dialogfordetailpage.findViewById(R.id.txt_product_pack_size);
                txt_avilable_quantity = dialogfordetailpage.findViewById(R.id.txt_avilable_quantity);
                txt_delete = dialogfordetailpage.findViewById(R.id.txt_delete);


                String strimg =  wishLists.get(position).getCommodityID()+".png";
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
                        .into(commodity_image);
                txt_commodity_name.setText(wishLists.get(position).getName());
                txt_variety_name.setText(wishLists.get(position).getVariety());
                txt_avilable_quantity.setText("Available Quantity: "+String.valueOf(wishLists.get(position).getQuantity())+" KG");
                txt_product_pack_size.setText("Packaging Size: "+String.valueOf(wishLists.get(position).getWeight())+" KG");


                Log.d("deatils",wishLists.get(position).getName()+" "+String.valueOf(wishLists.get(position).getQuantity())+" "+String.valueOf(wishLists.get(position).getWeight())+" KG");

                txt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                        builder.setTitle("Remove From Wishlist");
                        builder.setMessage("Are you sure you want to Remove ?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
dialogfordetailpage.dismiss();
                                callapideleteproduct(wishlistid,userID);

                                /* Toast.makeText(getActivity(), "You clicked Delete!", Toast.LENGTH_SHORT).show();*/
                            }

                            private void callapideleteproduct(String wishlistid, String userID) {

                                Map<String, Object> jsonParams = new ArrayMap<>();
                                jsonParams.put("userID", userID);
                                jsonParams.put("wishListID",wishlistid);
                                Log.d("getparams",wishLists.get(position).getUserID()+" " +wishLists.get(position).getWishListID());
                                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
                                AgriInvestor apiService = ApiHandler.getApiService();

                                try {
                                    SSLCertificateManagment.trustAllHosts();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (KeyManagementException e) {
                                    e.printStackTrace();
                                }
                                Call<GetRemoveFromWishlist> call = apiService.wsGetRemoveFromWiishList("123456", body);
                                call.enqueue(new Callback<GetRemoveFromWishlist>() {
                                    @Override
                                    public void onResponse(Call<GetRemoveFromWishlist> call, Response<GetRemoveFromWishlist> response) {

                                        Log.d("getfeatureresponse", response.toString());
                                        dialogfordetailpage.dismiss();
                                        HomePageActivity.removeFragment(new YourWishListFragment());
                                        HomePageActivity.setFragment(new YourWishListFragment(),"wishlist");

                                    }

                                    @Override
                                    public void onFailure(Call<GetRemoveFromWishlist> call, Throwable t) {

                                    }
                                });


                            }


                        });

                        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
dialogfordetailpage.dismiss();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancel", null);

                        androidx.appcompat.app.AlertDialog dialog = builder.create();
                        dialog.show();


                    }
                });


                dialogfordetailpage.show();
            }
        });
        String crrentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d("cccccdate",crrentdate);
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currnetDate = format0.parse(crrentdate);
            System.out.println(currnetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String str_validfrom=wishLists.get(position).getValidFrom();
        String[] separated = str_validfrom.split("T");
        String fromdate=separated[0];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = format.parse(fromdate);
            System.out.println(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String str_validTo=wishLists.get(position).getValidTo();
        String[] separatedTo = str_validTo.split("T");
        String Todate=separatedTo[0];
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            toDate = format2.parse(Todate);
            System.out.println(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //d.after(min) && d.before(max);
        if((currnetDate.after(fromDate) && currnetDate.before(toDate) )&& (wishLists.get(position).getQuantity()>=500)){
            //   Toast.makeText(context, "out of stock"+currnetDate, Toast.LENGTH_SHORT).show();
            holder.relative_stock.setBackgroundResource(R.drawable.featured_bg);
            holder.stock.setText("In Stock");

        }else {
            holder.relative_stock.setBackgroundResource(R.drawable.featured_red);
            holder.stock.setText("Out of Stock");
            holder.txt_tradenow.setVisibility(View.GONE);
            holder.txt_productnotavl.setVisibility(View.VISIBLE);
            holder.txt_productnotavl.setText("Product not Available");

        }


        holder.txt_tradenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wishlistid = wishLists.get(position).getWishListID();
                String userID = wishLists.get(position).getUserID();

                String orderid_forTrade =wishLists.get(position).getOrderID();
                String grade_fortrade =wishLists.get(position).getGrade();
                String commodityname_fortrade =wishLists.get(position).getName();
                double price_fortrade = wishLists.get(position).getPrice();

                callapideleteproductaftertrade(wishlistid,userID,orderid_forTrade,grade_fortrade,commodityname_fortrade,price_fortrade);




            }
        });
        String strimg =  wishLists.get(position).getCommodityID()+".png";
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("exception", String.valueOf(exception));
                    }
                })
                .build();
        picasso.load("https://data.agri10x.com/images/products/"+strimg)
                .into(holder.product_image);

    }
private void  callapideleteproductaftertrade(String wishlistid,String userID,String orderid_forTrade,String grade_fortrade,String commodityname_fortrade,double price_fortrade){
    Map<String, Object> jsonParams = new ArrayMap<>();
    jsonParams.put("userID", userID);
    jsonParams.put("wishListID",wishlistid);
  //  Log.d("getparams",wishLists.get(position).getUserID()+" " +wishLists.get(position).getWishListID());
    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
    AgriInvestor apiService = ApiHandler.getApiService();

    try {
        SSLCertificateManagment.trustAllHosts();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (KeyManagementException e) {
        e.printStackTrace();
    }
    Call<GetRemoveFromWishlist> call = apiService.wsGetRemoveFromWiishList("123456", body);
    call.enqueue(new Callback<GetRemoveFromWishlist>() {
        @Override
        public void onResponse(Call<GetRemoveFromWishlist> call, Response<GetRemoveFromWishlist> response) {

            Log.d("getfeatureresponse", response.toString());

            CallApiaddTOCard(orderid_forTrade,grade_fortrade,commodityname_fortrade,price_fortrade);

        }

        @Override
        public void onFailure(Call<GetRemoveFromWishlist> call, Throwable t) {

        }
    });

}

    private void CallApiaddTOCard(String orderID, String grade, String name, double price) {
        int quantity = 10;
        dialog=new WishlistAdapter.Alert().pleaseWait();
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
                    HomePageActivity.removeFragment(new YourWishListFragment());
                    HomePageActivity.getProductinCart();
                    Toast.makeText(context, quantity+" Kg of "+ name +" has been added to trade", Toast.LENGTH_LONG).show();
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

    public WishlistAdapter(FragmentActivity activity, List<GetProductInWishListData> whishLists) {
        this.wishLists = whishLists;
    }

    @Override
    public int getItemCount() {
        return wishLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image,img_setting;
        TextView txt_product_name, txt_product_price, txt_varity_name,txt_productnotavl,txt_tradenow,stock;
        RelativeLayout relative_stock;

        public MyViewHolder(View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_varity_name = itemView.findViewById(R.id.txt_varity_name);
            txt_productnotavl = itemView.findViewById(R.id.txt_productnotavl);
            stock = itemView.findViewById(R.id.stock);
            relative_stock = itemView.findViewById(R.id.relative_stock);
            img_setting = itemView.findViewById(R.id.img_setting);
            txt_tradenow = itemView.findViewById(R.id.txt_tradenow);



        }
    }
}
