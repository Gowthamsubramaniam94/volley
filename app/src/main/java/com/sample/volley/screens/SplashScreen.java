package com.sample.volley.screens;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sample.volley.NetworkManager.NetworkManager;
import com.sample.volley.R;
import com.sample.volley.helper.DBHelper;
import com.sample.volley.webservice.CommonValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends FragmentActivity implements CommonValues {

    private String TAG = SplashScreen.class.getSimpleName();
    private FragmentActivity myContext;
    private boolean isSplashScreenLoadedTimeOut = false;
    private boolean isDBPresent = true;
    private DBInsert myDBLoadTask = null;
    // --- Get file directory ---
    private String myDBFileName;
    private DBHelper myDBHelper;

    private NetworkManager myNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        myContext = SplashScreen.this;
        myDBHelper = new DBHelper(myContext);
        myNetworkManager = new NetworkManager();
        myDBFileName = myContext.getDatabasePath(DATABASE_NAME).toString();


        if (!isDBExist(myDBFileName)) {

            Log.d("TRW DB STATUS", "NOT PRESENT & Started async task");
            isDBPresent = false;
            myDBLoadTask = new DBInsert();
            myDBLoadTask.execute();

        } else
            Log.d("TRW DB STATUS", "DB PRESENT");

        Handler aHoldScreen = new Handler();
        aHoldScreen.postDelayed(new Runnable() {
            public void run() {
            }
        }, DELAY_TIME_FOR_SPLASH_SCREEN);

    }


    private void callMainScreen() {


    }


    class DBInsert extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Long doInBackground(Void... params) {

            constructNewFileFromResources(myDBFileName);
            return null;
        }

        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            Log.d("SPLASH", "Completed" + result);

            if (isSplashScreenLoadedTimeOut) {
                Log.d("TRW DB STATUS", "DB LOADED AFTER SPLASH SCREEN TIME OUT");
                //callRegistrationScreen();

            } else
                Log.d("TRW DB STATUS",
                        "DB LOADED BEFORE SPLASH SCREEN TIME OUT");

        }

    }

    private boolean isDBExist(String aDBFileName) {

        boolean aStatus = false;

        SQLiteDatabase aCheck = null;
        try {
            aCheck = SQLiteDatabase.openDatabase(aDBFileName, null,
                    SQLiteDatabase.OPEN_READONLY
                            | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

            aStatus = (aCheck != null) ? true : false;

            if (aStatus)
                aCheck.close();

        } catch (SQLiteException e) {

        }
        return aStatus;
    }

    public void constructNewFileFromResources(String DBFile) {

        try {
            String packageName = getApplicationContext().getPackageName();

            String appDatabaseDirectory = String.format(
                    "/data/data/%s/databases", packageName);
            (new File(appDatabaseDirectory)).mkdir();
            OutputStream dos = new FileOutputStream(appDatabaseDirectory + "/"
                    + DATABASE_NAME);

            InputStream dis = getResources().openRawResource(
                    DB_RAW_RESOURCES_ID);
            byte[] buffer = new byte[1028];
            while ((dis.read(buffer)) > 0) {
                dos.write(buffer);
            }
            dos.flush();
            dos.close();
            dis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkInternet() {
        if (myNetworkManager.isInternetOn(myContext))
            return true;
        else {
            return false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}