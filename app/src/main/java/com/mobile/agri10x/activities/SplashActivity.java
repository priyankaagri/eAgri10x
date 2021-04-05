package com.mobile.agri10x.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.mobile.agri10x.R;
import com.mobile.agri10x.utils.LiveNetworkMonitor;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SplashActivity extends AppCompatActivity {
    String currentVersion="";
    String newVersion = "";
    private LiveNetworkMonitor mNetworkMonitor;
    private int REQUEST_CODE = 11;
    private AppUpdateManager appUpdateManager;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private InstallStateUpdatedListener installStateUpdatedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mNetworkMonitor=new LiveNetworkMonitor(SplashActivity.this);
        if(mNetworkMonitor.isConnected()){

            Toast.makeText(SplashActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }  else{
            new GetVersionCode().execute();
            try {

                currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Thread background = new Thread() {
                public void run() {

                    try {
// Thread will sleep for 5 seconds
                        sleep(2*1000);
                        if (newVersion != null && !newVersion.isEmpty()) {


                            if (newVersion.equals(currentVersion)) {
                                Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            }
                            else {
                                //   checkForAppUpdate();
                                AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
                                alertDialog.setTitle("Time To Upgrade");
                                alertDialog.setIcon(getDrawable(R.drawable.appstorelogo));
                                alertDialog.setMessage("Hey there, Download Agri10x latest app version and stay updated !");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                                        }
                                    }
                                });



                                alertDialog.show();
                            }
                        }



// After 5 seconds redirect to another intent


//Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

// start thread
            background.start();

        }








    }



    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {



            try {
                Document document = (Document) Jsoup.connect("https://play.google.com/store/apps/details?id=" +getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (org.jsoup.nodes.Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {


                if (onlineVersion.equals(currentVersion)) {

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
                    alertDialog.setTitle("Time To Upgrade");
                    alertDialog.setIcon(getDrawable(R.drawable.appstorelogo));
                    alertDialog.setMessage("Hey there, Download Agri10x latest app version and stay updated !");
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                    });

//                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });

                    alertDialog.show();
                }

            }

         //   Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(mNetworkMonitor.isConnected()){
            Toast.makeText(SplashActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
//
}