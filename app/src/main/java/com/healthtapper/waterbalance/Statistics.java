package com.healthtapper.waterbalance;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Statistics extends Activity implements OnItemClickListener {

	public static final String KEY_DATE = "Date";
	public static final String KEY_WEIGHT = "Weight";
	public static final String KEY_INTAKE = "WaterIntake";
	public static final String KEY_TARGET = "WaterTarget";
	private static final String TAG_DATE = "date";
	private static final String TAG_WEIGHT = "weight";
	private static final String TAG_INTAKE = "intake";
	private static final String TAG_TARGET = "target";
	TextView avIntake, avTarget, currentWeight, idealWeight;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets_statistics;
	private ActionBarDrawerToggle drawerListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
		ActionBar bar = getActionBar();
		bar.setTitle(Html.fromHtml("<font color='#000000'>Statistics</font>"));
		// bar.setTitle("About");
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B2FF")));

		avIntake = (TextView) findViewById(R.id.avIntake);
		avTarget = (TextView) findViewById(R.id.avTarget);
		currentWeight = (TextView) findViewById(R.id.currentWeight);
		idealWeight = (TextView) findViewById(R.id.idealWeight);

		String[] columns = new String[] { KEY_DATE, KEY_WEIGHT, KEY_INTAKE,
				KEY_TARGET };
		WaterIntake info = new WaterIntake(this);
		info.open();

		Cursor c = info.ourDatabase.query(info.DATABASE_TABLE, columns, null,
				null, null, null, null);
		// String result =" ";
		int iDate = c.getColumnIndex(KEY_DATE);
		int iWeight = c.getColumnIndex(KEY_WEIGHT);
		int iIntake = c.getColumnIndex(KEY_INTAKE);
		int iTarget = c.getColumnIndex(KEY_TARGET);
		// for (c.moveToFirst() c.moveToLast(); !c.isAfterLast();
		// c.moveToNext()) {
		int totalEntries = c.getCount();
		int totalIntake = 0;
		int totalTarget = 0;
		
		if (totalEntries != 0) {
			for (c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {
				String date = c.getString(iDate);
				String weight = c.getString(iWeight);
				String intake = c.getString(iIntake);
				String target = c.getString(iTarget);
				totalIntake += Integer.parseInt(intake);
				totalTarget += Integer.parseInt(target);
				//currentweight = Integer.parseInt(weight);
			}
		int	currentweight = MainActivity.returnWeight(this);
		int avintake = totalIntake / totalEntries;
		int avtarget = totalTarget / totalEntries;
		int ht = MainActivity.returnHeight(this);
		int wt1 = (ht*ht)*(int)19.5/10000;
		int wt2 = (ht*ht)*(int)23.5/10000;
		String avintake1 = Integer.toString(avintake) + " ML";
		String avtarget1 = Integer.toString(avtarget) + " ML";
		String currentweight1 = Integer.toString(currentweight) + " KG";
		String weightRange = String.valueOf(wt1) + "-" + String.valueOf(wt2)+ " KG";
		avIntake.setText(avintake1);
		avTarget.setText(avtarget1);
		currentWeight.setText(currentweight1);
		idealWeight.setText(weightRange);
		}
		
		
		planets_statistics = getResources().getStringArray(
				R.array.planets_statistics);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.navigation_bar, planets_statistics));
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.drawer_close,
				R.string.drawer_open);
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, planets[arg2] + "selectd",
		// Toast.LENGTH_SHORT).show();

		if (arg2 == 0) { // Home
			Intent i = new Intent("com.healthtapper.waterbalance.MAINACTIVITY");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		} else if (arg2 == 1) { // Drink Log
			Intent i = new Intent("com.healthtapper.waterbalance.SQLVIEW");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		} else if (arg2 == 2) { // About
			Intent i = new Intent("com.healthtapper.waterbalance.ABOUT");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		} else if (arg2 == 3) { // Feedback
			Intent i = new Intent("com.healthtapper.waterbalance.FEEDBACK");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		return true;
	}

}