package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;


import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetProductsInCartData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_Fragment extends Fragment {

    ShimmerRecyclerView recyle_livetrade;
    List<GetProductsInCartData> ProductsInCartlist = new ArrayList<>();
    TextView sub_toatl_txt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart_view, container, false);
        recyle_livetrade=view.findViewById(R.id.recyle_cart_view);
        sub_toatl_txt=view.findViewById(R.id.sub_toatl_txt);

        getProductinCart();
        return  view;
    }

    private void getProductinCart() {
        ProductsInCartlist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID",SessionManager.getKeyTokenUser(getContext()));
        Log.d("getuserid",SessionManager.getKeyTokenUser(getContext()));
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
                Log.d("ProductinCart",response.toString());
                if (response.isSuccessful()) {
                    ProductsInCartlist.addAll(response.body().getData());
                    sub_toatl_txt.setText("₹ "+String.valueOf(response.body().getSubTotal()));
                    if(ProductsInCartlist.size()>0)
                    {
                        recyle_livetrade.setLayoutManager(new GridLayoutManager(getActivity(),1), R.layout.item_shimmer_daily_deals);
                        recyle_livetrade.showShimmer();
                        TradeValueAddCartProductList tradeValueAdpter = new TradeValueAddCartProductList(ProductsInCartlist, getActivity(),true);
                        recyle_livetrade.setAdapter(tradeValueAdpter);
                        tradeValueAdpter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetProductsInCart> call,
                                  Throwable t) {
                recyle_livetrade.hideShimmer();
                Toast.makeText(getActivity(),"Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
