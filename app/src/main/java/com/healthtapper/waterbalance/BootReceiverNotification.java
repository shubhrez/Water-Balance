package com.healthtapper.waterbalance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiverNotification extends BroadcastReceiver {
	
	//private static Integer notification = 1;
	//public static final String NOTIFICATION = "notificationkey";
	//static String PREF_NAME = "HealthTapperSettings";
	//private static SharedPreferences pref;
	
	@Override
	public void onReceive(Context ctxt, Intent arg1) {
		// TODO Auto-generated method stub
		//pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
		//notification = pref.getInt(NOTIFICATION, 1);
		//if (notification != 0) {
			//new AlarmNotificationManager(ctxt);
		//}
        SharedPreferences pref = ctxt.getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_MULTI_PROCESS);
        int notification = pref.getInt(MainActivity.NOTIFICATION, 1);
        if (notification != 0) {
          new AlarmNotificationManager(ctxt);
        }
		//new AlarmNotificationManager(ctxt);
		new DataEntryReceiver(ctxt);
	}

}
