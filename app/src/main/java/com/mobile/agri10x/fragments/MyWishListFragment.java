package com.mobile.agri10x.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mobile.agri10x.adapters.WishlistAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetProductInWishList;
import com.mobile.agri10x.models.GetProductInWishListData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWishListFragment extends Fragment  {

    private ImageView mBackButton;
    ShimmerRecyclerView recycleview_wishlist_stock;
    WishlistAdapter wishlistAdapter;
    ArrayList<GetProductInWishListData> arrayListwishlist=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_your_wish_menu_layout, container, false);
        mBackButton = view.findViewById(R.id.btn_back_manage_stock_id);
        recycleview_wishlist_stock = view.findViewById(R.id.recycleview_wishlist_stock);
        recycleview_wishlist_stock.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_wishlist_stock.showShimmer();
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePageActivity.removeFragment(new MenuFragment());
            }
        });

        callApigetWishlist();
        return view;
    }

    private void callApigetWishlist() {
        arrayListwishlist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
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
                recycleview_wishlist_stock.hideShimmer();


                if (response.isSuccessful()) {
                    arrayListwishlist.addAll(response.body().getData());


                    if (arrayListwishlist.size() > 0) {


                        wishlistAdapter = new WishlistAdapter(getActivity(), arrayListwishlist);
                        recycleview_wishlist_stock.setAdapter(wishlistAdapter);
                    }else{
                        Toast.makeText(getActivity(),"Wishlist is empty",Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProductInWishList> call,
                                  Throwable t) {
                recycleview_wishlist_stock.hideShimmer();
// Toast.makeText(Otp_Screen_Activity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}