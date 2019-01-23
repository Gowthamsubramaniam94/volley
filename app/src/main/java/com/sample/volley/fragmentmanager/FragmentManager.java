package com.sample.volley.fragmentmanager;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.sample.volley.R;

public class FragmentManager {
    private FragmentActivity myContext;

    /**
     * Last fragment tag
     */
    public static String myLastTag = "";

    public FragmentManager(FragmentActivity aContext) {
        myContext = aContext;
    }

    /**
     * Load Fragment
     *
     * @param aFragment
     * @param aUri
     * @param aTag
     * @param aTitleStr
     * @param aBundle
     */
    public void updateContent(Fragment aFragment, Uri aUri, String aTag,
                              String aTitleStr, Bundle aBundle) {
        try {

            Log.e("TAG Screen name", aTag);

            final android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
            final FragmentTransaction tr = fm.beginTransaction();

            Fragment currentFragment = fm.findFragmentByTag(myLastTag);
            if (currentFragment != null) {

                tr.hide(currentFragment);
                Log.i(myLastTag, "Hidded");

            }

            if (aFragment.isAdded()) {
                Log.i(aTag, "Already Added");
                tr.show(aFragment);
            } else {
                if (aBundle != null)
                    aFragment.setArguments(aBundle);
                tr.add(R.id.frame_container, aFragment, aTag);
                tr.addToBackStack(aTag);
                Log.i(aTag, "Newly Added");

            }
            //tr.commit();
            tr.commitAllowingStateLoss();
            fm.executePendingTransactions();
            myLastTag = aTag;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeFragment(int aCount) {

        android.support.v4.app.FragmentManager aFragmentManager = myContext
                .getSupportFragmentManager();

        for (int i = 0; i < aCount; i++) {


            aFragmentManager.popBackStack();

        }
    }

}
