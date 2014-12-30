package com.healthtapper.waterbalance;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthtapper.waterbalance.R;
import com.inmobi.monetization.IMBanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SQLView extends Activity implements OnItemClickListener{

	TextView tv;
	public static final String KEY_DATE = "Date";
	public static final String KEY_WEIGHT = "Weight";
	public static final String KEY_INTAKE = "WaterIntake";
	public static final String KEY_TARGET = "WaterTarget";
	private static final String TAG_DATE = "date";
	private static final String TAG_WEIGHT = "weight";
	private static final String TAG_INTAKE = "intake";
	private static final String TAG_TARGET = "target";
	

	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets_drinklog;
	private ActionBarDrawerToggle drawerListener;

	ListView list;
	TextView date;
	TextView weight;
	TextView intake;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    TextView dateText,intakevstargetText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
		ActionBar bar = getActionBar();
        String title = getResources().getString(R.string.titleDrinkLog);
        bar.setTitle(title);
		//bar.setTitle(Html.fromHtml("<font color='#000000'>Drink Log</font>"));
		bar.setIcon(R.drawable.icon);
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B2FF")));
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Dosis-SemiBold.ttf");
		date = (TextView) findViewById(R.id.date);
		weight = (TextView) findViewById(R.id.weight);
		intake = (TextView) findViewById(R.id.intake);
        dateText = (TextView) findViewById(R.id.datetext);
        intakevstargetText = (TextView) findViewById(R.id.intakevstargettext);
        dateText.setTypeface(custom_font);
        intakevstargetText.setTypeface(custom_font);
		list = (ListView) findViewById(R.id.list);
		TextView emptyText = (TextView) findViewById(R.id.empty);
		list.setEmptyView(emptyText);
		oslist = new ArrayList<HashMap<String, String>>();
		String[] columns = new String[] { KEY_DATE, KEY_WEIGHT, KEY_INTAKE , KEY_TARGET };
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
		for (c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {
			String date = c.getString(iDate);
			//String weight = "Weight- " + c.getString(iWeight) + " KG";
			String weight = "Water Intake/Target";
            String intake = c.getString(iIntake) + "/" + c.getString(iTarget) + " ML"  ;
			//String target = "Water Target- " + c.getString(iTarget) + " ML";
			// Adding value HashMap key => value
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(TAG_DATE, date);
			//map.put(TAG_WEIGHT, weight);
			map.put(TAG_INTAKE, intake);
		//	map.put(TAG_TARGET, target);
			oslist.add(map);
			list = (ListView) findViewById(R.id.list);
			ListAdapter adapter = new SimpleAdapter(SQLView.this, oslist,
					R.layout.list_v, new String[] { TAG_DATE,
							TAG_INTAKE}, new int[] { R.id.date,R.id.intake
							});
			list.setAdapter(adapter);
			// WaterIntake info = new WaterIntake(this);
			// info.open();

			// String data = info.getData();
			info.close();
			// tv.setText(data);
			// tv.setTextSize(15);
		}

		planets_drinklog = getResources().getStringArray(R.array.planets_drinklog);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.navigation_bar, planets_drinklog));
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.drawer_close,
				R.string.drawer_open);
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { MenuInflater
	 * inflater = getMenuInflater(); inflater.inflate(R.menu.drink_log_menu,
	 * menu); ActionBar bar = getActionBar(); bar.setTitle("Drink Log");
	 * bar.setBackgroundDrawable(new
	 * ColorDrawable(Color.parseColor("#00B2FF"))); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { // action with ID action_refresh was selected case
	 * R.id.home: Intent main = new
	 * Intent("com.example.waterbalance.MAINACTIVITY"); startActivity(main);
	 * break; case R.id.about:
	 * 
	 * break; case R.id.benefits:
	 * 
	 * break; } return true; }
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		finish();
//	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, planets[arg2] + "selectd",
		// Toast.LENGTH_SHORT).show();

		if (arg2 == 0) { // Home
			Intent i = new Intent("com.healthtapper.waterbalance.MAINACTIVITY");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 1) {  //Statistics
			Intent i = new Intent("com.healthtapper.waterbalance.STATISTICS");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 2) { // About
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