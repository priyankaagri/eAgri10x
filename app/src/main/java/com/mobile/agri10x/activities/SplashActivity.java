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

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SplashActivity extends AppCompatActivity {
    String currentVersion = "";
    String newVersion = null;

    private int REQUEST_CODE = 11;
    private AppUpdateManager appUpdateManager;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

// new GetVersionCode().execute();
        try {

            currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            Log.d("Current Version", "::" + currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

// Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(SplashActivity.this);


        Thread background = new Thread() {
            public void run() {

                try {
// Thread will sleep for 5 seconds
                    sleep(2 * 1000);
                    checkForAppUpdate();
// if (newVersion != null && !newVersion.isEmpty()) {
//
//
// if (newVersion.equals(currentVersion)) {
// Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
// startActivity(intent);
// }
// else {
// // checkForAppUpdate();
// AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
// alertDialog.setTitle("Time To Upgrade");
// alertDialog.setIcon(getDrawable(R.drawable.appstorelogo));
// alertDialog.setMessage("Hey there, Download Agri10x latest app version and stay updated !");
// alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
// public void onClick(DialogInterface dialog, int which) {
// try {
// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
// } catch (android.content.ActivityNotFoundException anfe) {
// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
// }
// }
// });
//
//
//
// alertDialog.show();
// }
// }


// After 5 seconds redirect to another intent


//Remove activity
// finish();

                } catch (Exception e) {

                }
            }
        };

// start thread
        background.start();


    }


// private class GetVersionCode extends AsyncTask<Void, String, String> {
//
// @Override
//
// protected String doInBackground(Void... voids) {
//
//
//
// try {
// Document document = (Document) Jsoup.connect("https://play.google.com/store/apps/details?id=" +getPackageName() + "&hl=en")
// .timeout(30000)
// .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
// .referrer("http://www.google.com")
// .get();
// if (document != null) {
// Elements element = document.getElementsContainingOwnText("Current Version");
// for (org.jsoup.nodes.Element ele : element) {
// if (ele.siblingElements() != null) {
// Elements sibElemets = ele.siblingElements();
// for (Element sibElemet : sibElemets) {
// newVersion = sibElemet.text();
// }
// }
// }
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
//
// return newVersion;
//
// }
//
//
// @Override
//
// protected void onPostExecute(String onlineVersion) {
//
// super.onPostExecute(onlineVersion);
//
// if (onlineVersion != null && !onlineVersion.isEmpty()) {
//
//
// if (onlineVersion.equals(currentVersion)) {
//
// } else {
// AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
// alertDialog.setTitle("Time To Upgrade");
// alertDialog.setIcon(getDrawable(R.drawable.appstorelogo));
// alertDialog.setMessage("Hey there, Download Agri10x latest app version and stay updated !");
// alertDialog.setCanceledOnTouchOutside(false);
// alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
// public void onClick(DialogInterface dialog, int which) {
// try {
// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
// } catch (android.content.ActivityNotFoundException anfe) {
// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
// }
// }
// });
//
//// alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//// public void onClick(DialogInterface dialog, int which) {
//// dialog.dismiss();
//// }
//// });
//
// alertDialog.show();
// }
//
// }
//
// Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
//
// }
// }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
// Log.v("Update flow " + resultCode);
// If the update is cancelled or fails,
// you can request to start the update again.
                unregisterInstallStateUpdListener();
                checkForAppUpdate();
            }
        }
    }

    private void goToInsideApp() {
        Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkForAppUpdate() {

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(@NotNull InstallState installState) {
// Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
// After the update is downloaded, show a notification
// and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
// Request the update.
/* if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

// Before starting an update, register a listener for updates.
appUpdateManager.registerListener(installStateUpdatedListener);
// Start an update.
startAppUpdateFlexible(appUpdateInfo);
} else*/ if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
// Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            } else
                goToInsideApp();
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
// The current activity making the update request.
                    this,
// Include a request code to later monitor this update request.
                    SplashActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
// The current activity making the update request.
                    this,
// Include a request code to later monitor this update request.
                    SplashActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar =
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.update_downloaded), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.restart, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.text_color));
        snackbar.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
//FLEXIBLE:
// If the update is downloaded but not installed,
// notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }

//IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
// If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }
}