package com.mobile.agri10x.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.agri10x.Fragments.Cart_Fragment;
import com.mobile.agri10x.Fragments.HomeFragment;
import com.mobile.agri10x.Fragments.MenuFragment;
import com.mobile.agri10x.Fragments.SeeAllLiveTradingFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetProductsInCartData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
  public static   List<GetProductsInCartData> ProductsInCartlist = new ArrayList<>();
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

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment,"home");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentByTag("home");
                if (currentFragment != null && (currentFragment instanceof HomeFragment)) {
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);

                } else {

                }
            }
        });
        if(SessionManager.isLoggedIn(HomePageActivity.this)){
            getProductinCart();
        }


// mFragmentManager = getSupportFragmentManager();
// mFragmentTransaction = mFragmentManager.beginTransaction();
// mFragmentTransaction.replace(R.id.nav_host_fragment, new HomeFragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab_home:

                        int id = item.getItemId();


                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        if (id == R.id.tab_home && !(f instanceof HomeFragment)) {
                            HomeFragment fragment = new HomeFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, fragment, "home");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onBackStackChanged() {
                                    FragmentManager fragmentManager = (FragmentManager) context.getSupportFragmentManager();
                                    Fragment currentFragment = fragmentManager.findFragmentByTag("home");
                                    if (currentFragment != null && (currentFragment instanceof HomeFragment)) {
                                        bottomNavigationView.getMenu().findItem(id).setChecked(true);

                                    } else {

                                    }
                                }
                            });
                        }

                        break;
                    case R.id.tab_livetrade:

                        int id1 = item.getItemId();

                        Fragment f2 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        if (id1 == R.id.tab_livetrade && !(f2 instanceof SeeAllLiveTradingFragment)) {
                            SeeAllLiveTradingFragment fragment = new SeeAllLiveTradingFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, fragment,"livetrade");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onBackStackChanged() {
                                    FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
                                    Fragment currentFragment = fragmentManager.findFragmentByTag("livetrade");
                                    if (currentFragment != null && (currentFragment instanceof SeeAllLiveTradingFragment)) {
                                        bottomNavigationView.getMenu().findItem(id1).setChecked(true);

                                    } else {

                                    }
                                }
                            });
                        }
//setFragment(new SeeAllLiveTradingFragment(),"see");

                        break;
                    case R.id.tab_cart:
                        if(SessionManager.isLoggedIn(HomePageActivity.this)){
                            getProductinCart();
                            int id2 = item.getItemId();
                            Log.d("hvghv", String.valueOf(id2));

                            Fragment f6 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            if (id2 == R.id.tab_cart && !(f6 instanceof Cart_Fragment)) {
                                Cart_Fragment fragment = new Cart_Fragment();
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment,"cart");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onBackStackChanged() {
                                        FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
                                        Fragment currentFragment = fragmentManager.findFragmentByTag("cart");
                                        if (currentFragment != null && (currentFragment instanceof Cart_Fragment)) {
                                            bottomNavigationView.getMenu().findItem(id2).setChecked(true);

                                        } else {

                                        }
                                    }
                                });
                            }
                        }
                        else {
                            startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
                        }

                        break;
                    case R.id.tab_menu:
                        getProductinCart();
                        int id3 = item.getItemId();
                        Fragment f4 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        if (id3 == R.id.tab_menu && !(f4 instanceof MenuFragment)) {
                            MenuFragment fragment = new MenuFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, fragment,"menu");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onBackStackChanged() {
                                    FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
                                    Fragment currentFragment = fragmentManager.findFragmentByTag("menu");
                                    if (currentFragment != null && (currentFragment instanceof MenuFragment)) {
                                        bottomNavigationView.getMenu().findItem(id3).setChecked(true);

                                    } else {

                                    }
                                }
                            });
                        }

                        break;
                }
                return true;
            }
        });

    }

    public static void getProductinCart() {
        ProductsInCartlist.clear();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(context));
        Log.d("getuserid",SessionManager.getKeyTokenUser(context));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final Call<GetProductsInCart> ProductinCart = apiService.wsgetProductinCart("123456",
                body);
        ProductinCart.enqueue(new Callback<GetProductsInCart>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetProductsInCart> call,
                                   Response<GetProductsInCart> response) {

                Log.d("ProductinCart",response.toString());
                if (response.isSuccessful()) {
                    ProductsInCartlist.addAll(response.body().getData());
                    int menuItemId = bottomNavigationView.getMenu().getItem(2).getItemId();
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(menuItemId);
                    badge.setBackgroundColor(context.getResources().getColor(R.color.appgreen));
                    badge.setNumber(ProductsInCartlist.size());

                }
                else {
                    Toast.makeText(context,"Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetProductsInCart> call,
                                  Throwable t) {
                Toast.makeText(context,"Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setFragment(Fragment fragment,String tag)
    {
        FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment,tag);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();


    }

    public static void removeFragment(Fragment fragment){
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