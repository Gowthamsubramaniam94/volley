package com.angler.volley.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {

    private SharedPreferences mySharedPreference;
    private SharedPreferences.Editor mySharedEditor;
    public static String myUserNameStr = "";

    public Preference(Context aCtx) {

        mySharedPreference = PreferenceManager
                .getDefaultSharedPreferences(aCtx);
        mySharedEditor = mySharedPreference.edit();

    }


    public void putTermsCond(String aString) {
        mySharedEditor.putString("String", aString);
        mySharedEditor.commit();
    }

    public String getTermsCond() {
        return mySharedPreference.getString("String", "");
    }

    public void putBizFeedRef(boolean isTrue) {
        mySharedEditor.putBoolean("Boolean", isTrue);
        mySharedEditor.commit();
    }

    public boolean getBizFeedRef() {
        return mySharedPreference.getBoolean("Boolean", false);
    }

}