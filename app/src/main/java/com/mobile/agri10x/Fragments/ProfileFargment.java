package com.mobile.agri10x.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobile.agri10x.R;


public class ProfileFargment extends Fragment {

    EditText et_firstname,et_lastname,et_email,et_mobilenumber;
    Button save_btn;

    public ProfileFargment() {
        // Required empty public constructor
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
        View view= inflater.inflate(R.layout.fragment_profile_fargment, container, false);
        et_firstname=view.findViewById(R.id.et_firstname);
        et_lastname=view.findViewById(R.id.et_lastname);
        et_email=view.findViewById(R.id.et_email);
        et_mobilenumber=view.findViewById(R.id.et_mobilenumber);
        save_btn=view.findViewById(R.id.save_btn);
        return view;
    }
}