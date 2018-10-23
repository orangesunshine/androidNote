package com.example.test.app;

import android.app.Application;
import android.util.Log;

import com.example.test.manager.AppForegroundStateManager;

public class MyApplication extends Application implements AppForegroundStateManager.OnAppForegroundStateChangeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        AppForegroundStateManager.getInstance().addListener(this);
    }

    @Override
    public void onAppForegroundStateChange(AppForegroundStateManager.AppForegroundState newState) {
        if (AppForegroundStateManager.AppForegroundState.IN_FOREGROUND == newState) {
            // App just entered the foreground. Do something here!
            Log.e("MyApplication", "IN_FOREGROUND");
        } else {
            // App just entered the background. Do something here!
            Log.e("MyApplication", "NOT_IN_FOREGROUND");
        }
    }
}
