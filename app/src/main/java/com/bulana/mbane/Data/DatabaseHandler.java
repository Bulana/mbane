package com.bulana.mbane.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.bulana.mbane.Model.ApplianceModel;
import com.bulana.mbane.Util.Constants;

import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context mContext;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPLIANCE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_APPLIANCE_ITEM + " TEXT,"
                + Constants.KEY_WATTAGE + " INTEGER,"
                + Constants.KEY_USAGE_TIME + " LONG,"
                + Constants.KEY_QTY_NUMBER + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG);";
        db.execSQL(CREATE_APPLIANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    //Add Appliance
    public void AddAppliance(ApplianceModel applianceModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_APPLIANCE_ITEM, applianceModel.getName());
        values.put(Constants.KEY_QTY_NUMBER, applianceModel.getQuantity());
        values.put(Constants.KEY_WATTAGE, applianceModel.getWattage());
        values.put(Constants.KEY_USAGE_TIME, applianceModel.getTime());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert row
        db.insert(Constants.TABLE_NAME, null, values);
        Log.d("Saved", "Saved to DB");
    }

    //Get Appliance
    private ApplianceModel getAppliance(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[] {Constants.KEY_ID,
                        Constants.KEY_APPLIANCE_ITEM,
                        Constants.KEY_WATTAGE,
                        Constants.KEY_USAGE_TIME,
                        Constants.KEY_QTY_NUMBER,
                        Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            ApplianceModel applianceModel = new ApplianceModel();
            applianceModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            applianceModel.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_APPLIANCE_ITEM)));
            applianceModel.setWattage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_WATTAGE))));
            applianceModel.setTime(cursor.getString(cursor.getColumnIndex(Constants.KEY_USAGE_TIME)));
            applianceModel.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //convert timestamp to something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

            applianceModel.setDateItemAdded(formatDate);
         return applianceModel;
        }
        return null;
    }

    //Get all Appliances
    public List<ApplianceModel> getAllAppliances() {
        return null;
    }
    //Update Appliances
    public int updateAppliances(ApplianceModel applianceModel) {
        return 0;
    }

    //Delete Appliance
    public void deleteAppliances(int id) {

    }

    //Get count of appliances
    public int getAppliancesCount() {
        return 0;
    }
}
