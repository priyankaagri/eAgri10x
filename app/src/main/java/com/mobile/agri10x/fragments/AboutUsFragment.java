package com.mobile.agri10x.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

public class AboutUsFragment extends Fragment {
    private ImageView mBackButton;
    private RelativeLayout mFaqRl,mPrivacyPolicyRl,mRefundPolicyRl;
    private final String FAQ_TAG="faq",PRIVACY_POLICY_TAG="privacy_policy",REFUND_POLICY_TAG="refund_policy";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView=inflater.inflate(R.layout.fragment_about_us_menu_layout,container,false);
        init(addStockView);
        return addStockView;
    }
    private void init(View view) {
        mBackButton=view.findViewById(R.id.btn_back_about_us_id);
        mRefundPolicyRl=view.findViewById(R.id.rl_refund_policy_id);
        mFaqRl=view.findViewById(R.id.rl_faq);
        mPrivacyPolicyRl=view.findViewById(R.id.rl_privacy_policy_id);

        mRefundPolicyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRefundFragment();
            }
        });

        mFaqRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFaqFragment();
            }
        });

        mPrivacyPolicyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyPolicyFragment();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void privacyPolicyFragment() {
        PrivacyPolicyFragment privacyPolicyFragment=new PrivacyPolicyFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, privacyPolicyFragment,PRIVACY_POLICY_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void callFaqFragment() {
        FaqFragment faqFragment=new FaqFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, faqFragment,FAQ_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void callRefundFragment() {
        RefundPolicyFragment refundPolicyFragment=new RefundPolicyFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, refundPolicyFragment,REFUND_POLICY_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new AboutUsFragment());
    }

}
