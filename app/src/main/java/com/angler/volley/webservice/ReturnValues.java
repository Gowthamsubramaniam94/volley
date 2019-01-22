package com.angler.volley.webservice;

import java.io.Serializable;
import java.util.ArrayList;

public class ReturnValues implements Serializable {

    private String myResponseCode = "";
    private String myResponseMsg = "";

    public String getResponseCode() {
        return myResponseCode;
    }

    public void setResponseCode(String aErrorCode) {
        this.myResponseCode = aErrorCode;
    }

    public String getResponseMsg() {
        return myResponseMsg;
    }

    public void setResponseMsg(String aErrorMessage) {
        this.myResponseMsg = aErrorMessage;
    }


    // Sample List
    public SampleList getSampleListInstance() {
        return new SampleList();
    }

    public class SampleList {
        public String Id = "";
        public String userId = "";
        public String description = "";
    }

    public ArrayList<SampleList> mySampleList = new ArrayList<>();
    public int myFeedTotalPage;

}
