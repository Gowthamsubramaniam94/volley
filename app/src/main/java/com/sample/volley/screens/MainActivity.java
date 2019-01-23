package com.sample.volley.screens;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.sample.volley.NetworkManager.NetworkManager;
import com.sample.volley.R;
import com.sample.volley.Utilities.ProgressDialog;
import com.sample.volley.adapter.RecyclerViewAdapter;
import com.sample.volley.helper.EndlessRecyclerViewScrollListener;
import com.sample.volley.helper.Helper;
import com.sample.volley.webservice.CommonValues;
import com.sample.volley.webservice.ReturnValues;
import com.sample.volley.webservice.Webservice;
import com.sample.volley.webservice.WebserviceCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CommonValues {

    private Activity myContext;
    private NetworkManager myNetworkManager;
    private ProgressDialog myProgressDialog;
    private Webservice myWebservices;
    private ReturnValues myReturnValues;
    private RecyclerView myRecyclerView;
    private int myPage = 1;
    private int myTotalPage = 0;
    private EndlessRecyclerViewScrollListener myEndlessScrollListener;
    private RecyclerViewAdapter myAdapter;

    ArrayList<ReturnValues.SampleList> mySampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        myContext = this;
        myNetworkManager = new NetworkManager();
        myRecyclerView = findViewById(R.id.recycler_list);
    }

    //loadImageView( myContext, "ImageView", "" );

    private void loadImageView(Context aContext, ImageView aImageView, String aImageUrl) {
        Helper.loadImage(aContext, aImageView, aImageUrl, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
    }

    private void setupRecyclerView() {
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(myContext);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);
        myAdapter = new RecyclerViewAdapter(myContext, mySampleList);
        myRecyclerView.setAdapter(myAdapter);

        myEndlessScrollListener = new EndlessRecyclerViewScrollListener(myLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                try {
                    if (myTotalPage > myPage) {
                        //isClear = false;
                        myPage++;
                        //loadValues();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        myRecyclerView.addOnScrollListener(myEndlessScrollListener);
    }

    private void callFeedDetail(String myFeedId) {

        if (checkInternet()) {

            myProgressDialog = new ProgressDialog(this);
            myProgressDialog.setCancelable(true);
            myProgressDialog.show();

            String aInputSTR = "&feed_id=" + myFeedId;

            myWebservices.sampleGetAllData(aInputSTR, new WebserviceCallback() {
                @Override
                public void onSuccess(Object object) {

                    myProgressDialog.dismiss();
                    myReturnValues = (ReturnValues) object;

                    switch (myReturnValues.getResponseCode()) {
                        case RESPONSE_CODE_SUCCESS:

                            break;
                        case RESPONSE_CODE_FAILURE:
                            Helper.showAlert(myContext, myReturnValues.getResponseMsg(), ALERT_FAILURE);
                            break;
                        default:
                            Helper.showAlert(myContext, SERVER_NOT_REACHABLE, ALERT_FAILURE);
                            break;
                    }
                }

                @Override
                public void onFailer(Object object) {
                    myProgressDialog.dismiss();
                    myReturnValues = (ReturnValues) object;
                    Helper.showAlert(myContext, myReturnValues.getResponseMsg(), ALERT_FAILURE);
                }
            });

        }
    }

    //Check Internet
    private boolean checkInternet() {
        return myNetworkManager.isInternetOn(this);
    }

}
