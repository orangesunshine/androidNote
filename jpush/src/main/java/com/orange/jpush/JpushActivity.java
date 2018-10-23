package com.orange.jpush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.jpush.android.api.JPushInterface;

public class JpushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush);
        JpushNotifictionUtil.customPushNotification(this, 1, R.layout.custom_push_notification, R.mipmap.logo1, R.mipmap.logo2);
        ActivityMg.getInstance().perform(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JPushInterface.clearAllNotifications(this);
    }
}
