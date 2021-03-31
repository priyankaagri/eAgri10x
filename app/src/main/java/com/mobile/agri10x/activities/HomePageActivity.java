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
import com.mobile.agri10x.Fragments.BookOrderFragment;
import com.mobile.agri10x.Fragments.Payment_E_Collection_Fragment;
import com.mobile.agri10x.Fragments.PurchaseOrderFargment;
import com.mobile.agri10x.Fragments.TradeValueAddCart;
import com.mobile.agri10x.Fragments.HomeFragment;
import com.mobile.agri10x.Fragments.MenuFragment;
import com.mobile.agri10x.Fragments.SeeAllLiveTradingFragment;
import com.mobile.agri10x.Fragments.YourOrderFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetBookingCheckOutHandling;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.models.GetOrderCheckOutHandling;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetProductsInCartProductData;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.LiveNetworkMonitor;
import com.mobile.agri10x.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    FragmentManager fragmentManager;
  public static   List<GetProductsInCartProductData> ProductsInCartlist = new ArrayList<>();
    public static FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static BottomNavigationView bottomNavigationView;
    static AppCompatActivity context;
    private LiveNetworkMonitor mNetworkMonitor;
    String order_id="", payment_id= "",signature="", bookingid_frombookorderfrag="";
    boolean getbookorpurchase;
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
//                        if(SessionManager.isLoggedIn(HomePageActivity.this)) {

                            int id1 = item.getItemId();
                            Log.d("hvghv", String.valueOf(id1));
                            Fragment f2 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            if (id1 == R.id.tab_livetrade && !(f2 instanceof SeeAllLiveTradingFragment)) {
                                SeeAllLiveTradingFragment fragment = new SeeAllLiveTradingFragment();
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, "livetrade");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onBackStackChanged() {
                                        FragmentManager fragmentManager = (FragmentManager) context.getSupportFragmentManager();
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
                            if (id2 == R.id.tab_cart && !(f6 instanceof TradeValueAddCart)) {
                                TradeValueAddCart fragment = new TradeValueAddCart();
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
                                        if (currentFragment != null && (currentFragment instanceof TradeValueAddCart)) {
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
                        if(SessionManager.isLoggedIn(HomePageActivity.this)) {
                            getProductinCart();
                            int id3 = item.getItemId();
                            Fragment f4 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            if (id3 == R.id.tab_menu && !(f4 instanceof MenuFragment)) {
                                MenuFragment fragment = new MenuFragment();
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, "menu");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onBackStackChanged() {
                                        FragmentManager fragmentManager = (FragmentManager) context.getSupportFragmentManager();
                                        Fragment currentFragment = fragmentManager.findFragmentByTag("menu");
                                        if (currentFragment != null && (currentFragment instanceof MenuFragment)) {
                                            bottomNavigationView.getMenu().findItem(id3).setChecked(true);

                                        } else {

                                        }
                                    }
                                });
                            }
                        } else {
                            startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
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
                    ProductsInCartlist.addAll(response.body().getProducts());
                    int menuItemId = bottomNavigationView.getMenu().getItem(2).getItemId();
                    FragmentManager fragmentManager = (FragmentManager)context.getSupportFragmentManager();
                    Fragment currentFragment = fragmentManager.findFragmentByTag("cart");
                    if (currentFragment != null && (currentFragment instanceof TradeValueAddCart)) {
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);

                    } else {

                    }
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
      public  void startpayment(String razorpay_id, double amount, String key,String bookingid,boolean isbooking) {

          final HomePageActivity activity= this;

          final Checkout co = new Checkout();
          co.setKeyID(key);

          try {
              JSONObject options = new JSONObject();
              options.put("name", "Agri10x");
              options.put("description", "(ICognitive Global Pvt Ltd)");
//You can omit the image option to fetch the image from dashboard
              options.put("image", "https://data.agri10x.com/images/Icognitive%20logo2.png");
              options.put("currency", "INR");
              options.put("theme.color", "#5FA30F");
              options.put("order_id", razorpay_id);
              String payment = String.valueOf(amount);        //orderamount.getText().toString();
// amount is in paise so please multiple it by 100
//Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
              double total = Double.parseDouble(payment);
//            total = total * 100;
              options.put("amount", total);

//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "kamal.bunkar07@gmail.com");
//            preFill.put("contact", "9144040888");

//            options.put("prefill", preFill);

              co.open(activity, options);
              bookingid_frombookorderfrag = bookingid;
              getbookorpurchase = isbooking;
          } catch (Exception e) {
              Toast.makeText(HomePageActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
              e.printStackTrace();
          }
      }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        order_id = paymentData.getOrderId();
        payment_id = paymentData.getPaymentId();
        signature = paymentData.getSignature();
if(getbookorpurchase){
    callcheckouthandleforbook(order_id,payment_id,signature,bookingid_frombookorderfrag);
}else{
    callcheckouthandleforpurchase(order_id,payment_id,signature,bookingid_frombookorderfrag);
}

        Log.d("mainresponse",order_id+ " "+ payment_id+ " "+signature+" "+bookingid_frombookorderfrag);


    }




    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.d("paymenterroe",s);
    }
    private void callcheckouthandleforpurchase(String order_id, String payment_id, String signature, String bookingid_frombookorderfrag) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("razorpay_payment_id",payment_id);
        jsonParams.put("razorpay_order_id",order_id);
        jsonParams.put("razorpay_signature",signature);
        jsonParams.put("bookingID",bookingid_frombookorderfrag);
        jsonParams.put("Userid",SessionManager.getKeyTokenUser(HomePageActivity.this));

        Log.d("getparamsforpurchase",payment_id+" "+order_id+" "+signature+" "+bookingid_frombookorderfrag);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetOrderCheckOutHandling> loginCall = apiService.wsCheckOrderCheckOutHandling("123456",body);
        loginCall.enqueue(new Callback<GetOrderCheckOutHandling>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetOrderCheckOutHandling> call,
                                   Response<GetOrderCheckOutHandling> response) {

                Log.d("getnameapi",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Payment Successful")){
                        HomePageActivity.removeFragment(new PurchaseOrderFargment());
                        HomePageActivity.setFragment(new YourOrderFragment(),"youroder");
                        getProductinCart();
                        Toast.makeText(HomePageActivity.this,"Payment Successful",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(HomePageActivity.this,"Payment Failed",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(HomePageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOrderCheckOutHandling> call,
                                  Throwable t) {
                Toast.makeText(HomePageActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callcheckouthandleforbook(String order_id, String payment_id, String signature, String bookingid) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("razorpay_payment_id",payment_id);
        jsonParams.put("razorpay_order_id",order_id);
        jsonParams.put("razorpay_signature",signature);
        jsonParams.put("bookingID",bookingid);
        jsonParams.put("Userid",SessionManager.getKeyTokenUser(HomePageActivity.this));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetBookingCheckOutHandling> loginCall = apiService.wsCheckBookingCheckOutHandling("123456",body);
        loginCall.enqueue(new Callback<GetBookingCheckOutHandling>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetBookingCheckOutHandling> call,
                                   Response<GetBookingCheckOutHandling> response) {

                Log.d("getnameapi",response.toString());
                if (response.isSuccessful()) {

                    if(response.body().getMessage().equals("Payment Successful")){
                        HomePageActivity.removeFragment(new BookOrderFragment());
                        HomePageActivity.setFragment(new YourOrderFragment(),"youroder");

                        getProductinCart();
                        Toast.makeText(HomePageActivity.this,"Payment Successful",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(HomePageActivity.this,"Payment Failed",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(HomePageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBookingCheckOutHandling> call,
                                  Throwable t) {
                Toast.makeText(HomePageActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}