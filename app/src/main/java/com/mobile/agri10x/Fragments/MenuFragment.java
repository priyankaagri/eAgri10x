package com.mobile.agri10x.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.utils.SessionManager;

public class MenuFragment extends Fragment {
    private Button mLogoutButton;
    private ImageView mBackImage;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_layout_menu,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        mLogoutButton=view.findViewById(R.id.btn_menu_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked();
            }
        });
        mBackImage=view.findViewById(R.id.menuButtonBack);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new MenuFragment());
            }
        });
        checkLoginStatus();

    }

    private void checkLoginStatus() {
        if(SessionManager.isLoggedIn(getActivity().getApplicationContext()))
        {
            mLogoutButton.setVisibility(View.VISIBLE);
        }else{
            mLogoutButton.setVisibility(View.INVISIBLE);
        }

    }

    private void showAlertDialogButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage(" Are you sure you want to Logout?");
        // add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                logout();
            }

            private void logout() {
                SessionManager.clearPrefrence(getActivity());
                HomePageActivity.removeFragment(new MenuFragment());
            }
        });

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
