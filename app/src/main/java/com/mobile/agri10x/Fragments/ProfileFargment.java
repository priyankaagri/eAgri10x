package com.mobile.agri10x.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;

import java.util.regex.Pattern;


public class ProfileFargment  extends Fragment {

    EditText et_firstname, et_lastname, et_email, et_mobilenumber;
    Button save_btn;
    ImageView mBackButton;
    private TextView mFirstNameErrTV,mLastNameErrTV,mEmailErrTV;
    public ProfileFargment() {
        // Required empty public constructor
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new ProfileFargment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fargment, container, false);
        et_firstname = view.findViewById(R.id.et_firstname);
        et_lastname = view.findViewById(R.id.et_lastname);
        et_email = view.findViewById(R.id.et_email);
        et_mobilenumber = view.findViewById(R.id.et_mobilenumber);
        save_btn = view.findViewById(R.id.save_btn);
        mEmailErrTV=view.findViewById(R.id.tv_email_error_id);
        mLastNameErrTV=view.findViewById(R.id.tv_last_error_id);
        mFirstNameErrTV=view.findViewById(R.id.tv_first_error_id);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileData();
            }
        });

        mBackButton = view.findViewById(R.id.btn_back_profile_id);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });

        return view;
    }

    private void saveProfileData() {
        Log.e("DEBUG: if", "Validation -> " + verifyProfileData());

        if (verifyProfileData()) {
            Log.e("DEBUG: if", "Validation Done");
        } else {
            Log.e("DEBUG: else", "Validation Done");

        }
    }

    private boolean verifyProfileData() {
        String firstName = et_firstname.getText().toString().trim();
        String lastName = et_lastname.getText().toString().trim();
        String mobileNo = et_mobilenumber.getText().toString().trim();
        String emailID = et_email.getText().toString().trim();

        Log.e("DEBUG: Verifyprofile", "\nisNullOrEmpty(firstName) " + isNullOrEmpty(firstName) +
                "\nisStringOnlyAlphabet(firstName)" + isStringOnlyAlphabet(firstName) +
                "\nisValidEmail(emailID)" + isValidEmail(emailID));

        if (isNullOrEmpty(firstName)) {
            mFirstNameErrTV.setVisibility(View.VISIBLE);
            return false;
        }

        if (!isStringOnlyAlphabet(firstName)) {
            mFirstNameErrTV.setVisibility(View.VISIBLE);
            return false;
        }
        mFirstNameErrTV.setVisibility(View.GONE);

        if (isNullOrEmpty(lastName) ) {
            mLastNameErrTV.setVisibility(View.VISIBLE);
            return false;
        }
        if (!isStringOnlyAlphabet(lastName) ) {
            mLastNameErrTV.setVisibility(View.VISIBLE);
            return false;
        }


        if (isNullOrEmpty(mobileNo)) {
            return false;
        }
        mLastNameErrTV.setVisibility(View.GONE);

        if (!isValidEmail(emailID)) {
            mEmailErrTV.setVisibility(View.VISIBLE);
            return false;
        }
        mEmailErrTV.setVisibility(View.GONE);

        return true;
    }

    private boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean isStringOnlyAlphabet(String str) {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }


}