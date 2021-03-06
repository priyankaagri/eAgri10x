package com.mobile.agri10x.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;
import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.activities.PaymentActivity;
import com.mobile.agri10x.utils.SessionManager;

public class MenuFragment extends Fragment {
    private Button mLogoutButton;
    private ImageView mBackImage;
    private RelativeLayout mProfileRl,mVerifyAccountRl,mPaymentRl,mAddStockRl,mManageStockRl,
            mYourOrdersRl,mYourWishListRl,mAboutUsRl,mConstactUsRl,mShareAppRl,mAddressRl,mMylanguage;
    private final String PROFILE_TAG="profile",VERIFY_ACCOUNT_TAG="verify",PAYMENT_TAG="payment",ADD_STOCK_TAG="addstock",
            MANAGE_STOCK_TAG="managestock",YOUR_ORDER_TAG="yourorder",WISH_LIST_TAG="yourwishlist",ABOUT_US_TAG="about_us",
            CONTACT_US_TAG="contact_us",MY_LANGUAGE="mylanguage";
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
       mMylanguage = view.findViewById(R.id.rl_menu_your_language);
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
        mMylanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyLanguageFragment();
            }
        });

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
                Intent intent=new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
//                openpaymentFragment();
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

    private void openMyLanguageFragment() {
        MyLanguageFragment addressFragment=new MyLanguageFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, addressFragment,MY_LANGUAGE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openAddressFragment() {
        MyAddressFragment addressFragment=new MyAddressFragment();
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
        MyOrderFragment yourOrderFragment=new MyOrderFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, yourOrderFragment,YOUR_ORDER_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openWishListFragment() {
        MyWishListFragment yourOrderFragment=new MyWishListFragment();
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
        builder.setTitle(R.string.logout);
        builder.setMessage(R.string.you_want_logout);
        // add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                logout();
            }

            private void logout() {
                SessionManager.clearPrefrence(getActivity());
                HomePageActivity.removeFragment(new MenuFragment());
                int menuItemId = HomePageActivity.bottomNavigationView.getMenu().getItem(2).getItemId();
                BadgeDrawable badge = HomePageActivity.bottomNavigationView.getOrCreateBadge(menuItemId);
                badge.setBackgroundColor(getActivity().getResources().getColor(R.color.appgreen));
                badge.setNumber(0);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void shareAppLink(){

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.mobile.agri10x";
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
