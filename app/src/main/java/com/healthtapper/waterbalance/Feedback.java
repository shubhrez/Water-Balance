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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Feedback extends Activity implements OnItemClickListener {

	
	EditText etSubject, etFeedback, etName;
    TextView email, subject, feedback, name;
	String emailAdd, subjectAdd, feedbackAdd, nameAdd;
	Button sendMail;
	private static int requestcode = 2 ;
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets_feedback;
	private ActionBarDrawerToggle drawerListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.feedback);
        ActionBar bar = getActionBar();
        String title = getResources().getString(R.string.titleFeedback);
        bar.setTitle(title);
        //bar.setTitle(Html.fromHtml("<font color='#000000'>Feedback </font>"));
        //bar.setTitle("Feedback");
        bar.setIcon(R.drawable.icon);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B2FF")));
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Dosis-SemiBold.ttf");
        initiateVars();
        name.setTypeface(custom_font);
        subject.setTypeface(custom_font);
        feedback.setTypeface(custom_font);

        sendMail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getVariablesInString();
				String emailAddress[] = { emailAdd };
				String message = feedbackAdd + "\n" + "\n" + nameAdd;
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddress);
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectAdd);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
	//			startActivity(emailIntent);
				startActivityForResult(emailIntent, requestcode);
				//onActivityResult();
				

			}
		});

		planets_feedback = getResources().getStringArray(R.array.planets_feedback);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.navigation_bar, planets_feedback));
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.drawer_close,
				R.string.drawer_open);
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	private void initiateVars() {
		// TODO Auto-generated method stub
		//etEmail = (EditText) findViewById(R.id.email);
		etSubject = (EditText) findViewById(R.id.subject);
		etFeedback = (EditText) findViewById(R.id.feedback);
		etName = (EditText) findViewById(R.id.name);
		sendMail = (Button) findViewById(R.id.bsentEmail);
        name = (TextView) findViewById(R.id.idname);
        subject =(TextView) findViewById(R.id.idSubject);
        feedback = (TextView) findViewById(R.id.idfeedback);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == requestcode){
				Intent i = new Intent("com.healthtapper.waterbalance.MAINACTIVITY");
				startActivity(i);
		}
	}

	public void getVariablesInString() {
		emailAdd = "healthtappers@gmail.com";
		//emailAdd = etEmail.getText().toString();
		subjectAdd = etSubject.getText().toString();
		feedbackAdd = etFeedback.getText().toString();
		nameAdd = etName.getText().toString();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
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
		}else if (arg2 == 2) {  //Statistics
			Intent i = new Intent("com.healthtapper.waterbalance.STATISTICS");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}else if (arg2 == 3) { // About
			Intent i = new Intent("com.healthtapper.waterbalance.ABOUT");
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