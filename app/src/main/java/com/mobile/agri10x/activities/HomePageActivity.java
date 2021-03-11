package com.mobile.agri10x.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.agri10x.Fragments.HomeFragment;
import com.mobile.agri10x.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomePageActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    public static FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
   public static BottomNavigationView animatedBottomBar;
    static AppCompatActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        context=this;
        animatedBottomBar = findViewById(R.id.bottomNavigationView);
        setFragment(new HomeFragment());
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.nav_host_fragment, new HomeFragment()).commit();


        animatedBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.tab_home)
                {
                    setFragment(new HomeFragment());
                }else if(item.getItemId()==R.id.tab_paymet)
                {

                }else if(item.getItemId()==R.id.tab_cart)
                {

                }else if(item.getItemId()==R.id.tab_offer)
                {

                }else if(item.getItemId()==R.id.tab_menu)
                {

                }
                return true;
            }
        });
    }
    public static void  setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();


    }
    public static  void removeFragment(Fragment fragment){
        FragmentManager manager =(FragmentManager)context.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
           finish();
//additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}