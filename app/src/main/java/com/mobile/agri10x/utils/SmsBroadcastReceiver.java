package com.mobile.agri10x.utils;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
public class SmsBroadcastReceiver extends BroadcastReceiver{

 public SmsBroadcastReceiverListener smsBroadcastReceiverListener;

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(SmsRetriever.SMS_RETRIEVED_ACTION) ) {
      Bundle extras = intent.getExtras();
      Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
      switch (smsRetrieverStatus.getStatusCode()) {
        case CommonStatusCodes.SUCCESS:
          Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
          try {
              smsBroadcastReceiverListener.onSuccess(messageIntent);
          }catch (ActivityNotFoundException e){
             smsBroadcastReceiverListener.onFailure("Something went wrong");
          }

          break;
        case CommonStatusCodes.TIMEOUT:
          smsBroadcastReceiverListener.onFailure("Something went wrong");
          break;
      }
    }
  }

  public interface SmsBroadcastReceiverListener {
    void onSuccess(Intent intent);

    void onFailure(String string);
  }
}
