package com.healthtapper.waterbalance;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CustomHeightWeightDialogListener implements OnClickListener {

	private Dialog dialog;
	private Context context;
	private EditText weightUser;
	private EditText heightUserInchAndCm;
	private EditText heightUserFoot;
	private Spinner heightSpinner;
	private Spinner weightSpinner;
	private SharedPreferences pref;
	private int _targetAmountOfWater;
	private TextView _waterDrunkVsTarget;
	private ProgressBar _waterDrunkProgressBar;
	private int _amountOfWaterDrunk;

	CustomHeightWeightDialogListener(Dialog dialog, Context context,
			EditText weightUser, EditText heightUserInchAndCm,
			EditText heightUserFoot, Spinner weightSpinner,
			Spinner heightSpinner, SharedPreferences pref,
			int _targetAmountOfWater, ProgressBar _waterDrunkProgressBar,
			TextView _waterDrunkVsTarget, int _amountOfWaterDrunk) {
		this.dialog = dialog;

		this.context = context;
		this.weightUser = weightUser;
		this.heightUserInchAndCm = heightUserInchAndCm;
		this.heightUserFoot = heightUserFoot;
		this.heightSpinner = heightSpinner;
		this.weightSpinner = weightSpinner;
		this.pref = pref;
		this._targetAmountOfWater = _targetAmountOfWater;
		this._waterDrunkVsTarget = _waterDrunkVsTarget;
		this._waterDrunkProgressBar = _waterDrunkProgressBar;
		this._amountOfWaterDrunk = _amountOfWaterDrunk;
	}

	@Override
	public void onClick(View v) {
		if ((weightUser.getText().toString().equals(""))
				|| (heightUserInchAndCm.getText().toString().equals(""))
				|| Integer.parseInt(weightUser.getText().toString()) == 0
				|| Integer.parseInt(heightUserInchAndCm.getText().toString()) == 0) {
			Toast.makeText(context, "Please fill the info", Toast.LENGTH_SHORT)
					.show();

		} else {
			SharedPreferences.Editor editor = pref.edit();
			Integer weight = Integer.parseInt(weightUser.getText().toString());
			//Integer height = Integer.parseInt(heightUser.getText().toString());

			editor.putInt(MainActivity.WEIGHT_KEY_KG, weight);
			// editor.putInt(MainActivity.HEIGHT_KEY_CM, height);
			_targetAmountOfWater = 36 * weight;
			int hotday = pref.getInt(MainActivity.HOTDAY, 0);
			int workout = pref.getInt(MainActivity.WORKOUT, 0);
			if (hotday == 1) {
				_targetAmountOfWater += 250;
			}
			if (workout == 1) {
				_targetAmountOfWater += 400;
			}
			editor.putInt(MainActivity.TARGETDRUNK, _targetAmountOfWater);

			_waterDrunkVsTarget.setText(_amountOfWaterDrunk + "/"
					+ _targetAmountOfWater + " ML");
			_waterDrunkProgressBar.setMax(_targetAmountOfWater);
			_waterDrunkProgressBar.setProgress(_amountOfWaterDrunk);
			editor.commit();
			dialog.dismiss();
			MainActivity.setBMI();
		}

	}
}
