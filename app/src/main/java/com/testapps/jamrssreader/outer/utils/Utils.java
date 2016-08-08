package com.testapps.jamrssreader.outer.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.jetbrains.annotations.Contract;

/**
 * Created by affy on 08.08.16.
 */
public class Utils {

    private static int deviceAPI = Build.VERSION.SDK_INT;

    private static Context mContext = JamRSSReaderApp.getContext();

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

//    public static void launchMarket() {
//        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
//        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
//        // To count with Play market backstack, After pressing back button,
//        // to taken back to our application, we need to add following flags to intent.
//        myAppLinkToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        try {
//            mContext.startActivity(myAppLinkToMarket);
//        } catch (ActivityNotFoundException e) {
//            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
//        }
//    }

    @Contract(pure = true)
    public static boolean isKitkat() {
        return deviceAPI >= Build.VERSION_CODES.KITKAT;
    }

    @Contract(pure = true)
    public static boolean isLollipop() {
        return deviceAPI >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Contract(pure = true)
    public static boolean isMarshmallow() {
        return deviceAPI >= Build.VERSION_CODES.M;
    }

    @Contract(pure = true)
    public static boolean isNougat() {
        return deviceAPI >= Build.VERSION_CODES.N;
    }

    public static void logError(Exception e) {
        Log.e("-CHP-ERROR:", " " + e.getMessage());
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
