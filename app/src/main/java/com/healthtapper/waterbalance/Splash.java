package com.healthtapper.waterbalance;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.splunk.mint.Mint;

public class Splash extends Activity {

	ImageView icon;
	TextView label;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(Splash.this, "acb78c23");
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		ActionBar bar = getActionBar();
		bar.hide();
		setContentView(R.layout.splash);
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DroidSerif-BoldItalic.ttf");
		label = (TextView) findViewById(R.id.label);
		label.setTypeface(custom_font);
		icon = (ImageView) findViewById(R.id.icon);
		icon.setImageResource(R.drawable.splashicon);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent mainActivity = new Intent(
							"com.healthtapper.waterbalance.MAINACTIVITY");
					startActivity(mainActivity);
				}
			}
		};
		timer.start();
	}

	protected void onPause() {
		super.onPause();
		finish();
	}
}
