package com.vienan.retrofitdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by kygo on 2016/5/13 0013.
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext=base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static Context getContext(){
        return mContext;
    }
}
