package com.mobile.agri10x.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    // Shared Preferences
    public static SharedPreferences pref;

    public static Context mainactivity;

    // Editor for Shared preferences
    public static SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "appname";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)

    public static final String KEY_TOKEN = "token";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_IAT = "iat";
    public static final String KEY_ROLE = "role";
    public static final String KEY_EXP = "exp";
    public static final String KEY_TOKEN_USER = "token_userid";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String token) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_TOKEN, token);

// editor.putString(KEY_HOTEL_NAME, hotel_name);

// commit changes
        editor.commit();
    }

    public static void addUserDetails(String mobile, String iat, String role, String exp,String tokenuserid) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_IAT, iat);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_EXP, exp);
        editor.putString(KEY_TOKEN_USER,tokenuserid);
        editor.commit();
    }


    public static String getRolePref(Context context) {
        return new SessionManager(context).pref.getString(SessionManager.KEY_ROLE, "NULL");
    }

    public static String getmobilePref(Context context) {
        return new SessionManager(context).pref.getString(SessionManager.KEY_MOBILE, "NULL");
    }
    public static String getKeyTokenUser(Context context) {
        return new SessionManager(context).pref.getString(SessionManager.KEY_TOKEN_USER, "NULL");
    }
    public static String getIatPref(Context context) {
        return new SessionManager(context).pref.getString(SessionManager.KEY_IAT, "NULL");
    }

    public static String getExpPref(Context context) {
        return new SessionManager(context).pref.getString(SessionManager.KEY_EXP, "NULL");
    }

    public static void clearPrefrence(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean isLoggedIn(Context context) {
        return new SessionManager(context).pref.getBoolean(SessionManager.IS_LOGIN, false);
    }


}