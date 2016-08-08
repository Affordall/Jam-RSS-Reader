package com.testapps.jamrssreader.outer.utils;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by affy on 08.08.16.
 */
public class JamRSSReaderApp extends Application {

    private static Application instance;
    private final Context mContext;

    public JamRSSReaderApp(Context mContext) {
        this.mContext = mContext;
    }

    public JamRSSReaderApp() {
        mContext = null;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        instance = this;
    }

}
