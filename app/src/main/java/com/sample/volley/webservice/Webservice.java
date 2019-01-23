package com.sample.volley.webservice;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sample.volley.ApplicationClass;
import com.sample.volley.R;
import com.sample.volley.sharedpreference.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

public class Webservice implements CommonValues {

    private String TAG = Webservice.class.getSimpleName();
    private ReturnValues myReturnValues;
    private Preference mySession;
    private Context myContext;

    private static String MAIN_URL = "";

    private static String SAMPLE_CASE = "Case=sampleCase";


    public Webservice(Context aContext) {
        this.myContext = aContext;
        mySession = new Preference(aContext);
        MAIN_URL = myContext.getResources().getString(R.string.webservice_url);
    }


    public void sampleSendData(String aInputSTR, final WebserviceCallback callback) {


        final String aURL = MAIN_URL + SAMPLE_CASE + aInputSTR;

        myReturnValues = new ReturnValues();

        StringRequest aStringRequest = new StringRequest(GET, aURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject aJsonObject = new JSONObject(response);
                    getResponseStatus(aJsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(myReturnValues);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailer(SERVER_NOT_REACHABLE);
                Log.e(TAG, "onErrorResponse" + error.toString());
            }
        });


        aStringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ApplicationClass.getInstance().addToRequestQueue(aStringRequest, TAG);
    }


    public void sampleGetAllData(String aInputSTR, final WebserviceCallback aCallback) {

        String aUrl = MAIN_URL + SAMPLE_CASE + aInputSTR;

        myReturnValues = new ReturnValues();

        StringRequest aStringRequest = new StringRequest(GET, aUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject aJsonObject = new JSONObject(response);
                    getResponseStatus(aJsonObject);

                    JSONObject aDetailsObject = aJsonObject.getJSONObject("details");

                    ReturnValues.SampleList aBizProfileDetails = new ReturnValues().getSampleListInstance();

                    aBizProfileDetails.Id = aDetailsObject.getString("id");
                    aBizProfileDetails.userId = aDetailsObject.getString("ev_id");
                    aBizProfileDetails.description = aDetailsObject.getString("ev_name");

                    myReturnValues.mySampleList.add(aBizProfileDetails);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aCallback.onSuccess(myReturnValues);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                aCallback.onFailer(SERVER_NOT_REACHABLE);
                Log.e(TAG, "onErrorResponse Biz T&C" + error.toString());
            }
        });

        aStringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ApplicationClass.getInstance().addToRequestQueue(aStringRequest, TAG);

    }


    /**
     * Function to get Response Status and Store in Pojo Class
     *
     * @param jsonObject
     */
    private void getResponseStatus(JSONObject jsonObject) {

        try {
            myReturnValues = new ReturnValues();
            String aResponseStr = jsonObject.getString("Response");
            jsonObject = new JSONObject(aResponseStr);

            myReturnValues.setResponseMsg(jsonObject
                    .getString(COMMON_RESPONSE_STRING));
            myReturnValues.setResponseCode(jsonObject
                    .getString(COMMON_RESPONSE_CODE));

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


}
