package com.orange.jpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class JpushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }
}
