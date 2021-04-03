package com.mobile.agri10x.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;


public class Trade_Value_Total_Fragment extends Fragment {
TextView bookorder_btn,checkout_btne,totaltradeamt,txt_commition,txt_handline_fee,txt_convenience_charge,txt_trade_amt;
ImageView but_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trade__value__total_, container, false);
        bookorder_btn=view.findViewById(R.id.bookorder_btn);
        checkout_btne=view.findViewById(R.id.checkout_btne);
        totaltradeamt=view.findViewById(R.id.totaltradeamt);
        txt_commition=view.findViewById(R.id.txt_commition);
        txt_handline_fee=view.findViewById(R.id.txt_handline_fee);
        txt_convenience_charge=view.findViewById(R.id.txt_convenience_charge);
        txt_trade_amt=view.findViewById(R.id.txt_trade_amt);
        but_back=view.findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new Trade_Value_Total_Fragment());
            }
        });
  return view;
    }
}