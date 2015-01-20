package com.healthtapper.waterbalance;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inmobi.monetization.IMBanner;


public class MainActivity extends Activity implements OnItemClickListener {

	/*
	 * progress bar to show amount of water drunk
	 */
	private static ProgressBar waterDrunkProgressBar;

	/*
	 * button to add water
	 */
	private Button addWater;

	/*
	 * text view to show amount of water drunk by user and the target amount of
	 * water to be drunk
	 */


	private static Integer amountOfWaterDrunk = 0;

	private static Integer targetAmountOfWater = 0;
	private static Integer notification = 1;
	public static Integer hotday = 0;
	public static Integer workout = 0;
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] planets;
	private ActionBarDrawerToggle drawerListener;

	/*
	 * list containing choices to be selected by user for amount of water drunk
	 */
	private String _listOfWaterGlasses[] = { "100", "150", "300", "400", "600" };
	private Integer icons[] = { R.drawable.glass_first, R.drawable.glass_two,
			R.drawable.glass_three, R.drawable.glass_four,
			R.drawable.glass_fifth };
	public static final String KEY_TITLE = "title";
	public static final String KEY_IMAGE = "image";
	ArrayList<HashMap<String, Object>> singlelist = new ArrayList<HashMap<String, Object>>();

	public static SharedPreferences pref;

	public static final String WEIGHT_KEY_KG = "weightKeyInKg";
	public static final String WEIGHT_KEY_POUND = "weightKeyInPound";
	public static final String HEIGHT_KEY_CM = "heightKeyInCm";
	public static final String HEIGHT_KEY_FOOT = "heightKeyInFoot";
	public static final String HEIGHT_KEY_INCH = "heightKeyInInch";
	public static final String AMOUNTDRUNK = "amountDrunkKey";
	public static final String TARGETDRUNK = "targetDrunkKey";
	public static final String NOTIFICATION = "notificationkey";
	public static final String HOTDAY = "hotdaykey";
	public static final String WORKOUT = "workoutkey";
	public static final String WEIGHT_UNIT = "weightUnit";
	public static final String HEIGHT_UNIT = "heightUnit";

	static String PREF_NAME = "HealthTapperSettings";

	ListAdapter adapter;
	static Integer weightInKg;
	static Integer weightInPound;
	static Integer heightInCm;
	static Integer heightInFoot;
	static Integer heightInInch;

	Integer amountDrunk;
	Integer targetWater;
	static TextView bodyMassIndex, bodyMassIndexTip,drinkTarget;
    TextView adjustDrinkTargetText,hotdayText,workoutText,addWaterText;
    private static TextView waterDrunkVsTarget;
	static Button hotday_button, workout_button,unitConventionButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Dosis-SemiBold.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Medium.ttf");

        drinkTarget = (TextView) findViewById(R.id.drinkTarget);
//        bodyMassIndex = (TextView) findViewById(R.id.bmi);
//        bodyMassIndexTip = (TextView) findViewById(R.id.tip);
        waterDrunkVsTarget = (TextView) findViewById(R.id.amount_of_water_drunk_vs_target);
        adjustDrinkTargetText = (TextView) findViewById(R.id.adjustDrinkTargetText);
        hotdayText = (TextView) findViewById(R.id.hotdayText);
        workoutText = (TextView) findViewById(R.id.workoutText);
        addWaterText = (TextView) findViewById(R.id.addWaterText);
        drinkTarget.setTypeface(custom_font);
//4        bodyMassIndex.setTypeface(custom_font);
//5        bodyMassIndexTip.setTypeface(custom_font1);
        waterDrunkVsTarget.setTypeface(custom_font);
        adjustDrinkTargetText.setTypeface(custom_font);
        hotdayText.setTypeface(custom_font);
        workoutText.setTypeface(custom_font);
        addWaterText.setTypeface(custom_font);
        singlelist = new ArrayList<HashMap<String, Object>>();
		waterDrunkProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		addWater = (Button) findViewById(R.id.add_water_button);
        unitConventionButton = (Button) findViewById(R.id.unitConventionButton);


        waterDrunkProgressBar.setMax(targetAmountOfWater);
		waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
		waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
				+ targetAmountOfWater + " ML");
		addWater.setBackgroundResource(R.drawable.addwater);
		// pref = getPreferences(MODE_PRIVATE);
		pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
		hotday_button = (Button) findViewById(R.id.hotday_button);
		hotday = pref.getInt(HOTDAY, 0);
		if (hotday == 1) {
			hotday_button.setBackgroundResource(R.drawable.hotday_on);
		} else {
			hotday_button.setBackgroundResource(R.drawable.hotday_off);
		}
		workout_button = (Button) findViewById(R.id.workout_button);
		workout = pref.getInt(WORKOUT, 0);
		if (workout == 1) {
			workout_button.setBackgroundResource(R.drawable.workout_on);
		} else {
			workout_button.setBackgroundResource(R.drawable.workout_off);
		}

		// bmiRange = (Button) findViewById(R.id.tip);
