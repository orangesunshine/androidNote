package com.example.eventbus.controller;

import android.os.Handler;
import android.os.Looper;

import com.google.inject.Singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yhuang115 on 2018/4/26.
 */
@Singleton
public class LocalThreadExecutor extends ThreadPoolExecutor {
    private Handler mHandler = new Handler(Looper.myLooper());

    public LocalThreadExecutor() {
        super(1, 10, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    /**
     * 主线执行
     */
    public void uiexe(Runnable runnable) {
        mHandler.post(runnable);
    }
}
