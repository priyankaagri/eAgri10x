package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.badge.BadgeDrawable;
import com.mobile.agri10x.Adapter.PurchaseorderAdpter;
import com.mobile.agri10x.Adapter.TradeValueAddCartProductList;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.CheckoutListFromOrderList;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetOrderList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourOrderFragment extends Fragment {
    private ImageView mBackButton;
    TextView btn_booking,btn_purchase;
    ShimmerRecyclerView recycleview_booking_list,recycleview_purchase_list;
    LinearLayout booking_layout,purchase_layout,layout_for_booking,layout_for_purchase;
    Dialog bookingdetaildialog;
    Dialog purchasedetaildialog;
    ImageView cancle_btn;
    public  static List<CheckoutListFromOrderList> ProductsInOrderlist = new ArrayList<>();
    PurchaseorderAdpter purchaseorderAdpter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView=inflater.inflate(R.layout.fragment_your_order_menu_layout,container,false);
        init(addStockView);

        getlistorderapi();

        return addStockView;

    }

    private void getlistorderapi() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        String getuserid =SessionManager.getKeyTokenUser(getActivity());

        getuserid = getuserid.replaceAll(" ","");
        Log.d("getuserid",getuserid);

        jsonParams.put("UserID",getuserid);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetOrderList> calltoapi = apiService.wsOrdeList("123456",body);

        calltoapi.enqueue(new Callback<GetOrderList>() {
            @Override
            public void onResponse(Call<GetOrderList> call, Response<GetOrderList> response) {

                                Log.d("productinorder",response.toString());
                if (response.isSuccessful()){

                    ProductsInOrderlist.addAll(response.body().getData().getCheckoutList());
                    if(ProductsInOrderlist.size()>0){
                        purchaseorderAdpter = new PurchaseorderAdpter(ProductsInOrderlist, getActivity(),true);
                        recycleview_purchase_list.setAdapter(purchaseorderAdpter);
                        purchaseorderAdpter.notifyDataSetChanged();
                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<GetOrderList> call, Throwable t) {
                Log.d("gfhgyhg","error");
                Toast.makeText(getActivity(),"Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void init(View view) {
        mBackButton=view.findViewById(R.id.btn_back_your_order_id);
        btn_booking=view.findViewById(R.id.btn_booking);
        btn_purchase=view.findViewById(R.id.btn_purchase);
        purchase_layout=view.findViewById(R.id.purchase_layout);
        booking_layout=view.findViewById(R.id.booking_layout);
        recycleview_booking_list=view.findViewById(R.id.recycleview_booking_list);
        recycleview_purchase_list=view.findViewById(R.id.recycleview_purchase_list);




        recycleview_booking_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_booking_list.showShimmer();


        recycleview_purchase_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_purchase_list.showShimmer();
//        layout_for_booking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                bookingdetaildialog = new Dialog(getActivity());
//                bookingdetaildialog.setContentView(R.layout.layout_detailof_bookingorder);
//                bookingdetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                bookingdetaildialog.setCancelable(true);
//                bookingdetaildialog.setCanceledOnTouchOutside(true);
//                bookingdetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//               /* product_image = dialogfordetailpage.findViewById(R.id.product_image);
//                txt_product_name = dialogfordetailpage.findViewById(R.id.txt_product_name);
//                txt_product_pack_size = dialogfordetailpage.findViewById(R.id.txt_product_pack_size);
//                txt_avilable_quantity = dialogfordetailpage.findViewById(R.id.txt_avilable_quantity);
//                txt_delete = dialogfordetailpage.findViewById(R.id.txt_delete);*/
//
//                cancle_btn = bookingdetaildialog.findViewById(R.id.cancle_btn);
//
//                cancle_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bookingdetaildialog.dismiss();
//                    }
//                });
//                bookingdetaildialog.show();
//
//            }
//        });

//        layout_for_purchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                purchasedetaildialog = new Dialog(getActivity());
//                purchasedetaildialog.setContentView(R.layout.layout_detailof_purchaseorder);
//                purchasedetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                purchasedetaildialog.setCancelable(true);
//                purchasedetaildialog.setCanceledOnTouchOutside(true);
//                purchasedetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//                purchasedetaildialog.show();
//
//            }
//        });

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_purchase.setBackgroundResource(R.drawable.click_change1);
                btn_booking.setBackgroundResource(R.drawable.click_chnage2);
                btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                purchase_layout.setVisibility(View.VISIBLE);
                booking_layout.setVisibility(View.GONE);





            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_purchase.setBackgroundResource(R.drawable.hollow_back);
                btn_booking.setBackgroundResource(R.drawable.filll_back);
                btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                booking_layout.setVisibility(View.VISIBLE);
                purchase_layout.setVisibility(View.GONE);

            }

            private void getOrderlistapi() {



            }
        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new YourOrderFragment());
    }


}
