package com.mobile.agri10x.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.adapters.FaqListAdapter;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.FaqInfoModelDataInfo;

import java.util.ArrayList;

public class FaqFragment extends Fragment {
    private ImageView mBackButton;
    private RecyclerView mFaqPointRV;
    private ArrayList<FaqInfoModelDataInfo> mFaqInfoModel;
    private FaqListAdapter mFaqListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View faqView=inflater.inflate(R.layout.fragment_faq_aboutus_layout,container,false);
        init(faqView);
        return faqView;
    }
    private void init(View view) {
        mFaqInfoModel=new ArrayList<>();
        mFaqInfoModel.add(new FaqInfoModelDataInfo(getResources().getString( R.string.faq_title1),getResources().getString( R.string.faq_desc1 )));
        mFaqInfoModel.add(new FaqInfoModelDataInfo(getResources().getString( R.string.faq_title2),getResources().getString( R.string.faq_desc2 )));
        mFaqInfoModel.add(new FaqInfoModelDataInfo(getResources().getString( R.string.faq_title3),getResources().getString( R.string.faq_desc3 )));
        mFaqInfoModel.add(new FaqInfoModelDataInfo(getResources().getString( R.string.faq_title4),getResources().getString( R.string.faq_desc4 )));
        mFaqInfoModel.add(new FaqInfoModelDataInfo(getResources().getString( R.string.faq_title5),getResources().getString( R.string.faq_desc5 )));

        mBackButton=view.findViewById(R.id.btn_back_faq_id);
        mFaqPointRV=view.findViewById(R.id.rv_faq_expand);
        mFaqListAdapter=new FaqListAdapter(getActivity(),mFaqInfoModel);
        mFaqPointRV.setHasFixedSize(true);
        mFaqPointRV.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        mFaqPointRV.setAdapter(mFaqListAdapter);


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new AboutUsFragment());
    }

}
