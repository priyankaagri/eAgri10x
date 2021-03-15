package com.mobile.agri10x.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.agri10x.Fragments.HomeFragment;
import com.mobile.agri10x.Fragments.MenuFragment;
import com.mobile.agri10x.Fragments.SeeAllLiveTradingFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

public class HomePageActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    public static FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
   public static BottomNavigationView bottomNavigationView;
    static AppCompatActivity context;
    private LiveNetworkMonitor mNetworkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mNetworkMonitor=new LiveNetworkMonitor(this);
        context=this;
        bottomNavigationView = findViewById(R.id.nav_view);
        setFragment(new HomeFragment());
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.nav_host_fragment, new HomeFragment()).commit();


        int menuItemId = bottomNavigationView.getMenu().getItem(2).getItemId();
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(menuItemId);
        badge.setBackgroundColor(getResources().getColor(R.color.appgreen));
        badge.setNumber(0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        setFragment(new HomeFragment());
                        break;
                    case R.id.tab_livetrade:
                        setFragment(new SeeAllLiveTradingFragment());
                        break;
                    case R.id.tab_cart:

                        break;
                    case R.id.tab_menu:
                        setFragment(new MenuFragment());
                        break;
                }
                return true;
            }
        });
    }
    public static void  setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
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
//onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.e("Log count",count+"");
        if (count == 1) {
            open();
        } else {
            if(mNetworkMonitor.isConnected()){
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
            getSupportFragmentManager().popBackStack();
        }

    }


    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to exit from the application");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();

//Toast.makeText(MainActivity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}