//1		setBMI();

		notification = pref.getInt(NOTIFICATION, 1);
		if (notification != 0) {
			new AlarmNotificationManager(getApplicationContext());
		}
		new DataEntryReceiver(getApplicationContext());

		for (int x = 0; x < 5; x++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String title = _listOfWaterGlasses[x] + " ML";
			int image = icons[x];
			map.put(KEY_TITLE, title);
			map.put(KEY_IMAGE, image);
			singlelist.add(map);
			adapter = new SimpleAdapter(MainActivity.this, singlelist,
					R.layout.single_choice_item, new String[] { KEY_TITLE,
							KEY_IMAGE }, new int[] { R.id.title, R.id.image });
		}

		hotday_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hotday = pref.getInt(HOTDAY, 0);
				if (hotday == 0) {
					targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
					amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);
					targetAmountOfWater += 250;
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt(TARGETDRUNK, targetAmountOfWater);
					editor.commit();
					hotday_button.setBackgroundResource(R.drawable.hotday_on);
					editor.putInt(HOTDAY, 1);
					editor.commit();
					waterDrunkProgressBar.setMax(targetAmountOfWater);
					waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
					waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
							+ targetAmountOfWater + " ML");
				} else {
					targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
					amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);
					targetAmountOfWater -= 250;
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt(TARGETDRUNK, targetAmountOfWater);
					editor.commit();
					hotday_button.setBackgroundResource(R.drawable.hotday_off);
					editor.putInt(HOTDAY, 0);
					editor.commit();
					waterDrunkProgressBar.setMax(targetAmountOfWater);
					waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
					waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
							+ targetAmountOfWater + " ML");
				}
			}
		});
		workout_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				workout = pref.getInt(WORKOUT, 0);
				if (workout == 0) {
					targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
					amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);
					targetAmountOfWater += 400;
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt(TARGETDRUNK, targetAmountOfWater);
					editor.commit();
					workout_button.setBackgroundResource(R.drawable.workout_on);
					editor.putInt(WORKOUT, 1);
					editor.commit();
					waterDrunkProgressBar.setMax(targetAmountOfWater);
					waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
					waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
							+ targetAmountOfWater + " ML");
				} else {
					targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
					amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);
					targetAmountOfWater -= 400;
					SharedPreferences.Editor editor = pref.edit();
					editor.putInt(TARGETDRUNK, targetAmountOfWater);
					editor.commit();
					workout_button
							.setBackgroundResource(R.drawable.workout_off);
					editor.putInt(WORKOUT, 0);
					editor.commit();
					waterDrunkProgressBar.setMax(targetAmountOfWater);
					waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
					waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
							+ targetAmountOfWater + " ML");
				}

			}
		});

		addWater.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle(R.string.addWaterAlertTitle);
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								amountOfWaterDrunk = pref
										.getInt(AMOUNTDRUNK, 0);
								int from = amountOfWaterDrunk * 100;
								amountOfWaterDrunk += Integer
										.parseInt((String) _listOfWaterGlasses[item]);
								int to = amountOfWaterDrunk * 100;
								targetAmountOfWater = pref.getInt(TARGETDRUNK,
										0);
								ProgressBarAnimate anim = new ProgressBarAnimate(
										waterDrunkProgressBar, from, to);
								anim.setDuration(1000);
								waterDrunkProgressBar.startAnimation(anim);
								waterDrunkProgressBar
										.setProgress(amountOfWaterDrunk);
								waterDrunkProgressBar
										.setMax(targetAmountOfWater * 100);
								SharedPreferences.Editor editor = pref.edit();
								targetAmountOfWater = pref.getInt(TARGETDRUNK,
										0);
								waterDrunkVsTarget.setText(amountOfWaterDrunk
										+ "/" + targetAmountOfWater + " ML");
								dialog.cancel();
								if (amountOfWaterDrunk >= targetAmountOfWater) {
									Toast.makeText(
											getApplicationContext(),
											"Congratulations, Daily Goal Reached",
											Toast.LENGTH_SHORT).show();
								}
								editor.putInt(AMOUNTDRUNK, amountOfWaterDrunk);
								editor.commit();
							}
						});

				builder.setNegativeButton(R.string.addWaterAlertButton,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

                builder.show();

			}

		});

          unitConventionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle(R.string.unitsText);
                builder.setCancelable(true);
                LayoutInflater inflater = getLayoutInflater();
                View unitConventionLayout = inflater.inflate(R.layout.unit_convention, null);
                builder.setView(unitConventionLayout);
                builder.setNegativeButton(R.string.addWaterAlertButton,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });

                builder.show();
            }

        });



        weightInKg = pref.getInt(WEIGHT_KEY_KG, 0);
		weightInPound = pref.getInt(WEIGHT_KEY_POUND, 0);
		amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);

		targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
		waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
				+ targetAmountOfWater + " ML");
		waterDrunkProgressBar.setMax(targetAmountOfWater);
		waterDrunkProgressBar.setProgress(amountOfWaterDrunk);

		if (weightInKg == 0 && weightInPound == 0) {
			showWeightHeightDialog(false);
		}

		planets = getResources().getStringArray(R.array.planets);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.navigation_bar, planets));
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.drawer_icon, R.string.drawer_close,
				R.string.drawer_open);
		/*
		 * drawerListener = new
		 * ActionBarDrawerToggle(this,drawerLayout,R.drawable
		 * .drawer_icon,R.string.drawer_close,R.string.drawer_open){
		 * 
		 * @Override public void onDrawerClosed(View drawerView) { // TODO
		 * Auto-generated method stub super.onDrawerClosed(drawerView);
		 * Toast.makeText(MainActivity.this, "Drawer Closed",
		 * Toast.LENGTH_SHORT).show(); }
		 * 
		 * @Override public void onDrawerOpened(View drawerView) { // TODO
		 * Auto-generated method stub super.onDrawerOpened(drawerView);
		 * Toast.makeText(MainActivity.this, "Drawer Open",
		 * Toast.LENGTH_SHORT).show(); }
		 * 
		 * };
		 */
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

