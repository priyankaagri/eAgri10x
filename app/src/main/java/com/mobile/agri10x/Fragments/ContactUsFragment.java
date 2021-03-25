package com.mobile.agri10x.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

public class ContactUsFragment extends Fragment {
    private ImageView mBackButton;
    private TextView mTxtCallUs, mTxtMailUs;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addStockView = inflater.inflate(R.layout.fragment_contact_us_menu_layout, container, false);
        init(addStockView);
        return addStockView;
    }


    private void init(View view) {
        mContext = getActivity();
        mBackButton = view.findViewById(R.id.btn_back_contactus_menu_id);
        mTxtCallUs = view.findViewById(R.id.txt_call_us_id);
        mTxtMailUs = view.findViewById(R.id.txt_mail_us_id);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });

        mTxtCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUs();
            }
        });

        mTxtMailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] email = new String[]{"info@agri10x.com"};
                mailUs("Support Subject", email, "Mail to support");
            }
        });
    }

    private void mailUs(String subject, String[] mail, String message) {
        Intent email = new Intent(Intent.ACTION_VIEW);
        email.setData(Uri.parse("mailto:")); // only email apps should handle this
        email.putExtra(Intent.EXTRA_EMAIL, mail);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }


    private void callUs() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:18001212243"));
        startActivity(intent);
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new ContactUsFragment());
    }
}
