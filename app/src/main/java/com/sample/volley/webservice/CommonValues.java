package com.sample.volley.webservice;

import android.graphics.Color;

import static com.sample.volley.R.raw.app;

public interface CommonValues {

    String APP_NAME = "Volley";

    String DATABASE_NAME = "app.db";

    int DB_RAW_RESOURCES_ID = app;

    int ALERT_SUCCESS = Color.parseColor("#4CAF50");

    int ALERT_FAILURE = Color.parseColor("#F44336");

    String ALERT_NO_INTERNET_MESSAGE = "Network is not available. Please try again later";

    String ALERT_SERVER_NOT_REACHABLE_MESSAGE = "Something went wrong. Please try again later.";

    String ALERT_NO_INTERNET_MESSAGE_ERROR = "Data connection is not available.\n Please check your connection";

    String ALERT_SERVER_NOT_REACHABLE_MESSAGE_ERROR = "Something went wrong.\n Please try again later.";

    String SERVER_NOT_REACHABLE = "Server not reachable. Please try again later";

    String WHATS_APP_NOT_INSTALLED = "WhatsApp is not Installed in your Device";

    int DELAY_TIME_FOR_SPLASH_SCREEN = 4500;

    String RESPONSE_CODE_SUCCESS = "1";

    String RESPONSE_CODE_FAILURE = "0";

    /**
     * Common Response Key
     */
    String COMMON_RESPONSE_STRING = "response";

    String COMMON_RESPONSE_CODE = "response_code";


    // Jun 04,2014
    String DATE_FORMAT_NEWS = "MMM dd, yyyy";


    String DATE_FORMAT_FOR_MONTH = "MMM\ndd";

    String DATE_FORMAT_FOR_DAY = "EEE hh:mm a";

    // 14-12-41
    String DATE_FORMAT_TIME = "kk-mm";

    // 04-June-2014
    String DATE_FORMAT_DATE = "dd-MMM-yyyy";

    String DATE_FORMAT_BIRTHDAY = "MMM dd";

    String FILTER = "chat_message";

    String CHAT_FILTER = "chat_screen";

    // Jun 04,2014 06:52 pm
    String DATE_FORMAT_LAST_UPDATED_TIME = "MMM dd, yyyy hh:mm a";

    // 04-June-2014 at 06:52 pm
    String DATE_FORMAT_MEETING_NOTIFICATION = "dd-MMM-yyyy 'at' hh:mm a";

    // 1960-08-01
    String DATE_FORMAT_MYPROFILE_FROM_SERVER = "yyyy-MM-dd";

    // 14-04-2014
    String DATE_FORMAT_MYPROFILE_FROM_PICKER = "dd-MM-yyyy";

    // 2014-06-24 15:47:18
    String DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd kk:mm:ss";

    // June 04 2014 , 15:47 pm or am
    // String DATE_FORMAT_FOR_QUERY = "MMMMM dd yyyy, hh:mm aa";

    // 08-Aug-2014 15:47 pm or am
    String DATE_FORMAT_FOR_DATE_AND_TIME = "MMM dd, yyyy hh:mm aa";

    // Jul 10, 15:47 pm or am
    String DATE_FORMAT_FOR_TIME = "MMM dd, hh:mm a";

    // 15:47 pm or am
    String DATE_FORMAT_ONLY_TIME = "hh:mm a";

    // Jul 10
    String DATE_FORMAT_ONLY_DATE = "MMM dd";

    // 17-Nov-2014 Date Format For News Screen

    String NEWS_SCREEN_DATE_FORMAT = "dd-MMM-yyyy";

    String NEWS_SCREEN_DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd";
}
