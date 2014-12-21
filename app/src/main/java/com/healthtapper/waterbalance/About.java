package com.healthtapper.waterbalance;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class About extends Activity implements OnItemClickListener{

	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets_about;
	private ActionBarDrawerToggle drawerListener;
    private TextView information,tip1,tip2,tip3,tip4,tip5,tip6,tip7;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActionBar bar = getActionBar();
        String title = getResources().getString(R.string.titleAbout);
        bar.setTitle(title);
		//bar.setTitle(Html.fromHtml("<font color='#000000'>About</font>"));
		//bar.setTitle("About");
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B2FF")));
		bar.setIcon(R.drawable.icon);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Medium.ttf");
        information = (TextView) findViewById(R.id.information);
        information.setTypeface(custom_font);
        tip1 = (TextView) findViewById(R.id.tip1);
        tip2 = (TextView) findViewById(R.id.tip2);
        tip3 = (TextView) findViewById(R.id.tip3);
        tip4 = (TextView) findViewById(R.id.tip4);
        tip5 = (TextView) findViewById(R.id.tip5);
        tip6 = (TextView) findViewById(R.id.tip6);
        tip7 = (TextView) findViewById(R.id.tip7);
        tip1.setTypeface(custom_font);
        tip2.setTypeface(custom_font);
        tip3.setTypeface(custom_font);
        tip4.setTypeface(custom_font);
        tip5.setTypeface(custom_font);
        tip6.setTypeface(custom_font);
        tip7.setTypeface(custom_font);

		planets_about = getResources().getStringArray(R.array.planets_about);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.navigation_bar, planets_about));
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.drawer_close,
				R.string.drawer_open);
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	protected void onPause() {
		super.onPause();
		finish();
	}
//	
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
		
		if (arg2 == 0) {  //Home
			Intent i = new Intent("com.healthtapper.waterbalance.MAINACTIVITY");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 1) {  //Drink Log
			Intent i = new Intent("com.healthtapper.waterbalance.SQLVIEW");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 2) {  //Statistics
			Intent i = new Intent("com.healthtapper.waterbalance.STATISTICS");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 3) {  //Feedback
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
