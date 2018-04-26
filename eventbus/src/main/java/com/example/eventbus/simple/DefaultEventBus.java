package com.example.eventbus.simple;

import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yhuang115 on 2018/4/25.
 */

public class DefaultEventBus implements EventBus {
    private final String TAG = DefaultEventBus.class.getSimpleName();
    private static volatile DefaultEventBus sInstance;
    private Set<Observer> mObservers = new HashSet<>();

    private DefaultEventBus() {
    }

    @Override
    public void register(Event event, Observer observer) {
        if (null == event || null == observer) {
            throw new NullPointerException("event/observer 不能为空");
        }
        if (mObservers.contains(observer)) {
            for (Observer mObserver : mObservers) {
                if (observer.equals(mObserver)) {
                    mObserver.addEvent(event);
                }
            }
        } else {
            observer.addEvent(event);
            mObservers.add(observer);
        }
    }

    @Override
    public void unregister(Event event, Observer observer) {
        Iterator<Observer> iterator = mObservers.iterator();
        while (iterator.hasNext()) {
            Observer next = iterator.next();
            next.equals(observer);
            mObservers.remove(next);
        }
    }

    public static EventBus getDefault() {
        if (null == sInstance) {
            synchronized (DefaultEventBus.class) {
                if (null == sInstance) {
                    sInstance = new DefaultEventBus();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void post(Event event) {
        if (null == event) {
            throw new NullPointerException("event 不能为空");
        }
        for (Observer mObserver : mObservers) {
            mObserver.notice(event);
        }
    }
}
