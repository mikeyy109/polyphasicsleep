package com.liquications.polyphasicsleep.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneStateChangedBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(getClass().getSimpleName(), "onReceive()");
		
	}

}
