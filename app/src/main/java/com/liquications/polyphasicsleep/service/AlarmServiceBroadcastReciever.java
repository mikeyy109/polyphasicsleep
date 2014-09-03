package com.liquications.polyphasicsleep.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmServiceBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("AlarmServiceBroadcastReciever", "onReceive()");
		Intent serviceIntent = new Intent(context, AlarmService.class);
		context.startService(serviceIntent);
	}

}
