package com.example.jobservice.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("JobSchedulerService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("JobSchedulerService", "onStartJob");
        startMainService();
        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("JobSchedulerService", "onStopJob");
        startMainService();
        return false;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        startMainService();
    }

    public void startMainService() {
        startService(MainService.getIntentAlarm(this));
    }
}
