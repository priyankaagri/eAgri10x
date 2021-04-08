package com.mobile.agri10x.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessages {
    public static void makeToast(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
