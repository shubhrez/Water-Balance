package com.healthtapper.waterbalance;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WaterIntake {
	
	public static final String KEY_DATE = "Date";
	public static final String KEY_WEIGHT = "Weight";
	public static final String KEY_INTAKE = "WaterIntake";
	public static final String KEY_TARGET = "WaterTarget";
	
	
	public static final String DATABASE_NAME="WaterBalance";
	public static final String DATABASE_TABLE="IntakeTable";
	public static final int DATABASE_VERSION= 2;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	public SQLiteDatabase ourDatabase;
	
	private class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_DATE +
					" DATE DEFAULT CURRENT_DATE, " + KEY_WEIGHT + 
					" TEXT NOT NULL, " + KEY_INTAKE + " TEXT NOT NULL, " + KEY_TARGET + " TEXT NOT NULL);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + " );");
			onCreate(db);
		}
		
	}
	
	public WaterIntake(Context c){
		ourContext = c;
	}
	
	public WaterIntake open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String wt, String wi,String dt) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_WEIGHT, wt);
		cv.put(KEY_INTAKE, wi);
		cv.put(KEY_TARGET, dt);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	/*//public String getData() {
		// TODO Auto-generated method stub
	public ArrayList<HashMap<String, String>> getData(){
		//String[] columns = new String[] {KEY_DATE,KEY_WEIGHT,KEY_INTAKE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns ,null,null,null,null,null);
		//String result =" ";
		int iDate = c.getColumnIndex(KEY_DATE);
		int iWeight = c.getColumnIndex(KEY_WEIGHT);
		int iIntake = c.getColumnIndex(KEY_INTAKE);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			//result = result + c.getString(iDate) + "                   " + c.getString(iWeight) + "                              " + c.getString(iIntake) + "\n" + "-------------------------------------------------------------------------------------" + "\n";
		
		}
		return result;
	}
	*/
}
