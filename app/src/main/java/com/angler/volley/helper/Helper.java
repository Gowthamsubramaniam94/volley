package com.angler.volley.helper;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.angler.volley.R;
import com.angler.volley.webservice.CommonValues;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Helper implements CommonValues {

    private static String TAG = Helper.class.getSimpleName();

    public static void loadImage( Context aContext, final ImageView aImageView, String aImageUrlStr, int aDefaultImage, int aErrorImage ) {
        try {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init( ImageLoaderConfiguration.createDefault( aContext ) );
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory( true )
                    .cacheOnDisk( true )
                    .imageScaleType( ImageScaleType.EXACTLY )
                    .resetViewBeforeLoading( true )
                    .showImageForEmptyUri( ContextCompat.getDrawable( aContext, aDefaultImage ) )
                    .showImageOnFail( ContextCompat.getDrawable( aContext, aErrorImage ) )
                    .showImageOnLoading( ContextCompat.getDrawable( aContext, aDefaultImage ) )
                    .build();

            //download and display image from url
            imageLoader.displayImage( aImageUrlStr, aImageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted( String s, View view ) {

                }

                @Override
                public void onLoadingFailed( String s, View view, FailReason failReason ) {

                }

                @Override
                public void onLoadingComplete( String s, View view, Bitmap bitmap ) {
                    //   Log.d(TAG, "onLoadingComplete: " + s);

                }


                @Override
                public void onLoadingCancelled( String s, View view ) {

                }
            } );
        } catch( OutOfMemoryError e ) {
            e.printStackTrace();
        } catch( Exception e ) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings({"unused", "deprecation"})
    public static void getListViewSize(ListView myListView,
                                       FragmentActivity context) {

        try {
            ListAdapter myListAdapter = myListView.getAdapter();
            if (myListAdapter == null) {
                // do nothing return null
                return;
            }
            // set listAdapter in loop for getting final size
            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++) {
                View listItem = myListAdapter.getView(size, null, myListView);
                WindowManager wm = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();

                int screenWidth = display.getWidth();
                int height = display.getHeight();
                int leftPadding = 0, rightPadding = 0;

                int listViewWidth = screenWidth - leftPadding - rightPadding;
                int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth,
                        View.MeasureSpec.AT_MOST);
                listItem.measure(widthSpec, 0);

                totalHeight += listItem.getMeasuredHeight();
            }
            // setting listview item in adapter
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight
                    + (myListView.getDividerHeight() * (myListAdapter
                    .getCount() - 1));
            myListView.setLayoutParams(params);
            myListView.requestLayout();
            // print height of adapter on log
            Log.i("height of listItem:", String.valueOf(totalHeight));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }


    /**
     * getAppName
     *
     * @param myContext
     * @return
     */
    public static String getAppName(Context myContext) {
        return myContext.getResources().getString(R.string.app_name);
    }


    /**
     * @param aFragmentActivity
     * @param aUrl
     */
    public static void callWebView(FragmentActivity aFragmentActivity,
                                   String aUrl) {
        try {
            if (!aUrl.startsWith("http://"))
                aUrl = "http://" + aUrl;

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(aUrl));
            aFragmentActivity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Cureeent Date and time
     *
     * @param aDateFormat
     * @return
     */
    public static String getCurrentDateAndTime(String aDateFormat) {
        try {
            DateFormat sdf = new SimpleDateFormat(aDateFormat);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Get Cureeent Date and time
     *
     * @param aDateFormat
     * @return
     */
    public static String getTomorrowDateAndTime(String aDateFormat) {
        try {
            DateFormat sdf = new SimpleDateFormat(aDateFormat);
            Date date = new Date();
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTime(date);
            aCalendar.add(Calendar.DATE, 1);
            return sdf.format(aCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param aLastUpdatedTime
     * @return
     */
    public static boolean isDayOver(String aLastUpdatedTime) {

        try {
            Date aCurrentDate = getDate(getCurrentDateAndTime(DATE_FORMAT_LAST_UPDATED_TIME), DATE_FORMAT_LAST_UPDATED_TIME);

            Date aLastUpdatedDate = getDate(aLastUpdatedTime, DATE_FORMAT_LAST_UPDATED_TIME);

            return aCurrentDate.after(aLastUpdatedDate);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * getDateInMilliSeconds
     *
     * @param date
     * @param aDateFormatStr
     * @return
     */
    public static long getDateInMilliSeconds(String date, String aDateFormatStr) {

        StringBuilder aSelectedDate = null;
        try {

            DateFormat sdf = new SimpleDateFormat(aDateFormatStr);
            Date parsedDate = sdf.parse(date.toString());
            Calendar now = Calendar.getInstance();
            now.setTime(parsedDate);
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTimeInMillis(System.currentTimeMillis());
            int selectedYear = aCalendar.get(Calendar.YEAR);
            int selectedMonth = now.get(Calendar.MONTH);
            int selectedDay = now.get(Calendar.DAY_OF_MONTH);
            aSelectedDate = new StringBuilder().append(selectedDay).append("-")
                    .append(selectedMonth + 1).append("-").append(selectedYear);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.e("TI", e.getMessage().toString());
        }
        // return convertIntoDateFormat(aSelectedDate.toString(),
        // DATE_FORMAT_FOR_DATE_AND_TIME, "dd-MM-yyyy");
        String aDateStr = convertIntoDateFormat(aSelectedDate.toString(),
                DATE_FORMAT_FOR_DATE_AND_TIME, "dd-MM-yyyy");

        return getTimeInMilliSeconds(aDateStr, DATE_FORMAT_FOR_DATE_AND_TIME);

    }


    /**
     * getDateAndTimeInMilliSeconds
     *
     * @param date
     * @param aDateFormatStr
     * @return
     */
    public static long getDateAndTimeInMilliSeconds(String date,
                                                    String aDateFormatStr) {
        Calendar now = Calendar.getInstance();
        try {
            DateFormat sdf = new SimpleDateFormat(aDateFormatStr);
            Date parsedDate = sdf.parse(date.toString());
            now.setTime(parsedDate);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.e("TI", e.getMessage().toString());
        }

        return now.getTimeInMillis();

    }

    /**
     * convertIntoDateFormat
     *
     * @param aSelectedDate
     * @param aConversionFormat
     * @param aCurrentFormat
     * @return
     */
    public static String convertIntoDateFormat(String aSelectedDate,
                                               String aConversionFormat, String aCurrentFormat) {
        if (aSelectedDate.length() == 0)
            return "";

        String aDate = "";

        DateFormat curFormater = new SimpleDateFormat(aCurrentFormat);
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(aSelectedDate.toString());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage().toString());
        }

        DateFormat postFormater = new SimpleDateFormat(aConversionFormat);
        try {
            aDate = postFormater.format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage().toString());
        }

        return aDate;
    }

    /**
     * getTimeInMilliSeconds
     *
     * @param aDateStr
     * @return
     */
    public static long getTimeInMilliSeconds(String aDateStr,
                                             String aDateFormatStr) {
        DateFormat curFormater = new SimpleDateFormat(aDateFormatStr);
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(aDateStr.trim());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return dateObj.getTime();

    }

    /**
     * Conver Milliseconds into required date format
     *
     * @param milliSeconds
     * @param dateFormat
     * @return
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * getDate
     *
     * @param aDateStr
     * @param aDateFormat
     * @return
     */
    public static Date getDate(String aDateStr, String aDateFormat) {
        Date aDate = null;
        try {
            DateFormat sdf1 = new SimpleDateFormat(aDateFormat);
            aDate = sdf1.parse(aDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aDate;
    }


    /**
     * getCurrentDate
     *
     * @return
     */
    public static Date getCurrentDate() {
        Date aDate = null;
        try {
            aDate = new Date(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aDate;
    }

    /**
     * getCurrentDate
     *
     * @return
     */
    public static Date getCurrentDate(String aFormat) {
        Date aDate = null;
        try {
            aDate = new Date(System.currentTimeMillis());

            DateFormat dateFormat = new SimpleDateFormat(aFormat);
            java.util.Date date = new java.util.Date();
            System.out.println("Current Date : " + dateFormat.format(aDate));

            aDate = getDate(dateFormat.format(aDate), aFormat);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return aDate;
    }


    /**
     * getTimeInDateFormat
     *
     * @param aDateStr
     * @return
     */
    public static Date getTimeInDateFormat(String aDateStr,
                                           String aDateFormatStr) {
        DateFormat curFormater = new SimpleDateFormat(aDateFormatStr);
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(aDateStr.trim());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return dateObj;

    }


    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }


    /**
     * Show Alert as in iphone
     *
     * @param myContext
     * @param aContentToShow
     * @param aAlertType
     */
    public static void showAlert(Activity myContext, String aContentToShow, int aAlertType) {
        try {
            ViewGroup viewGroup = myContext
                    .findViewById(R.id.screen_common_LAY_alert);
            if (viewGroup == null)
                return;
            viewGroup.setVisibility(View.VISIBLE);

            SnackbarManager.show(
                    Snackbar.with(myContext)
                            .position(Snackbar.SnackbarPosition.TOP)
                            .text(aContentToShow)
                            .type(SnackbarType.MULTI_LINE)
                            .textColor(Color.WHITE)
                            .color(aAlertType)
                    , viewGroup);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Function to hide soft keyboard
     */
    public static void hideSoftKeyBoard(FragmentActivity aActivity) {
        try {
            if (aActivity.getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) aActivity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(aActivity.getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Load Description in TextView for Justification as HTMl
     *
     * @param aDescripitionStr
     * @param aTextView
     */
    public static void setDescriptionInHtml(String aDescripitionStr,
                                            TextView aTextView) {

        aTextView.setText(Html.fromHtml(aDescripitionStr));

    }


    /**
     * get FormattedDate
     *
     * @param aStartDateStr
     * @param aEndTimeStr
     * @return
     */
    public static String getFormattedDate(String aStartDateStr,
                                          String aEndTimeStr) {

        DateFormat dateFormat = new SimpleDateFormat(
                DATE_FORMAT_FROM_SERVER);
        Date aStartTimeObj = new Date();
        Date aEndTimeObj = new Date();

        try {
            aStartTimeObj = dateFormat.parse(aStartDateStr);
            aEndTimeObj = dateFormat.parse(aEndTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DateFormat aStartTimeFormatted = new SimpleDateFormat(
                DATE_FORMAT_FOR_DATE_AND_TIME);
        DateFormat aEndTimeFormatted = new SimpleDateFormat(
                DATE_FORMAT_FOR_TIME);

        try {
            aStartDateStr = convertIntoDateFormat(aStartDateStr,
                    DATE_FORMAT_MYPROFILE_FROM_SERVER, DATE_FORMAT_FROM_SERVER);
            aEndTimeStr = convertIntoDateFormat(aEndTimeStr,
                    DATE_FORMAT_MYPROFILE_FROM_SERVER, DATE_FORMAT_FROM_SERVER);

            DateFormat sdf = new SimpleDateFormat(
                    DATE_FORMAT_MYPROFILE_FROM_SERVER);
            Date date1 = sdf.parse(aStartDateStr);
            Date date2 = sdf.parse(aEndTimeStr);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (!date2.equals(date1)) {
                // Meeting held for two days
                System.out.println("Date2 is not equal to Date1");
                aEndTimeFormatted = new SimpleDateFormat(
                        DATE_FORMAT_FOR_DATE_AND_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aStartTimeFormatted.format(aStartTimeObj) + " to "
                + aEndTimeFormatted.format(aEndTimeObj);

    }


    /**
     * getCurrentTimeInMilliseconds
     *
     * @return
     */
    public static long getCurrentTimeInMilliseconds() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTimeInMillis(System.currentTimeMillis());
        return aCalendar.getTimeInMillis();
    }


    /**
     * contactViaWhatsApp
     *
     * @param aContext
     * @param displayName
     * @param mobileNumber
     */
    public static void contactViaWhatsApp(FragmentActivity aContext,
                                          String displayName, String mobileNumber) {
        if (mobileNumber.equals("") || displayName.equals(""))
            return;

        saveContact(aContext, displayName, mobileNumber);
    }

    /**
     * saveContact
     *
     * @param displayName
     * @param mobileNumber
     */
    protected static void saveContact(final FragmentActivity aContext,
                                      String displayName, final String mobileNumber) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // ------------------------------------------------------ Names
        if (displayName != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            displayName).build());
        }

        // ------------------------------------------------------ Mobile Number
        if (mobileNumber != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            mobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try {
            aContext.getContentResolver().applyBatch(
                    ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(aContext, "Exception: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {

            public void run() {
                contactViaWhatsApp(aContext, mobileNumber);
            }
        }, 1000);

    }

    /**
     * contactViaWhatsApp
     *
     * @param mobile
     */
    protected static void contactViaWhatsApp(FragmentActivity aContext,
                                             String mobile) {

        boolean installed = appInstalledOrNot(aContext, "com.whatsapp");

        if (installed) {
            Uri uri = Uri.parse("smsto:" + mobile);
            Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri);
            waIntent.putExtra("chat", true);
            waIntent.setPackage("com.whatsapp");
            aContext.startActivity(Intent.createChooser(waIntent, ""));
        } else {
            showAlert(aContext, WHATS_APP_NOT_INSTALLED,
                    ALERT_FAILURE);
        }

    }

    /**
     * shareViaWhatsApp
     *
     * @param aContext
     * @param aStatus
     */
    public static void shareViaWhatsApp(FragmentActivity aContext,
                                        String aStatus) {

        boolean installed = appInstalledOrNot(aContext, "com.whatsapp");

        if (installed) {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, aStatus);
            aContext.startActivity(Intent.createChooser(waIntent, "Share with"));
        } else {
            showAlert(aContext, WHATS_APP_NOT_INSTALLED,
                    ALERT_FAILURE);
        }

    }

    /**
     * appInstalledOrNot
     *
     * @param aContext
     * @param uri
     * @return
     */
    private static boolean appInstalledOrNot(FragmentActivity aContext,
                                             String uri) {
        PackageManager pm = aContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    /**
     * exitApp
     *
     * @param aContext
     */
    public static void exitApp(FragmentActivity aContext) {
        try {
            Intent aStartMain = new Intent(Intent.ACTION_MAIN);
            aStartMain.addCategory(Intent.CATEGORY_HOME);
            aStartMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            aContext.startActivity(aStartMain);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * showToast
     *
     * @param myContext
     * @param aContentToShow
     */
    public static void showToast(FragmentActivity myContext,
                                 String aContentToShow) {
        try {
            Toast.makeText(myContext, aContentToShow, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
