package com.mobile.agri10x.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.utils.SessionManager;


public class Payment_E_Collection_Fragment extends Fragment {

    ImageView mBackImage;
    Button ihavemadepayment;
    String amountstr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment__e__collection_, container, false);

        amountstr = getArguments().getString("message");
        Log.d("getamount",amountstr);
        ihavemadepayment = view.findViewById(R.id.ihavemadepayment);
        mBackImage=view.findViewById(R.id.but_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new MenuFragment());
            }
        });
        
        ihavemadepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountstr != null || !amountstr.isEmpty()){
                    double amtdouble = Double.parseDouble(amountstr);
                    String userid = SessionManager.getKeyTokenUser(getActivity());
                    callapi(amtdouble,userid);
                }

                
            }
        });
        return  view;
    }

    private void callapi(double amtdouble, String userid) {


    }

}