//	public void setBMI() {
//		weightInKg = pref.getInt(WEIGHT_KEY_KG, 0);
//		if (weightInKg == 0) {
//			weightInKg = pref.getInt(WEIGHT_KEY_POUND, 0);
//			weightInKg = (int) (0.453 * weightInKg);
//		}
//		heightInCm = pref.getInt(HEIGHT_KEY_CM, 0);
//		if (heightInCm == 0) {
//			heightInFoot = pref.getInt(HEIGHT_KEY_FOOT, 0);
//			heightInInch = pref.getInt(HEIGHT_KEY_INCH, 0);
//			heightInCm = (int) (heightInFoot * 30.48 + heightInInch * 2.54);
//		}
//
//		int z = 0;
//		if (heightInCm != 0) {
//			z = (int) ((weightInKg * 10000) / (heightInCm * heightInCm));
//		}
//
//		String bmi = String.valueOf(z);
//        String string = getString(R.string.bodyMassIndex);
//		bodyMassIndex.setText(string + " " + bmi);
//
//        if (z < 18.5) {
//            bodyMassIndexTip.setText(R.string.bmiunderweight);
//        } else if (z >= 18.5 && z < 24.9) {
//            bodyMassIndexTip.setText(R.string.bmiidealweight);
//        } else if (z >= 25 && z < 29.9) {
//            bodyMassIndexTip.setText(R.string.bmioverweight);
//        } else if (z >= 30) {
//            bodyMassIndexTip.setText(R.string.bmiobese);
//        }
//	}

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
		} else if (arg2 == 2) { // Statistics
			Intent i = new Intent("com.healthtapper.waterbalance.STATISTICS");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		} else if (arg2 == 3) { // About
			Intent about = new Intent("com.healthtapper.waterbalance.ABOUT");
			startActivity(about);
			drawerLayout.closeDrawer(listView);
		} else if (arg2 == 4) { // Feedback
			Intent i = new Intent("com.healthtapper.waterbalance.FEEDBACK");
			startActivity(i);
			drawerLayout.closeDrawer(listView);
		}
	}

	public static int returnWeight(Context context) {
		pref = context.getSharedPreferences(PREF_NAME,
				Context.MODE_MULTI_PROCESS);
		weightInKg = pref.getInt(WEIGHT_KEY_KG, 0);
		if (weightInKg == 0) {
			weightInKg = pref.getInt(WEIGHT_KEY_POUND, 0);
			weightInKg = (int) (0.453 * weightInKg);
		}
		return weightInKg;
	}

	public static int returnDrinkTarget(Context context) {
		pref = context.getSharedPreferences(PREF_NAME,
				Context.MODE_MULTI_PROCESS);
		int dt = pref.getInt(TARGETDRUNK, 0);
		return dt;
	}

	public static int returnHeight(Context context) {
		pref = context.getSharedPreferences(PREF_NAME,
				Context.MODE_MULTI_PROCESS);
		heightInCm = pref.getInt(HEIGHT_KEY_CM, 0);
		if (heightInCm == 0) {
			heightInFoot = pref.getInt(HEIGHT_KEY_FOOT, 0);
			heightInInch = pref.getInt(HEIGHT_KEY_INCH, 0);
			heightInCm = (int) (heightInFoot * 30.48 + heightInInch * 2.54);
		}
		return heightInCm;
	}

	public static int returnWater(Context context) {
		pref = context.getSharedPreferences(PREF_NAME,
				Context.MODE_MULTI_PROCESS);
		int wi = pref.getInt(AMOUNTDRUNK, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(AMOUNTDRUNK, 0);
		editor.commit();
		return wi;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem menuitem = menu.findItem(R.id.notification);
		notification = pref.getInt(NOTIFICATION, 1);
		if (notification == 1) {
			menuitem.setIcon(R.drawable.notification_on);
		} else {
			menuitem.setIcon(R.drawable.notification_off);
		}
		ActionBar bar = getActionBar();
        String title = getResources().getString(R.string.titleHome);
        bar.setTitle(title);
		//bar.setTitle(Html.fromHtml("<font color='#000000'>Home</font>"));
		bar.setIcon(R.drawable.icon);
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B2FF")));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.weightEdit:
			showWeightHeightDialog(true);
			break;
		case R.id.notification:
			notification = pref.getInt(NOTIFICATION, 1);
			if (notification == 1) {
				Intent intent = new Intent(this, NotificationDisplayer.class);
				PendingIntent notifyIntent = PendingIntent.getBroadcast(
						this.getApplicationContext(), 0, intent, 0);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarmManager.cancel(notifyIntent);
				PendingIntent.getBroadcast(this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT).cancel();
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt(NOTIFICATION, 0);
				editor.commit();
				item.setIcon(R.drawable.notification_off);
			} else {
				new AlarmNotificationManager(getApplicationContext());
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt(NOTIFICATION, 1);
				editor.commit();
				item.setIcon(R.drawable.notification_on);
			}
			break;
		}

		return true;
	}

	/**
	 * Show the dialog builder to ask for user's height and weight
	 * 
	 * @param cancelable
	 *            - is true if builder should be cancelable, false otherwise
	 */

	private void showWeightHeightDialog(boolean cancelable) {
		final EditText heightUserFoot;
		final EditText heightUserInchAndCm;
		final EditText weightUser;
        final EditText heightUserInch;
		ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter
				.createFromResource(this, R.array.weight_dropdown,
						android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter
				.createFromResource(this, R.array.height_dropdown,
						android.R.layout.simple_spinner_item);

		weightAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		heightAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.height_weight_input, null);

		final Spinner weightSpinner = (Spinner) layout.findViewById(R.id.spinner_weight);
		final Spinner heightSpinner = (Spinner) layout.findViewById(R.id.spinner_height);

		weightSpinner.setAdapter(weightAdapter);
		heightSpinner.setAdapter(heightAdapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (cancelable) {
			builder.setCancelable(true);
		} else {
			builder.setCancelable(false);
		}
		builder.setTitle(R.string.weightHeightAlertTitle);

		weightUser = (EditText) layout.findViewById(R.id.weight);
		heightUserFoot = (EditText) layout.findViewById(R.id.input_foot);
		heightUserInchAndCm = (EditText) layout.findViewById(R.id.input_cm_inch);
        heightUserInch = (EditText) layout.findViewById(R.id.input_inch);

		String weightUnit = pref.getString(WEIGHT_UNIT, "0");
		String heightUnit = pref.getString(HEIGHT_UNIT, "0");

		if (weightUnit.equals("kg")) {
			weightInKg = pref.getInt(WEIGHT_KEY_KG, 0);
			weightUser.setText(weightInKg.toString());
		} else if (weightUnit.equals("lb")) {
			weightInPound = pref.getInt(WEIGHT_KEY_POUND, 0);
			weightUser.setText(weightInPound.toString());
			weightSpinner.setSelection(1);
		}

		if (heightUnit.equals("cm")) {
			heightInCm = pref.getInt(HEIGHT_KEY_CM, 0);
			heightUserInchAndCm.setText(heightInCm.toString());
		} else if (heightUnit.equals("ft-in")) {
			heightInFoot = pref.getInt(HEIGHT_KEY_FOOT, 0);
			heightInInch = pref.getInt(HEIGHT_KEY_INCH, 0);
			heightUserFoot.setText(heightInFoot.toString());
			//heightUserInchAndCm.setText(heightInInch.toString());
            heightUserInch.setText(heightInInch.toString());
            heightSpinner.setSelection(1);
		}
		// weightInKg = pref.getInt(WEIGHT_KEY_KG, 0);
		// heightInCm = pref.getInt(HEIGHT_KEY_CM, 0);
		//

		// need not display zero

		// weightUser.setText(weightInKg.toString());
		// heightUserInchAndCm.setText(heightInCm.toString());

		builder.setView(layout);

		builder.setPositiveButton(R.string.weightHeightAlertButton,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// custom listener created
					}
				});
		final Dialog dialog = builder.create();
		dialog.show();
		Button button = ((AlertDialog) dialog)
				.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setBackgroundColor(getResources().getColor(R.color.alertBlue));
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
                Integer weightPosition = weightSpinner.getSelectedItemPosition();
                Integer heightPosition = heightSpinner.getSelectedItemPosition();

           /*   if (!(weightUser.getText().toString().equals("")
                        || (Integer.parseInt(weightUser.getText().toString())) == 0
                        || heightUserInchAndCm.getText().toString().equals("") || heightUserFoot.getText().toString().equals("") || (Integer
                        .parseInt(heightUserFoot.getText().toString())) == 0)) {
*/

                if (!(weightUser.getText().toString().equals("")) && (Integer.parseInt(weightUser.getText().toString())) != 0) {

                    if (heightPosition == 1) {

                        if (!(heightUserFoot.getText().toString().equals(""))
                                && ((Integer.parseInt(heightUserFoot.getText().toString().toString())) != 0)
                                && !(heightUserInch.getText().equals(""))) {
                            // foot-inch selected
                            Integer weight = Integer.parseInt(weightUser.getText()
                                    .toString());

                            Integer heightInch = Integer
                                    .parseInt(heightUserInch.getText()
                                            .toString());
                            Integer heightFoot = Integer.parseInt(heightUserFoot
                                    .getText().toString());
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(MainActivity.WEIGHT_UNIT, (String) weightSpinner.getSelectedItem());
                            editor.putString(MainActivity.HEIGHT_UNIT, (String) heightSpinner.getSelectedItem());
                            if (((String) weightSpinner.getSelectedItem()).equals("kg")) {

                                editor.putInt(WEIGHT_KEY_KG, weight);
                                editor.putInt(WEIGHT_KEY_POUND, 0);
                                editor.putInt(HEIGHT_KEY_FOOT, heightFoot);
                                editor.putInt(HEIGHT_KEY_INCH, heightInch);
                                editor.putInt(HEIGHT_KEY_CM, 0);
                                editor.commit();
                                targetAmountOfWater = 36 * weight;
                            } else {
                                editor.putInt(WEIGHT_KEY_KG, 0);
                                editor.putInt(WEIGHT_KEY_POUND, weight);
                                editor.putInt(HEIGHT_KEY_FOOT, heightFoot);
                                editor.putInt(HEIGHT_KEY_INCH, heightInch);
                                editor.putInt(HEIGHT_KEY_CM, 0);
                                editor.commit();
                                targetAmountOfWater = (int) (0.453 * 36.0 * weight);
                            }
                            int hotday = pref.getInt(MainActivity.HOTDAY, 0);
                            int workout = pref.getInt(MainActivity.WORKOUT, 0);
                            if (hotday == 1) {
                                targetAmountOfWater += 250;
                            }
                            if (workout == 1) {
                                targetAmountOfWater += 400;
                            }
                            editor.putInt(MainActivity.TARGETDRUNK,
                                    targetAmountOfWater);
                            editor.commit();
                            waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
                                    + targetAmountOfWater + " ML");
                            waterDrunkProgressBar.setMax(targetAmountOfWater);
                            waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
                            dialog.dismiss();
    //2                        setBMI();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter height before preceeding",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else if (heightPosition == 0) {
                        // cm selected
                        if (!(heightUserInchAndCm.getText().toString().equals("")) && ((Integer.parseInt(heightUserInchAndCm.getText().toString())) != 0)) {
                            Integer weight = Integer.parseInt(weightUser.getText()
                                    .toString());
                            Integer height = Integer.parseInt(heightUserInchAndCm
                                    .getText().toString());
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(MainActivity.WEIGHT_UNIT, (String) weightSpinner.getSelectedItem());
                            editor.putString(MainActivity.HEIGHT_UNIT, (String) heightSpinner.getSelectedItem());
                            if (((String) weightSpinner.getSelectedItem()).equals("kg")) {
                                editor.putInt(WEIGHT_KEY_KG, weight);
                                editor.putInt(WEIGHT_KEY_POUND, 0);
                                editor.putInt(HEIGHT_KEY_FOOT, 0);
                                editor.putInt(HEIGHT_KEY_INCH, 0);
                                editor.putInt(HEIGHT_KEY_CM, height);
                                editor.commit();
                                targetAmountOfWater = 36 * weight;
                            } else {
                                editor.putInt(WEIGHT_KEY_KG, 0);
                                editor.putInt(WEIGHT_KEY_POUND, weight);
                                editor.putInt(HEIGHT_KEY_FOOT, 0);
                                editor.putInt(HEIGHT_KEY_INCH, 0);
                                editor.putInt(HEIGHT_KEY_CM, height);
                                editor.commit();
                                targetAmountOfWater = (int) (0.453 * 36.0 * weight);
                            }
                            int hotday = pref.getInt(MainActivity.HOTDAY, 0);
                            int workout = pref.getInt(MainActivity.WORKOUT, 0);
                            if (hotday == 1) {
                                targetAmountOfWater += 250;
                            }
                            if (workout == 1) {
                                targetAmountOfWater += 400;
                            }
                            editor.putInt(MainActivity.TARGETDRUNK, targetAmountOfWater);
                            editor.commit();
                            waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
                                    + targetAmountOfWater + " ML");
                            waterDrunkProgressBar.setMax(targetAmountOfWater);
                            waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
                            dialog.dismiss();
         //3                   setBMI();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter height before preceeding",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter weight before preceeding",
                            Toast.LENGTH_SHORT).show();
                }
            }
           });

		heightSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {

                    heightUserFoot.setVisibility(View.INVISIBLE);
                    heightUserInchAndCm.setVisibility(View.VISIBLE);
                    heightUserInch.setVisibility(View.INVISIBLE);

				} else if (position == 1) {
					heightUserFoot.setVisibility(View.VISIBLE);
                    heightUserInchAndCm.setVisibility(View.INVISIBLE);
                    heightUserInch.setVisibility(View.VISIBLE);

				}
			}


			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		amountOfWaterDrunk = pref.getInt(AMOUNTDRUNK, 0);
		targetAmountOfWater = pref.getInt(TARGETDRUNK, 0);
		MainActivity.waterDrunkProgressBar.setProgress(amountOfWaterDrunk);
		MainActivity.waterDrunkProgressBar.setMax(targetAmountOfWater);
		waterDrunkVsTarget.setText(amountOfWaterDrunk + "/"
				+ targetAmountOfWater + " ML");
	}

}
