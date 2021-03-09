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
    public static final String PREF_NAME ="appname";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)

    public static final String KEY_TOKEN = "token";


    // Constructor
    public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String token)
    {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_TOKEN, token);

// editor.putString(KEY_HOTEL_NAME, hotel_name);

// commit changes
        editor.commit();
    }

    public static boolean isLoggedIn(Context context)
    {
        return new SessionManager(context).pref.getBoolean(SessionManager.IS_LOGIN,false);
    }


}