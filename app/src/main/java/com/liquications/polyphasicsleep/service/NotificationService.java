package com.liquications.polyphasicsleep.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.liquications.polyphasicsleep.AlarmActivity;
import com.liquications.polyphasicsleep.R;

public class NotificationService extends Service {

	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		// Getting Notification Service
		mManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(
						this.getApplicationContext().NOTIFICATION_SERVICE);
		/*
		 * When the user taps the notification we have to show the Home Screen
		 * of our App, this job can be done with the help of the following
		 * Intent.
		 */
		Intent intent1 = new Intent(this.getApplicationContext(), AlarmActivity.class);

		Notification notification = new Notification(R.drawable.ic_launcher,
				"Polyphasic Sleep Manager:", System.currentTimeMillis());

		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				this.getApplicationContext(), 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notification.setLatestEventInfo(this.getApplicationContext(),
				"NAP", "5 Mins to start nap.",
				pendingNotificationIntent);

		mManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}