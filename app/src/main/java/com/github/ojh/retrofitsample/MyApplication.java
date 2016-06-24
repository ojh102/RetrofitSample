package com.github.ojh.retrofitsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by JaeHwan Oh on 2016-06-24.
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
