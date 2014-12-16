package com.healthtapper.waterbalance;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataEntry extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Log.d("NotificationDiaplyer", "Received Data " + (new Date()));
		WaterIntake entry = new WaterIntake(context);
		entry.open();
		int x = MainActivity.returnWeight(context);
		int y = MainActivity.returnWater(context);
		int z = MainActivity.returnDrinkTarget(context);
		String wt = Integer.toString(x);
		String wi = Integer.toString(y);
		String dt = Integer.toString(z);
		entry.createEntry(wt, wi,dt);
		entry.close();
		
	}
}
