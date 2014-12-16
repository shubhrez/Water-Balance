package com.healthtapper.waterbalance;

import java.util.Calendar;
import java.util.Date;

import com.healthtapper.waterbalance.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class NotificationDisplayer extends BroadcastReceiver {

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("NotificationDiaplyer", "Received broadcast " + (new Date()));

		Calendar calendar = Calendar.getInstance();
		boolean isInTimeInterval = (calendar.get(Calendar.HOUR_OF_DAY) < AlarmNotificationManager.END_HOUR_OF_DAY)
				&& (calendar.get(Calendar.HOUR_OF_DAY) >= AlarmNotificationManager.START_HOUR_OF_DAY);

		if (isInTimeInterval) {
			NotificationManager notifictionManager = (NotificationManager)context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					new Intent(context, MainActivity.class), 0);
			Notification notification = new Notification.Builder(context)
					.setTicker("Notification")
					.setSmallIcon(R.drawable.notifyicon)
					.setContentTitle("Reminder to Drink Water")
					.setContentIntent(contentIntent)
					.setAutoCancel(true)
					.setWhen(System.currentTimeMillis())
					.setContentText("Time to wet your body").build();
			notification.defaults |= Notification.DEFAULT_SOUND;
			notifictionManager.notify(1, notification);
		}
	}
}
