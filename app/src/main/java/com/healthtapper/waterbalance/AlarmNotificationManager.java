package com.healthtapper.waterbalance;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class AlarmNotificationManager {

	public static final int START_HOUR_OF_DAY = 7; // start at 8 AM
	public static final int END_HOUR_OF_DAY = 22; // end at 9 PM
	public static final long INTERVAL = 90*60*1000; // five minutes in milli-secs
	
	private Context context;

	AlarmNotificationManager(Context context) {
		this.context = context;
		setAlarm();
	}

		void setAlarm() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, START_HOUR_OF_DAY);
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotificationDisplayer.class);
		
		boolean alarmUp = (PendingIntent.getBroadcast(context, 0, 
		        intent, PendingIntent.FLAG_NO_CREATE) != null);
		if(alarmUp){
			Log.d("myTag", "Alarm is already active");
		}else{
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
			//	calendar.getTimeInMillis(), INTERVAL, pendingIntent);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		System.currentTimeMillis() + INTERVAL, INTERVAL, pendingIntent);
		}
	}
}
