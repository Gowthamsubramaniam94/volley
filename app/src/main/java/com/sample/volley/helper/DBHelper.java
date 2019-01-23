package com.sample.volley.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sample.volley.webservice.CommonValues;
import com.sample.volley.webservice.ReturnValues;

import java.util.ArrayList;

public class DBHelper implements CommonValues {

    private int DATABASE_VERSION = 1;
    private String TAG = DBHelper.class.getSimpleName();
    private DataBaseHelper myDBHelper;
    private SQLiteDatabase myDataBase;
    private ReturnValues myReturnValues;
    //Table Name
    String SAMPLE_TABLE_NAME = "sample";

    public DBHelper(Context context) {
        myDBHelper = new DataBaseHelper(context);
        myDataBase = myDBHelper.getReadableDatabase();
        myReturnValues = new ReturnValues();
        open();

    }

    public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase aDB, int aOldVersion,
                              int aNewVersion) {

            Log.e(TAG, "onUpgrade called " + aOldVersion + " NEW" + aNewVersion);

            try {

                try {
                    String aAlterNes = "ALTER TABLE " + SAMPLE_TABLE_NAME + " ADD meeting_heading";
                    aDB.execSQL(aAlterNes);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function to make DB as readable and writable
     */
    private void open() {

        try {
            if (myDataBase == null) {
                System.out.println("DB WRITE");
                myDataBase = myDBHelper.getWritableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to Close the database
     */
    public void close() {

        try {
            Log.d(TAG, "mySQLiteDatabase Closed");

            // ---Closing the database---
            if (myDBHelper != null) {
                //  myDBHelper.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeSampleList(ArrayList<ReturnValues.SampleList> aSampleInfo) {

        try {
            Log.d(TAG, "Store Service type in DB");

            for (int i = 0; i < aSampleInfo.size(); i++) {
                ReturnValues.SampleList aSampleList = aSampleInfo.get(i);
                ContentValues values = new ContentValues();
                values.put("id", aSampleList.Id);
                values.put("user_id", aSampleList.userId);
                values.put("description", aSampleList.description);

                myDataBase.insert(SAMPLE_TABLE_NAME, null, values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ReturnValues.SampleList> getServiceTypeInfoList() {

        ArrayList<ReturnValues.SampleList> aSampleInfoList = new ArrayList<ReturnValues.SampleList>();

        try {
            String aQuery = "";
            aQuery = "SELECT  * FROM " + SAMPLE_TABLE_NAME;
            Log.e("Query ", aQuery);
            Cursor aCursor = myDataBase.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {
                while (!aCursor.isAfterLast()) {
                    ReturnValues.SampleList aServiceTypeInfo = myReturnValues
                            .getSampleListInstance();

                    aServiceTypeInfo.Id(aCursor.getString(aCursor
                            .getColumnIndex("id")));
                    aServiceTypeInfo.userId(aCursor.getString(aCursor
                            .getColumnIndex("user_id")));
                    aServiceTypeInfo.description(aCursor
                            .getString(aCursor
                                    .getColumnIndex("descrition")));

                    aSampleInfoList.add(aServiceTypeInfo);
                    aCursor.moveToNext();
                }
            }
            aCursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aSampleInfoList;
    }


    /**
     * @param aDesc
     * @param aUserIdStr
     */
    public void updateSampleDB(String aDesc,
                               String aUserIdStr) {

        Log.d(TAG, "update Update User Information in DB");
        try {
            String aInsert = "UPDATE " + SAMPLE_TABLE_NAME
                    + " SET  description = '"
                    + aDesc + "'   WHERE user_id = '"
                    + aUserIdStr + "'";
            Log.e("Query ", aInsert);
            myDataBase.execSQL(aInsert);

        } catch (SQLiteException e) {
            Log.e(" SQLiteException ::", e.getMessage().toString());
        }

    }

}