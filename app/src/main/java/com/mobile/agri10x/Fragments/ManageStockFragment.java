package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mobile.agri10x.Adapter.ManageStockAdapter;
import com.mobile.agri10x.Adapter.OnlyFeaturedAdapter;
import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetStockByID;
import com.mobile.agri10x.models.GetStockByIDDatum;
import com.mobile.agri10x.models.QueryFeatureOnly;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStockFragment extends Fragment {
    private ImageView mBackButton;
    ShimmerRecyclerView recycleview_manage_stock;
    ManageStockAdapter manageStockAdapter;
    List<GetStockByIDDatum> listmanagestock = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView = inflater.inflate(R.layout.fragment_manage_stock_menu_layout, container, false);
        init(addStockView);
        callapimanageStock();
        return addStockView;
    }

    private void callapimanageStock() {
        listmanagestock.clear();
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
        final Call<GetStockByID> loginCall = apiService.wsGetStockByID("123456",
                body);
        loginCall.enqueue(new Callback<GetStockByID>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetStockByID> call,
                                   Response<GetStockByID> response) {
                recycleview_manage_stock.hideShimmer();
                Log.d("resFeatureonly", response.toString());
                if (response.isSuccessful()) {
                    listmanagestock.addAll(response.body().getData());
                    Log.d("getsizefeat", String.valueOf(listmanagestock.size()));
                    if (listmanagestock.size() > 0) {
                        manageStockAdapter = new ManageStockAdapter(getActivity(), listmanagestock);
                        recycleview_manage_stock.setAdapter(manageStockAdapter);
                    }


                } else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetStockByID> call,
                                  Throwable t) {
                recycleview_manage_stock.hideShimmer();
// Toast.makeText(Otp_Screen_Activity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init(View view) {
        mBackButton = view.findViewById(R.id.btn_back_manage_stock_id);
        recycleview_manage_stock = view.findViewById(R.id.recycleview_manage_stock);
        recycleview_manage_stock.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_manage_stock.showShimmer();
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new ManageStockFragment());
    }
}