package com.liquications.polyphasicsleep.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

     @Override
     public void onReceive(Context context, Intent intent) {
     // When our Alaram time is triggered , this method will be excuted (onReceive)
     // We're invoking a service in this method which shows Notification to the User
         Toast.makeText(context,"Noti received",Toast.LENGTH_SHORT);
      Intent myIntent = new Intent(context, NotificationService.class);
      context.startService(myIntent);
    }

} 