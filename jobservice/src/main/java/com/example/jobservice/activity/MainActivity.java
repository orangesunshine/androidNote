package com.example.jobservice.activity;

import android.annotation.TargetApi;
import android.app.job.JobScheduler;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jobservice.R;
import com.example.jobservice.service.MainService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(MainService.getIntentStart(this));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JobScheduler _JobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        _JobScheduler.cancelAll();
    }
}
