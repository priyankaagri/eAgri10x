package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mobile.agri10x.Fragments.HomeFragment;
import com.mobile.agri10x.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomePageActivity extends AppCompatActivity {

    public static FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    AnimatedBottomBar animatedBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        animatedBottomBar = findViewById(R.id.bottom_bar);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.nav_host_fragment, new HomeFragment()).commit();

    }
}