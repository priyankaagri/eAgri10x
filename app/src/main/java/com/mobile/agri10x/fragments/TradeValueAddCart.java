package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;


import com.mobile.agri10x.adapters.TradeValueAddCartProductList;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetProductsInCartProductData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TradeValueAddCart extends Fragment {
   ImageView but_back;
    double amt;
    RelativeLayout totallayout;
    Dialog dialoginfo;
   ShimmerRecyclerView recyle_livetrade;
    TradeValueAddCartProductList tradeValueAdpter;
    public static double subTotal;
    public  static  double totalkgs;
    public  static  List<GetProductsInCartProductData> ProductsInCartlist = new ArrayList<>();
    TextView totaltradeamount,btn_orderbook,btn_purchaes,txt_tradeamt;
Button checkout_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart_view, container, false);
        but_back=view.findViewById(R.id.but_back);
        totallayout=view.findViewById(R.id.totallayout);
        totaltradeamount=view.findViewById(R.id.totaltradeamount);
        btn_orderbook = view.findViewById(R.id.btn_orderbook);
        btn_purchaes = view.findViewById(R.id.btn_purchaes);
        txt_tradeamt = view.findViewById(R.id.txt_tradeamt);
        btn_orderbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HomePageActivity.setFragment(new BookOrderFragment(),"book");
                if(ProductsInCartlist.size() >0){
                    HomePageActivity.removeFragment(new TradeValueAddCart());
                    BookOrderFragment fragment = new BookOrderFragment(); // replace your custom fragment class
                    Bundle bundle = new Bundle();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    bundle.putString("value", String.valueOf(amt)); // use as per your need
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                    fragmentTransaction.commit();
                }
                else{
                    Toast.makeText(getActivity(),"No data available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_purchaes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProductsInCartlist.size() >0){
                    HomePageActivity.removeFragment(new TradeValueAddCart());
                PurchaseOrderFargment fragment = new PurchaseOrderFargment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                bundle.putString("value", String.valueOf(amt)); // use as per your need
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
                fragmentTransaction.commit();
                }
                else{
                    Toast.makeText(getActivity(),"No data available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new TradeValueAddCart());
            }
        });
        recyle_livetrade=view.findViewById(R.id.recyle_cart_view);
        //sub_toatl_txt=view.findViewById(R.id.sub_toatl_txt);
        recyle_livetrade.setLayoutManager(new GridLayoutManager(getActivity(),1), R.layout.item_shimmer_card_view);
        recyle_livetrade.showShimmer();
        getProductinCart();
        return  view;
    }
    private void getProductinCart() {
        ProductsInCartlist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID",SessionManager.getKeyTokenUser(getContext()));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetProductsInCart> ProductinCart = apiService.wsgetProductinCart("123456",
                body);
        ProductinCart.enqueue(new Callback<GetProductsInCart>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetProductsInCart> call,
                                   Response<GetProductsInCart> response) {
                recyle_livetrade.hideShimmer();

                if (response.isSuccessful()) {
                    ProductsInCartlist.addAll(response.body().getProducts());
                    subTotal=response.body().getSubTotal();
                                   totalkgs=response.body().getTotalKgs();

                    double withconveniencecharge = (subTotal / 100.0f) *2;
                    double withhandlefees = (subTotal / 100.0f) *2;
                    double withcommision = (subTotal / 100.0f) *1;


                    amt=subTotal+withconveniencecharge+withhandlefees+withcommision;

                    double number = subTotal + withconveniencecharge + withhandlefees + withcommision;
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                    String currency = format.format(number);
                    String subtotalString=format.format(subTotal);
                    String withconveniencechargeString=format.format(withconveniencecharge);
                    String withhandlefeesString=format.format(withhandlefees);
                    String withcommisionString=format.format(withcommision);
                    System.out.println("Currency in INDIA : " + currency);
                    String pricepeoduct = String.format("%.2f", subTotal + withconveniencecharge + withhandlefees + withcommision);
                    totaltradeamount.setText(currency);

                    totallayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialoginfo = new Dialog(getActivity());

                            dialoginfo.setContentView(R.layout.layout_charges_dialog);
                            // int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                            //  int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
                            dialoginfo.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialoginfo.getWindow().setGravity(Gravity.CENTER);
                            dialoginfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialoginfo.setCancelable(true);
                            dialoginfo.setCanceledOnTouchOutside(false);
                            dialoginfo.getWindow().getAttributes().windowAnimations = R.style.animation;
                            ImageView cancle_btn=dialoginfo.findViewById(R.id.cancle_btn);
                            TextView txt_trade_amont=dialoginfo.findViewById(R.id.txt_trade_amont);
                            TextView txt_convenience_amount=dialoginfo.findViewById(R.id.txt_convenience_amount);
                            TextView txt_commition_amount=dialoginfo.findViewById(R.id.txt_commition_amount);
                            TextView txt_handling_amount=dialoginfo.findViewById(R.id.txt_handling_amount);
                            TextView txt_total_trade_amount=dialoginfo.findViewById(R.id.txt_total_trade_amount);
                            txt_trade_amont.setText(subtotalString);
                            txt_convenience_amount.setText(withconveniencechargeString);
                            txt_commition_amount.setText(withcommisionString);
                            txt_handling_amount.setText(withhandlefeesString);
                            txt_total_trade_amount.setText(currency);
                            cancle_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialoginfo.dismiss();
                                }
                            });
                            dialoginfo.show();

                        }
                    });


                    if(ProductsInCartlist.size()>0)
                    {

                        tradeValueAdpter = new TradeValueAddCartProductList(ProductsInCartlist, getActivity(),true);
                        recyle_livetrade.setAdapter(tradeValueAdpter);
                        tradeValueAdpter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(),"No data available", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetProductsInCart> call,
                                  Throwable t) {
                recyle_livetrade.hideShimmer();
                Toast.makeText(getActivity(),R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
