package com.mobile.agri10x.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.utils.SessionManager;

public class MenuFragment extends Fragment {
    private Button mLogoutButton;
    private ImageView mBackImage;
    private RelativeLayout mProfileRl,mVerifyAccountRl,mPaymentRl,mAddStockRl,mManageStockRl,
            mYourOrdersRl,mYourWishListRl,mAboutUsRl,mConstactUsRl,mShareAppRl,mAddressRl;
    private final String PROFILE_TAG="profile",VERIFY_ACCOUNT_TAG="verify",PAYMENT_TAG="payment",ADD_STOCK_TAG="addstock",
            MANAGE_STOCK_TAG="managestock",YOUR_ORDER_TAG="yourorder",WISH_LIST_TAG="yourwishlist",ABOUT_US_TAG="about_us",
            CONTACT_US_TAG="contact_us";
    private final Context mContext=getActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_layout_menu,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        mProfileRl=view.findViewById(R.id.rl_menu_profile);
        mVerifyAccountRl=view.findViewById(R.id.rl_menu_verified_account);
        mPaymentRl=view.findViewById(R.id.rl_menu_payment);
        mAddStockRl=view.findViewById(R.id.rl_menu_add_stock);
        mManageStockRl=view.findViewById(R.id.rl_menu_manage_stock);

        mYourOrdersRl=view.findViewById(R.id.rl_menu_your_orders);
        mYourWishListRl=view.findViewById(R.id.rl_menu_your_wish_list);
        mAddressRl = view.findViewById(R.id.rl_menu_your_address);
        mAboutUsRl=view.findViewById(R.id.rl_menu_about_us);
        mConstactUsRl=view.findViewById(R.id.rl_menu_contact_us);
        mShareAppRl=view.findViewById(R.id.rl_menu_share_app);
        mBackImage=view.findViewById(R.id.but_back);
        mLogoutButton=view.findViewById(R.id.btn_menu_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                showAlertDialogButtonClicked();
            }
        });
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.removeFragment(new MenuFragment());
            }
        });
        checkLoginStatus();

        mProfileRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileFragment();
            }
        });

        mVerifyAccountRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVerifyFragment();
            }
        });


        mConstactUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContactUs();
            }
        });

        mAddressRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddressFragment();
            }
        });

        mPaymentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpaymentFragment();
            }
        });
        mShareAppRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAppLink();
            }
        });

        mAddStockRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddStockFragment();
            }
        });

        mManageStockRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManageStockFragment();
            }
        });

        mYourOrdersRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYourOrderFragment();
            }
        });

        mAboutUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutUsFragment();
            }
        });

        mYourWishListRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWishListFragment();
            }
        });

    }

    private void openAddressFragment() {
        AddressFragment addressFragment=new AddressFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, addressFragment,VERIFY_ACCOUNT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void openVerifyFragment() {
        VerifyAccountFragment verifyAccountFragment=new VerifyAccountFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, verifyAccountFragment,VERIFY_ACCOUNT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openContactUs() {
        ContactUsFragment contactUsFragment=new ContactUsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, contactUsFragment,CONTACT_US_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openpaymentFragment() {
        PaymentFragment paymentFragment=new PaymentFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, paymentFragment,PAYMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void openAddStockFragment() {
        AddStockFragment addStockFragment=new AddStockFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, addStockFragment,ADD_STOCK_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openManageStockFragment() {
        ManageStockFragment manageStockFragment=new ManageStockFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, manageStockFragment,MANAGE_STOCK_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openYourOrderFragment() {
        YourOrderFragment yourOrderFragment=new YourOrderFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, yourOrderFragment,YOUR_ORDER_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openWishListFragment() {
        YourWishListFragment yourOrderFragment=new YourWishListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, yourOrderFragment,WISH_LIST_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openAboutUsFragment() {
        AboutUsFragment aboutUsFragment=new AboutUsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, aboutUsFragment,ABOUT_US_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void makeToast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

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

    private void shareAppLink(){

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "www.agri10x.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void openProfileFragment() {
        ProfileFargment profileFargment=new ProfileFargment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, profileFargment,PROFILE_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
