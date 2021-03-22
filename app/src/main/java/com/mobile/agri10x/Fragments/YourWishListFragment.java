package com.mobile.agri10x.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

public class YourWishListFragment extends Fragment {
    private ImageView mBackButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView=inflater.inflate(R.layout.fragment_your_wish_menu_layout,container,false);
        init(addStockView);
        return addStockView;
    }


    private void init(View view) {
        mBackButton=view.findViewById(R.id.btn_back_wishmenu_id);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new YourWishListFragment());
    }
}
