package com.example.eventbus.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by yhuang115 on 2018/4/26.
 */

public class HyApplication extends Application {
    private static HyApplication sApplication;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();
    }

    public static HyApplication getApplication() {
        return sApplication;
    }

    public static Context getGlobalContext() {
        return sContext;
    }
}
