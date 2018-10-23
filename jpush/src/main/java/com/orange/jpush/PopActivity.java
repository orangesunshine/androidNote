package com.orange.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

import static com.orange.jpush.JpushReceiver.printBundle;

public class PopActivity extends AppCompatActivity {
    private static final String TAG = "PopActivity";
    private TextView tvTitle, tvContent, tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvMsg = findViewById(R.id.tv_msg);
        showBundle(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "[PopActivity] onNewIntent");
        showBundle(intent);
    }

    private void showBundle(Intent intent) {
        if (null == intent) return;
        Bundle bundle = intent.getExtras();
        if (null == bundle) return;
        Log.d(TAG, "[PopActivity] showBundle -extras: " + printBundle(bundle));
        String title = bundle.getString(JPushInterface.EXTRA_ALERT);
        if (null != tvTitle && !TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        String content = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        if (null != tvContent && !TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        if (null != tvMsg && !TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
