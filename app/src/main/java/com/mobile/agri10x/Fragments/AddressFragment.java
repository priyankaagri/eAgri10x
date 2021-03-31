package com.mobile.agri10x.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mobile.agri10x.Adapter.AddressListShowAdpter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.utils.SessionManager;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressFragment extends Fragment {

    Activity activity;
    Context context;
    ImageView but_back;
    TextView add_billingAddress;
    ShimmerRecyclerView recyle_Addresslist;
    AddressListShowAdpter addressListShowAdpter;

    ArrayList<getAddressData> getAddressDataArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        context = view.getContext();
        activity = (HomePageActivity) getActivity();
        add_billingAddress = view.findViewById(R.id.add_billingAddress);
        recyle_Addresslist = view.findViewById(R.id.recyle_addresss_view);
        but_back = view.findViewById(R.id.but_back);
        recyle_Addresslist.setLayoutManager(new GridLayoutManager(getActivity(),1), R.layout.item_shimmer_card_view);
        recyle_Addresslist.showShimmer();

        callapigetAddress();
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new MenuFragment());
            }
        });
        add_billingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetForAddAddress fragment = new BottomSheetForAddAddress();
                fragment.show((getActivity()).getSupportFragmentManager().beginTransaction(), "TAG");
            }

        });

        return view;
    }



    private void callapigetAddress() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        final Call<getAddress> addressCall = apiService.wsGetAddress("123456", body);
        addressCall.enqueue(new Callback<getAddress>() {
            @Override
            public void onResponse(Call<getAddress> call, Response<getAddress> response) {
                Log.d("getapiaddress", response.toString());

                recyle_Addresslist.hideShimmer();
                if (response.isSuccessful()){
                    getAddressDataArrayList.addAll(response.body().getData());

                    if(getAddressDataArrayList.size() > 0){
                        addressListShowAdpter = new AddressListShowAdpter(getAddressDataArrayList, getActivity(),true);
                        recyle_Addresslist.setAdapter(addressListShowAdpter);
                        addressListShowAdpter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(), "Add Address", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getAddress> call, Throwable t) {
                recyle_Addresslist.hideShimmer();
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }




}
