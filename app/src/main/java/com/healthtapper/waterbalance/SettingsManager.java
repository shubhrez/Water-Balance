package com.healthtapper.waterbalance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsManager {

	private static final String PREF_NAME = "HealthTapperSettings";

	public static Integer weight;

	private static String WEIGHT_KEY = "weightKey";

	public static Integer load(Context context) {
		SharedPreferences pref = getSharedPrefs(context);
		Integer weight = pref.getInt(WEIGHT_KEY, 0);
		return weight;
	}

	private static SharedPreferences getSharedPrefs(Context context) {
		return context.getSharedPreferences(PREF_NAME,
				Context.MODE_MULTI_PROCESS);
	}

	public static void save(Context context, Integer weight) {
		Editor editor = getSharedPrefs(context).edit();
		editor.putInt(WEIGHT_KEY, weight);
		editor.commit();
		return;
	}
}
