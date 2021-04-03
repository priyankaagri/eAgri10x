package com.mobile.agri10x.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.adapters.BookingorderAdpter;
import com.mobile.agri10x.adapters.PurchaseorderAdpter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

import com.mobile.agri10x.models.GetOrderList;
import com.mobile.agri10x.models.GetOrderListDatumBooking;
import com.mobile.agri10x.models.GetOrderListDatumCheckout;
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

public class YourOrderFragment extends Fragment {

    private ImageView mBackButton;
    TextView btn_booking,btn_purchase;
    ShimmerRecyclerView recycleview_purchase_list;
    RecyclerView recycleview_booking_list;
    LinearLayoutManager linearLayoutManager;
    LinearLayout booking_layout,purchase_layout,layout_for_booking,layout_for_purchase;

    boolean getval= true;
    public static List<GetOrderListDatumCheckout> checkoutorderlist = new ArrayList<>();
    public static List<GetOrderListDatumBooking> bookingorderlist = new ArrayList<>();
    PurchaseorderAdpter purchaseorderAdpter;
    BookingorderAdpter bookingorderAdpter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView=inflater.inflate(R.layout.fragment_your_order_menu_layout,container,false);
        init(addStockView);

        getlistorderapi();
        if(getArguments()!=null){
            getval = getArguments().getBoolean("getValue");
            Log.d("getvalue", String.valueOf(getval));

        }
        else {

        }


        return addStockView;

    }

    private void getlistorderapi() {
        checkoutorderlist.clear();
        bookingorderlist.clear();
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

                Log.d("response",response.toString());
                if (response.isSuccessful()) {
                    Log.d("checkresponse", "success");

                    if (!response.body().getData().isEmpty()) {
                        checkoutorderlist.addAll(response.body().getData().get(0).getCheckoutList());
                        bookingorderlist.addAll(response.body().getData().get(0).getBookingList());
                        if (checkoutorderlist.size() > 0) {
                            purchaseorderAdpter = new PurchaseorderAdpter(checkoutorderlist, getActivity());
                            recycleview_purchase_list.setAdapter(purchaseorderAdpter);
                            purchaseorderAdpter.notifyDataSetChanged();
                        }

                        if (bookingorderlist.size() > 0) {

                            bookingorderAdpter = new BookingorderAdpter(bookingorderlist, getActivity());
                            recycleview_booking_list.setAdapter(bookingorderAdpter);
                            bookingorderAdpter.notifyDataSetChanged();

                        }


                        if (getval) {
                            btn_purchase.setBackgroundResource(R.drawable.hollow_back);
                            btn_booking.setBackgroundResource(R.drawable.filll_back);
                            btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                            btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                            booking_layout.setVisibility(View.VISIBLE);
                            purchase_layout.setVisibility(View.GONE);
                        }
                        if (!getval) {

                            btn_purchase.setBackgroundResource(R.drawable.click_change1);
                            btn_booking.setBackgroundResource(R.drawable.click_chnage2);
                            btn_purchase.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                            btn_booking.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.black));
                            purchase_layout.setVisibility(View.VISIBLE);
                            booking_layout.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOrderList> call, Throwable t) {
                Log.d("checkresponse",t.toString());
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



        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview_booking_list.setLayoutManager(linearLayoutManager);


/*recycleview_booking_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
recycleview_booking_list.showShimmer();*/

        recycleview_purchase_list.setLayoutManager(new GridLayoutManager(getActivity(), 1), R.layout.item_shimmer_card_view);
        recycleview_purchase_list.showShimmer();


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