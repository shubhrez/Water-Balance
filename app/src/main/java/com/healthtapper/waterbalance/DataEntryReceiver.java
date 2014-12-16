package com.healthtapper.waterbalance;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataEntryReceiver {
	public static final int DATA_ENTRY_HOUR_OF_DAY = 0; // start at 8 AM
	public static final long INTERVAL = 24*60*60*1000; // five minutes in milli-secs

	private Context context;

	DataEntryReceiver(Context context) {
		this.context = context;
		setData();
	}

	void setData() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, DATA_ENTRY_HOUR_OF_DAY);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent1 = new Intent(context, DataEntry.class);
		boolean alarmUp = (PendingIntent.getBroadcast(context, 1, 
		        intent1, PendingIntent.FLAG_NO_CREATE) != null);
		if(alarmUp){
			Log.d("myTag", "Data not set");
		}else{
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,
					intent1, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis() + INTERVAL, INTERVAL, pendingIntent);
				}
	}
}
