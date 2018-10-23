package com.yhuang115.baidulbs.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class LocApplication extends Application {
    private static LocApplication mLocApplicationInstance;
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        mLocApplicationInstance = this;
        mAppContext = getApplicationContext();
//        LocHelper.getInstance(getApplicationContext());
    }

    public static LocApplication getLocAppInstance() {
        return mLocApplicationInstance;
    }

    public static Context getGlobleContext() {
        return mAppContext;
    }

}
